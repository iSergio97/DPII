
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Company;
import domain.Position;
import domain.Problem;
import forms.PositionForm;
import security.LoginService;
import services.CompanyService;
import services.PositionService;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	// Services
	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;

	//@Autowired
	//private ProblemService	problemService;


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

	@RequestMapping(value = "company/edit", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("position") final PositionForm positionForm, final BindingResult bindingResult) {
		ModelAndView result;
		Position pos;
		positionForm.setSalary(Double.valueOf(positionForm.getSalary()));

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(positionForm);
		else
			try {
				pos = this.positionService.reconstruct(positionForm, bindingResult);
				final Collection<Problem> test = new ArrayList<Problem>();
				final Company company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
				if (pos.getProblems().size() < 2)
					pos.setDraft(true);
				if (company != null && pos.isDraft()) {
					pos.setProblems(test);
					pos.setCompany(company);
					this.positionService.save(pos);
				}
				result = new ModelAndView("redirect:company/list.do");
			} catch (final ValidationException valExp) {
				result = this.createEditModelAndView(positionForm);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(positionForm, "commit.error");
			}
		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/company/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;
		result = new ModelAndView("position/company/show");
		position = this.positionService.findOne(positionId);

		result.addObject("position", position);

		return result;
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Company company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Problem> problems = this.positionService.findProblemsByCompany(company);

		result = new ModelAndView("position/company/list");

		result.addObject("problems", problems);

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PositionForm position) {

		return this.createEditModelAndView(position, null);
	}

	protected ModelAndView createEditModelAndView(final PositionForm position, final String message) {
		ModelAndView result;
		final Collection<Problem> problems;

		final Company company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
		//TODO: Hacer query que obtenga los problemas de una compa��a y se le asigne a la position
		problems = this.positionService.findProblemsByCompany(company);

		result = new ModelAndView("position/company/create");
		result.addObject("problems", problems);
		result.addObject("message", message);
		result.addObject("position", position);

		return result;

	}

}