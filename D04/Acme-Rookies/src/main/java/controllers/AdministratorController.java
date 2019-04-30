/*
 * AdministratorController.java
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.SystemConfiguration;
import forms.SystemConfigurationForm;
import services.ApplicationService;
import services.PositionService;
import services.SystemConfigurationService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private PositionService				positionService;

	@Autowired
	private ApplicationService			applicationService;


	// Constructors ----------------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// System configuration --------------------------------------------------------

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.GET)
	public ModelAndView systemConfiguration() {
		final ModelAndView result;
		final SystemConfigurationForm systemConfigurationForm;

		systemConfigurationForm = this.systemConfigurationService.deconstruct(this.systemConfigurationService.getSystemConfiguration());

		result = new ModelAndView("administrator/systemconfiguration");
		result.addObject("systemConfigurationForm", systemConfigurationForm);

		return result;
	}

	// Dashboard -------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result;

		result = new ModelAndView("administrator/dashboard");
		final Double minCompany = this.positionService.min();
		final Double maxCompany = this.positionService.max();
		final Double avgCompany = this.positionService.media();
		final Double stdDevCompany = this.positionService.stdDev();
		//final Company companyMax = this.positionService.companyMax();

		result.addObject("minC", minCompany);
		result.addObject("maxC", maxCompany);
		result.addObject("avgC", avgCompany);
		result.addObject("stdDevC", stdDevCompany);
		//result.addObject("companyMax", companyMax);

		final Double minApplication = this.applicationService.min();
		final Double maxApplication = this.applicationService.max();
		final Double avgApplication = this.applicationService.media();
		final Double stdDevApplication = this.applicationService.stdDev();
		//final Rookie rookieMax = this.applicationService.rookieMax();

		result.addObject("minA", minApplication);
		result.addObject("maxA", maxApplication);
		result.addObject("avgA", avgApplication);
		result.addObject("stdDevA", stdDevApplication);
		//result.addObject("rookieMax", rookieMax);

		return result;
	}

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.POST)
	public ModelAndView systemConfiguration(@Valid @ModelAttribute("systemConfigurationForm") final SystemConfigurationForm systemConfigurationForm, final BindingResult bindingResult) {
		final ModelAndView result;
		SystemConfiguration systemConfiguration;

		if (bindingResult.hasErrors()) {
			result = new ModelAndView("administrator/systemconfiguration");
			result.addObject("systemConfigurationForm", systemConfigurationForm);
			result.addObject("error", true);
		} else {
			systemConfiguration = this.systemConfigurationService.reconstruct(systemConfigurationForm, bindingResult);
			this.systemConfigurationService.save(systemConfiguration);
			result = new ModelAndView("administrator/systemconfiguration");
			result.addObject("systemConfigurationForm", systemConfigurationForm);
			result.addObject("success", true);
		}

		return result;
	}

}
