
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import services.PhaseService;
import services.WorkPlanService;
import domain.FixUpTask;
import domain.Phase;
import domain.WorkPlan;

@Controller
@RequestMapping("/workPlan/handyWorker")
public class WorkplanController extends AbstractController {

	@Autowired
	private WorkPlanService		workPlanService;

	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int workPlanId) {
		// Create result object
		ModelAndView result;
		Collection<Phase> phases;

		result = new ModelAndView("phase/list");

		final WorkPlan workPlan = this.workPlanService.findById(2779);
		phases = workPlan.getPhases();
		result.addObject("phases", phases);
		result.addObject("workPlan", workPlan);
		result.addObject("requestURI", "phase/handyWorker/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		WorkPlan workplan;
		final FixUpTask fixUpTask = this.fixUpTaskService.findById(fixUpTaskId);
		workplan = this.workPlanService.create();
		workplan.setFixUpTask(fixUpTask);
		fixUpTask.setWorkPlan(workplan);
		this.fixUpTaskService.save(fixUpTask);

		result = this.createEditModelAndView(workplan);

		return result;
	}

	//Este metodo es un bypass a un segundo meodo con 2 valores de entrada
	protected ModelAndView createEditModelAndView(final WorkPlan workplan) {

		ModelAndView result;
		result = this.createEditModelAndView(workplan, null);

		return result;
	}

	//Este metodo construye el objeto modelandview en funcion del finder de entrada y los mensajes de error
	protected ModelAndView createEditModelAndView(final WorkPlan workplan, final String messageCode) {

		ModelAndView result;

		Collection<Phase> phases;

		phases = workplan.getPhases();

		result = new ModelAndView("phase/list");
		result.addObject("workPlan", workplan);
		result.addObject("phases", phases);

		return result;
	}

}
