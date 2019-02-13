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

import services.CustomerService;
import services.FixUpTaskCategoryService;
import services.FixUpTaskService;
import services.SystemConfigurationService;
import services.WarrantyService;
import services.WorkPlanService;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.FixUpTaskCategory;
import domain.Warranty;

@Controller
@RequestMapping("/fixUpTask")
public class FixUpTaskController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private FixUpTaskCategoryService	fixUpTaskCategoryService;

	@Autowired
	private WarrantyService				warrantyService;

	@Autowired
	private WorkPlanService				workPlanService;

	@Autowired
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Create/Edit Fix-Up Task ------------------------------------------------

	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView list() {
		// Create result object
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		result = new ModelAndView("fixUpTask/list");

		// Add logged customer
		final Customer customer = this.customerService.findPrincipal();
		fixUpTasks = customer.getFixUpTasks();
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("customer", customer);
		result.addObject("requestURI", "fixUpTask/customer/list.do");

		return result;
	}

	@RequestMapping(value = "/handyWorker/list", method = RequestMethod.GET)
	public ModelAndView handyWorkerList() {
		// Create result object
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		result = new ModelAndView("fixUpTask/handyWorker/list");

		// Add logged handyWorker
		fixUpTasks = this.fixUpTaskService.findAll();
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/handyWorker/list.do");

		return result;
	}

	@RequestMapping(value = "/customer/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int fixUpTaskId) {
		// Create result object
		ModelAndView result;
		FixUpTask fixUpTask;
		Collection<Application> applications;
		final Double vat = this.systemConfigurationService.getSystemConfiguration().getVAT();
		result = new ModelAndView("fixUpTask/show");
		fixUpTask = this.fixUpTaskService.findById(fixUpTaskId);
		applications = fixUpTask.getApplications();
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("applications", applications);
		result.addObject("vat", vat);

		return result;
	}
	@RequestMapping(value = "/handyWorker/show", method = RequestMethod.GET)
	public ModelAndView handyWorkerShow(@RequestParam final int fixUpTaskId) {
		// Create result object
		ModelAndView result;
		FixUpTask fixUpTask;
		Collection<Application> applications;
		result = new ModelAndView("fixUpTask/handyWorker/show");
		fixUpTask = this.fixUpTaskService.findById(fixUpTaskId);
		applications = fixUpTask.getApplications();
		final boolean accepted = this.fixUpTaskService.anyApplicationAccepted(fixUpTask);
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("applications", applications);
		result.addObject("accepted", accepted);

		return result;
	}

	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.create();
		result = this.createEditModelAndView(fixUpTask);

		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findById(fixUpTaskId);
		Assert.notNull(fixUpTask);
		result = this.createEditModelAndView(fixUpTask);

		return result;
	}

	@RequestMapping(value = "customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(fixUpTask);
		else
			try {
				this.fixUpTaskService.save(fixUpTask);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error");
			}

		return result;

	}
	@RequestMapping(value = "/customer/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findById(fixUpTaskId);
		try {
			this.fixUpTaskService.delete(fixUpTask);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask) {
		final ModelAndView result;
		result = this.createEditModelAndView(fixUpTask, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask, final String messageCode) {
		final ModelAndView result;
		final Warranty warranty;
		Collection<Warranty> warranties;
		Collection<FixUpTaskCategory> categories;

		warranties = this.warrantyService.findAll();
		categories = this.fixUpTaskCategoryService.findAll();

		result = new ModelAndView("fixUpTask/edit");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("warranties", warranties);
		result.addObject("fixUpTaskCategories", categories);
		result.addObject("message", messageCode);
		return result;

	}
}
