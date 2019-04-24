
package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import services.CurriculumService;
import services.HackerService;
import services.PositionService;
import domain.Application;
import domain.Company;
import domain.Curriculum;
import domain.Hacker;
import domain.Position;
import domain.Problem;
import forms.ApplicationForm;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CompanyService		companyService;
	@Autowired
	private CurriculumService	curriculumService;
	@Autowired
	private HackerService		hackerService;
	@Autowired
	private PositionService		positionService;


	// Constructors -----------------------------------------------------------

	public ApplicationController() {
		super();
	}

	// Hacker list ------------------------------------------------------------

	@RequestMapping(value = "/hacker/list", method = RequestMethod.GET)
	public ModelAndView hackerList() {
		final ModelAndView result;
		final Hacker hacker;
		final Map<String, List<Application>> applications;

		hacker = this.hackerService.findPrincipal();
		if (hacker == null)
			return new ModelAndView("redirect:/welcome/index.do");
		applications = ApplicationService.groupByStatus(this.applicationService.getApplicationsOfHacker(hacker));

		result = new ModelAndView("application/list");
		result.addObject("pendingApplications", applications.get("PENDING"));
		result.addObject("submittedApplications", applications.get("SUBMITTED"));
		result.addObject("rejectedApplications", applications.get("REJECTED"));
		result.addObject("acceptedApplications", applications.get("ACCEPTED"));

		return result;
	}

	// Hacker show ------------------------------------------------------------

	@RequestMapping(value = "/hacker/show", method = RequestMethod.GET)
	public ModelAndView hackerShow(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final Hacker hacker;
		final Application application;

		hacker = this.hackerService.findPrincipal();
		if (hacker == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(id);
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getHacker().equals(hacker))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("application/show");

		result.addObject("application", application);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/hacker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "positionId") final int positionId) {
		final ModelAndView result;
		final Hacker hacker;
		final Position position;

		hacker = this.hackerService.findPrincipal();
		if (hacker == null)
			return new ModelAndView("redirect:/welcome/index.do");
		position = this.positionService.findOne(positionId);
		if (position == null)
			return new ModelAndView("redirect:/welcome/index.do");
		result = new ModelAndView("application/hacker/create");

		final Collection<Curriculum> curricula = this.curriculumService.findCurriculaByHacker(hacker);
		if (curricula.size() == 0)
			return new ModelAndView("redirect:/welcome/index.do");

		final Map<Integer, String> curriculaMap = new HashMap<>();
		for (final Curriculum curriculum : curricula)
			curriculaMap.put(curriculum.getId(), curriculum.getPersonalData().getStatement());
		result.addObject("curriculaMap", curriculaMap);
		result.addObject("position", position);

		return result;
	}

	@RequestMapping(value = "/hacker/create", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam(value = "positionId") final int positionId, @RequestParam(value = "curriculumId") final int curriculumId) {
		Application application;
		Curriculum curriculum;
		final Hacker hacker;
		Position position;
		Problem problem;

		hacker = this.hackerService.findPrincipal();
		if (hacker == null)
			return new ModelAndView("redirect:/welcome/index.do");
		position = this.positionService.findOne(positionId);
		if (position == null)
			return new ModelAndView("redirect:/welcome/index.do");
		curriculum = this.curriculumService.findOne(curriculumId);
		if (curriculum == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!curriculum.getHacker().equals(hacker))
			return new ModelAndView("redirect:/welcome/index.do");

		// Copy curriculum and set hacker to null so it doesn't show up in the hacker's curriculums
		curriculum = this.curriculumService.copy(curriculum);
		curriculum.setHacker(null);
		curriculum = this.curriculumService.save(curriculum);

		problem = null;
		final Collection<Problem> problems = position.getProblems();
		int chosenProblemIndex = ThreadLocalRandom.current().nextInt(problems.size());
		for (final Problem chosenProblem : problems) {
			if (chosenProblemIndex == 0) {
				problem = chosenProblem;
				break;
			}
			--chosenProblemIndex;
		}

		// Create application
		application = this.applicationService.create();
		application.setCurriculum(curriculum);
		application.setProblem(problem);
		application.setPosition(position);
		application.setHacker(hacker);
		application = this.applicationService.save(application);

		return this.hackerShow(application.getId());
	}

	// Submit -----------------------------------------------------------------

	@RequestMapping(value = "/hacker/submit", method = RequestMethod.GET)
	public ModelAndView submit(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final Hacker hacker;
		final Application application;

		hacker = this.hackerService.findPrincipal();
		if (hacker == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(id);
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getHacker().equals(hacker))
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getStatus().equals("PENDING"))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("application/hacker/submit");

		result.addObject("applicationForm", this.applicationService.deconstruct(application));

		return result;
	}

	@RequestMapping(value = "/hacker/submit", method = RequestMethod.POST)
	public ModelAndView submit(@Valid @ModelAttribute("applicationForm") final ApplicationForm applicationForm, final BindingResult bindingResult) {
		final ModelAndView result;
		final Hacker hacker;
		Application application;

		hacker = this.hackerService.findPrincipal();
		if (hacker == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(applicationForm.getId());
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getHacker().equals(hacker))
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getStatus().equals("PENDING"))
			return new ModelAndView("redirect:/welcome/index.do");
		if (!bindingResult.hasErrors()) {
			application = this.applicationService.reconstructForm(applicationForm, bindingResult);
			application.setStatus("SUBMITTED");
			application = this.applicationService.save(application);
			result = this.hackerShow(application.getId());
		} else {
			result = new ModelAndView("application/hacker/submit");
			result.addObject("applicationForm", applicationForm);
		}

		return result;
	}

	// Company list -----------------------------------------------------------

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView companyList(@RequestParam(value = "positionId") final int positionId) {
		final ModelAndView result;
		final Company company;
		final Position position;
		final Map<String, List<Application>> applications;

		company = this.companyService.findPrincipal();
		if (company == null)
			return new ModelAndView("redirect:/welcome/index.do");
		position = this.positionService.findOne(positionId);
		if (position == null)
			return new ModelAndView("redirect:/welcome/index.do");
		applications = ApplicationService.groupByStatus(this.applicationService.getApplicationsOfPosition(position));

		result = new ModelAndView("application/list");
		result.addObject("pendingApplications", applications.get("PENDING"));
		result.addObject("submittedApplications", applications.get("SUBMITTED"));
		result.addObject("rejectedApplications", applications.get("REJECTED"));
		result.addObject("acceptedApplications", applications.get("ACCEPTED"));

		return result;
	}

	// Company show -----------------------------------------------------------

	@RequestMapping(value = "/company/show", method = RequestMethod.GET)
	public ModelAndView companyShow(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final Company company;
		final Application application;

		company = this.companyService.findPrincipal();
		if (company == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(id);
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getPosition().getCompany().equals(company))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("application/show");

		result.addObject("application", application);

		return result;
	}

	// Accept -----------------------------------------------------------------

	@RequestMapping(value = "/company/accept", method = RequestMethod.POST)
	public ModelAndView accept(@RequestParam(value = "id") final int id) {
		final Company company;
		final Application application;

		company = this.companyService.findPrincipal();
		if (company == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(id);
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getPosition().getCompany().equals(company))
			return new ModelAndView("redirect:/welcome/index.do");

		application.setStatus("ACCEPTED");
		this.applicationService.save(application);

		return this.companyShow(id);
	}

	// Reject -----------------------------------------------------------------

	@RequestMapping(value = "/company/reject", method = RequestMethod.POST)
	public ModelAndView reject(@RequestParam(value = "id") final int id) {
		final Company company;
		final Application application;

		company = this.companyService.findPrincipal();
		if (company == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(id);
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getPosition().getCompany().equals(company))
			return new ModelAndView("redirect:/welcome/index.do");

		application.setStatus("REJECTED");
		this.applicationService.save(application);

		return this.companyShow(id);
	}

}
