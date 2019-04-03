
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
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CompanyService;
import services.PositionService;
import domain.Company;
import domain.Position;
import domain.Problem;
import forms.PositionForm;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	// Services
	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private ProblemService	problemService;


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
		final ModelAndView result;
		Position pos;

		try {
			pos = this.positionService.reconstruct(positionForm, bindingResult);
			final Collection<Problem> test = new ArrayList<Problem>();
			//Problem problem = this.systemConfigurationService.getGenericProblem();
			//test.add(problem)
			pos.setProblems(test);

			this.positionService.save(pos);
		} catch (final ValidationException valExp) {
			result = this.createEditModelAndView(positionForm);
		} catch (final Throwable oops) {
			this.createEditModelAndView(positionForm, "commit.error");
		}
	}
	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PositionForm position) {
		ModelAndView result;

		result = this.createEditModelAndView(position, null);
	}

	protected ModelAndView createEditModelAndView(final PositionForm position, final String message) {
		ModelAndView result;
		Collection<Problem> problems;

		final Company company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
		problems = this.problemService.findAll();

		result = new ModelAndView("position/company/create");
		result.addObject("problems", problems);
		result.addObject("message", message);

		return result;

	}

}
