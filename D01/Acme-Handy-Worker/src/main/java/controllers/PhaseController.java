/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PhaseService;
import services.WorkPlanService;
import domain.Phase;
import domain.WorkPlan;

@Controller
@RequestMapping("/phase/handyWorker")
public class PhaseController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PhaseService	phaseService;
	@Autowired
	private WorkPlanService	workPlanService;


	// Create/Edit Fix-Up Task ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int workPlanId) {
		// Create result object
		ModelAndView result;
		Collection<Phase> phases;
		WorkPlan workPlan;
		result = new ModelAndView("phase/list");
		workPlan = this.workPlanService.findById(workPlanId);
		phases = workPlan.getPhases();
		result.addObject("phases", phases);
		result.addObject("workPlan", workPlan);
		result.addObject("requestURI", "phase/handyWorker/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int workPlanId) {
		ModelAndView result;
		Phase phase;
		final WorkPlan workPlan = this.workPlanService.findById(workPlanId);
		phase = this.phaseService.create();
		phase.setWorkPlan(workPlan);

		result = this.createEditModelAndView(phase);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		ModelAndView result;
		Phase phase;
		phase = this.phaseService.findById(phaseId);
		Assert.notNull(phase);
		result = this.createEditModelAndView(phase);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else
			try {
				this.phaseService.save(phase);
				final Integer workPlanId = phase.getWorkPlan().getId();
				result = new ModelAndView("redirect:list.do?workPlanId=" + workPlanId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}

		return result;

	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int phaseId) {
		ModelAndView result;
		Phase phase;
		phase = this.phaseService.findById(phaseId);
		try {
			final Integer workPlanId = phase.getWorkPlan().getId();
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:list.do?workPlanId=" + workPlanId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase) {
		final ModelAndView result;
		result = this.createEditModelAndView(phase, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase, final String messageCode) {
		final ModelAndView result;
		final WorkPlan workPlan = phase.getWorkPlan();
		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);
		result.addObject("workPlan", workPlan);
		result.addObject("message", messageCode);

		return result;

	}
}
