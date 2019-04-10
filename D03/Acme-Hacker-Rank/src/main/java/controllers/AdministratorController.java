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

import services.SystemConfigurationService;
import domain.SystemConfiguration;
import forms.SystemConfigurationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


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
