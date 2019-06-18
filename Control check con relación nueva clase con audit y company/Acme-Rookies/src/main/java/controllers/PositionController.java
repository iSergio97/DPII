
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuditService;
import services.CompanyService;
import services.PositionService;
import services.SponsorshipService;
import domain.Company;
import domain.Position;
import domain.Problem;
import domain.Sponsorship;
import forms.PositionForm;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService		auditService;
	@Autowired
	private CompanyService		companyService;
	@Autowired
	private PositionService		positionService;
	@Autowired
	private SponsorshipService	sponsorshipService;


	// Constructor ------------------------------------------------------------

	public PositionController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/company/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PositionForm positionForm;
		positionForm = this.positionService.createForm();

		result = this.createEditModelAndView(positionForm);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int positionId) {
		final ModelAndView res;
		final Position pos = this.positionService.findOne(positionId);
		if (!(this.companyService.findPrincipal() == null) || !(pos.getCompany() != this.companyService.findPrincipal())) {
			final PositionForm posForm = this.positionService.deconstruct(pos);
			res = this.createEditModelAndView(posForm);
		} else
			res = this.createModelAndViewWithSystemConfiguration("redirect:../welcome/index.do");

		return res;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("position") final PositionForm positionForm, final BindingResult bindingResult) {
		ModelAndView result;
		Position pos;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(positionForm);
		else
			try {
				pos = this.positionService.reconstruct(positionForm, bindingResult);
				final Company company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
				if (company != null && (pos.getCompany().equals(company) && pos.getIsDraft()))
					this.positionService.save(pos);
				result = this.createModelAndViewWithSystemConfiguration("redirect:list.do");
			} catch (final ValidationException valExp) {
				result = this.createEditModelAndView(positionForm);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(positionForm, "commit.error");
			}
		return result;
	}

	// Final -------------------------------------------------------------------

	@RequestMapping(value = "/company/final", method = RequestMethod.GET)
	public ModelAndView finalMode(final int positionId) {
		final Position pos = this.positionService.findOne(positionId);
		if (pos != null && pos.getCompany() == this.companyService.findPrincipal() && pos.getProblems().size() > 1) {
			pos.setIsDraft(false);
			pos.setStatus("ACCEPTED");
			this.positionService.save(pos);
		}
		return this.createModelAndViewWithSystemConfiguration("redirect:list.do");
	}

	// Cancel -------------------------------------------------------------------

	@RequestMapping(value = "/company/cancel", method = RequestMethod.GET)
	public ModelAndView cancelStatus(final int positionId) {
		final Position pos = this.positionService.findOne(positionId);
		if (pos != null && pos.getStatus().equals("ACCEPTED") && pos.getCompany() == this.companyService.findPrincipal()) {
			pos.setStatus("CANCELLED");
			this.positionService.save(pos);
		}
		return this.createModelAndViewWithSystemConfiguration("redirect:list.do");
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/company/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		ModelAndView result = this.createModelAndViewWithSystemConfiguration("redirect:list.do");
		Position position;
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findByPositionId(positionId);

		final List<Sponsorship> list = new ArrayList<>(sponsorships);
		final Random rand = new Random();
		final int random = rand.nextInt(list.size());
		final String banner = list.get(random).getBanner();

		final String locale = Locale.getDefault().getLanguage();
		final Company company = this.companyService.findPrincipal();
		position = this.positionService.findOne(positionId);
		if (position != null && position.getCompany() == company) {
			result = this.createModelAndViewWithSystemConfiguration("position/company/show");
			result.addObject("position", position);
			result.addObject("draft", position.getIsDraft());
			result.addObject("problems", position.getProblems());
			result.addObject("locale", locale);
			result.addObject("banner", banner);
		}

		return result;
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Company company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Position> pos = this.positionService.findPositionsByCompany(company);

		result = this.createModelAndViewWithSystemConfiguration("position/company/list");

		result.addObject("positions", pos);

		return result;
	}

	// Public list ------------------------------------------------------------

	@RequestMapping(value = "/all/list", method = RequestMethod.GET)
	public ModelAndView publicList() {
		ModelAndView res;
		final Collection<Position> positions;
		positions = this.positionService.findAll();
		res = this.createModelAndViewWithSystemConfiguration("position/all/list");
		res.addObject("positions", positions);

		return res;
	}

	@RequestMapping(value = "/all/list", method = RequestMethod.POST)
	public ModelAndView publicList(@RequestParam final String keyword) {
		ModelAndView res;
		final Collection<Position> positions;
		if (keyword == "")
			positions = this.positionService.findAll();
		else
			positions = this.positionService.searchQuery(keyword);
		res = this.createModelAndViewWithSystemConfiguration("position/all/list");
		res.addObject("positions", positions);

		return res;
	}

	// Public show ------------------------------------------------------------

	@RequestMapping(value = "/all/show-company", method = RequestMethod.GET)
	public ModelAndView publicShow(@RequestParam final int positionId) {
		ModelAndView res;
		final Position p = this.positionService.findOne(positionId);
		final Company company = p.getCompany();
		res = this.createModelAndViewWithSystemConfiguration("position/all/show-company");
		res.addObject("company", company);
		return res;
	}

	// Public query for public positions list

	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PositionForm position) {

		return this.createEditModelAndView(position, null);
	}

	protected ModelAndView createEditModelAndView(final PositionForm position, final String message) {
		ModelAndView result;
		final Collection<Problem> problems;

		final Company company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
		problems = this.positionService.findProblemsByCompany(company);

		result = this.createModelAndViewWithSystemConfiguration("position/company/create");
		result.addObject("problems", problems);
		result.addObject("message", message);
		result.addObject("position", position);

		return result;

	}

}
