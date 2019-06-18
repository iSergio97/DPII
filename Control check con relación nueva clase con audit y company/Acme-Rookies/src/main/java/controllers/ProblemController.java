
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

		result = this.createModelAndViewWithSystemConfiguration("problem/list");
		result.addObject("problems", problems);
		result.addObject("positionId", positionId);
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

		result = this.createModelAndViewWithSystemConfiguration("/problem/show");

		result.addObject("problem", problem);

		return result;
	}

	// Create --------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		ModelAndView result;
		ProblemForm problem;
		Problem p;

		p = this.problemService.create();
		problem = this.problemService.createForm(p);
		problem.setPositionId(positionId);
		result = this.createAndEditModelAndView(problem);

		return result;
	}

	// Edit ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;
		ProblemForm form;
		final Position p = this.problemService.findPositionAssociated(problemId);

		problem = this.problemService.findOne(problemId);
		form = this.problemService.createForm(problem);
		form.setPositionId(p.getId());

		Assert.isTrue(problem.getIsDraft());

		result = this.createModelAndViewWithSystemConfiguration("/problem/edit");
		result.addObject("problem", form);

		return result;
	}

	// Save ---------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("problem") final ProblemForm problem, final BindingResult binding) {
		ModelAndView result;
		Problem problem2;
		final Position pos = this.positionService.findOne(problem.getPositionId());

		problem2 = this.problemService.reconstruct(problem, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(problem);
		else
			try {
				problem2.setIsDraft(true);
				final Problem p = this.problemService.save(problem2);
				pos.getProblems().add(p);
				final Position newPos = this.positionService.save(pos);
				result = this.list(newPos.getId());
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
		final Position pos = this.positionService.findOne(problem.getPositionId());

		problem2 = this.problemService.reconstruct(problem, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(problem);
		else
			try {
				problem2.setIsDraft(false);
				final Problem p = this.problemService.save(problem2);
				pos.getProblems().add(p);
				final Position newPos = this.positionService.save(pos);
				result = this.list(newPos.getId());
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(problem, "problem.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("problem") final ProblemForm problemForm, final BindingResult binding) {
		ModelAndView result;
		Problem problem;

		problem = this.problemService.reconstruct(problemForm, binding);
		if (!problem.getIsDraft())
			result = this.createAndEditModelAndView(problemForm);
		else
			try {
				final Position p = this.problemService.findPositionAssociated(problem.getId());
				p.getProblems().remove(problem);
				this.positionService.save(p);
				this.problemService.delete(problem);
				result = this.createModelAndViewWithSystemConfiguration("redirect:/problem/list.do?positionId=" + p.getId());

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

		result = this.createModelAndViewWithSystemConfiguration("/problem/create");
		result.addObject("problem", problem);
		result.addObject("message", message);

		return result;
	}

}
