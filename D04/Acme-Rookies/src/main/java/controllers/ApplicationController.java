
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

import domain.Application;
import domain.Company;
import domain.Curriculum;
import domain.Position;
import domain.Problem;
import domain.Rookie;
import forms.ApplicationForm;
import services.ApplicationService;
import services.CompanyService;
import services.CurriculumService;
import services.PositionService;
import services.RookieService;

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
	private RookieService		rookieService;
	@Autowired
	private PositionService		positionService;


	// Constructors -----------------------------------------------------------

	public ApplicationController() {
		super();
	}

	// Rookie list ------------------------------------------------------------

	@RequestMapping(value = "/rookie/list", method = RequestMethod.GET)
	public ModelAndView rookieList() {
		final ModelAndView result;
		final Rookie rookie;
		final Map<String, List<Application>> applications;

		rookie = this.rookieService.findPrincipal();
		if (rookie == null)
			return new ModelAndView("redirect:/welcome/index.do");
		applications = ApplicationService.groupByStatus(this.applicationService.getApplicationsOfRookie(rookie));

		result = new ModelAndView("application/list");
		result.addObject("pendingApplications", applications.get("PENDING"));
		result.addObject("submittedApplications", applications.get("SUBMITTED"));
		result.addObject("rejectedApplications", applications.get("REJECTED"));
		result.addObject("acceptedApplications", applications.get("ACCEPTED"));

		return result;
	}

	// Rookie show ------------------------------------------------------------

	@RequestMapping(value = "/rookie/show", method = RequestMethod.GET)
	public ModelAndView rookieShow(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final Rookie rookie;
		final Application application;

		rookie = this.rookieService.findPrincipal();
		if (rookie == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(id);
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getRookie().equals(rookie))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("application/show");

		result.addObject("application", application);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/rookie/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "positionId") final int positionId) {
		final ModelAndView result;
		final Rookie rookie;
		final Position position;

		rookie = this.rookieService.findPrincipal();
		if (rookie == null)
			return new ModelAndView("redirect:/welcome/index.do");
		position = this.positionService.findOne(positionId);
		if (position == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!position.getStatus().equals("ACCEPTED"))
			return new ModelAndView("redirect:/welcome/index.do");
		result = new ModelAndView("application/rookie/create");

		final Collection<Curriculum> curricula = this.curriculumService.findCurriculaByRookie(rookie);
		if (curricula.size() == 0)
			return new ModelAndView("redirect:/welcome/index.do");

		final Map<Integer, String> curriculaMap = new HashMap<>();
		for (final Curriculum curriculum : curricula)
			curriculaMap.put(curriculum.getId(), curriculum.getPersonalData().getStatement());
		result.addObject("curriculaMap", curriculaMap);
		result.addObject("position", position);

		return result;
	}

	@RequestMapping(value = "/rookie/create", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam(value = "positionId") final int positionId, @RequestParam(value = "curriculumId") final int curriculumId) {
		Application application;
		Curriculum curriculum;
		final Rookie rookie;
		Position position;
		Problem problem;

		rookie = this.rookieService.findPrincipal();
		if (rookie == null)
			return new ModelAndView("redirect:/welcome/index.do");
		position = this.positionService.findOne(positionId);
		if (position == null)
			return new ModelAndView("redirect:/welcome/index.do");
		curriculum = this.curriculumService.findOne(curriculumId);
		if (curriculum == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!curriculum.getRookie().equals(rookie))
			return new ModelAndView("redirect:/welcome/index.do");

		// Copy curriculum and set rookie to null so it doesn't show up in the rookie's curriculums
		curriculum = this.curriculumService.copy(curriculum);
		curriculum.setRookie(null);
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
		application.setRookie(rookie);
		application = this.applicationService.save(application);

		return this.rookieShow(application.getId());
	}

	// Submit -----------------------------------------------------------------

	@RequestMapping(value = "/rookie/submit", method = RequestMethod.GET)
	public ModelAndView submit(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final Rookie rookie;
		final Application application;

		rookie = this.rookieService.findPrincipal();
		if (rookie == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(id);
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getRookie().equals(rookie))
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getStatus().equals("PENDING"))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("application/rookie/submit");

		result.addObject("applicationForm", this.applicationService.deconstruct(application));

		return result;
	}

	@RequestMapping(value = "/rookie/submit", method = RequestMethod.POST)
	public ModelAndView submit(@Valid @ModelAttribute("applicationForm") final ApplicationForm applicationForm, final BindingResult bindingResult) {
		final ModelAndView result;
		final Rookie rookie;
		Application application;

		rookie = this.rookieService.findPrincipal();
		if (rookie == null)
			return new ModelAndView("redirect:/welcome/index.do");
		application = this.applicationService.findOne(applicationForm.getId());
		if (application == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getRookie().equals(rookie))
			return new ModelAndView("redirect:/welcome/index.do");
		if (!application.getStatus().equals("PENDING"))
			return new ModelAndView("redirect:/welcome/index.do");
		if (!bindingResult.hasErrors()) {
			application = this.applicationService.reconstructForm(applicationForm, bindingResult);
			application.setStatus("SUBMITTED");
			application = this.applicationService.save(application);
			result = this.rookieShow(application.getId());
		} else {
			result = new ModelAndView("application/rookie/submit");
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
