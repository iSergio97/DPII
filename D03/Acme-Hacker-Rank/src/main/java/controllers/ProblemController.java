
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.PositionService;
import services.ProblemService;
import domain.Position;
import domain.Problem;
import forms.ProblemForm;

@Controller
@RequestMapping("/problem")
public class ProblemController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private PositionService	positionService;


	// Constructors -----------------------------------------------

	public ProblemController() {

	}

	// List ------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;
		Collection<Problem> problems;

		position = this.positionService.findOne(positionId);
		problems = position.getProblems();

		result = new ModelAndView("problem/list");
		result.addObject("problems", problems);
		result.addObject("requestURI", "problem/list.do");

		return result;
	}

	// Show -----------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;

		problem = this.problemService.findOne(problemId);
		Assert.notNull(problem);

		result = new ModelAndView("/problem/show");

		result.addObject("problem", problem);

		return result;
	}

	// Create --------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ProblemForm problem;

		problem = this.problemService.createForm();
		result = this.createAndEditModelAndView(problem);

		return result;
	}

	// Edit ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;

		problem = this.problemService.findOne(problemId);
		Assert.notNull(problem);
		Assert.isTrue(problem.getIsDraft());

		result = new ModelAndView("/problem/edit");
		result.addObject("problem", problem);

		return result;
	}

	// Save ---------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("problem") final ProblemForm problem, final BindingResult binding) {
		ModelAndView result;
		Problem problem2;

		problem2 = this.problemService.reconstruct(problem, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(problem);
		else
			try {
				problem2.setIsDraft(true);
				final Problem p = this.problemService.save(problem2);
				result = this.show(p.getId());
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(problem, "problem.commit.error");
			}

		return result;
	}

	// Save in final mode -------------------------------------

	@RequestMapping(value = "/edit", params = "finalMode", method = RequestMethod.POST)
	public ModelAndView saveFinal(@ModelAttribute("problem") final ProblemForm problem, final BindingResult binding) {
		ModelAndView result;
		Problem problem2;

		problem2 = this.problemService.reconstruct(problem, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(problem);
		else
			try {
				problem2.setIsDraft(false);
				final Problem p = this.problemService.save(problem2);
				result = new ModelAndView("redirect:/problem/show.do?problemId=" + p.getId());
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(problem, "problem.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProblemForm problemForm, final BindingResult binding) {
		ModelAndView result;
		Problem problem;

		problem = this.problemService.reconstruct(problemForm, binding);
		if (!problem.getIsDraft())
			result = this.createAndEditModelAndView(problemForm);
		else
			try {
				this.problemService.delete(problem);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(problemForm, "parade.commit.error");
			}
		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final ProblemForm problem) {
		ModelAndView result;

		result = this.createAndEditModelAndView(problem, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final ProblemForm problem, final String message) {
		final ModelAndView result;

		result = new ModelAndView("/problem/create");
		result.addObject("problem", problem);
		result.addObject("message", message);

		return result;
	}

}
