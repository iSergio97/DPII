/*
 * AbstractController.java
 * 
 * Copyright (C) 2019 Group 16 Desing & Testing II
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigurationService;

@Controller
public class AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Model and View methods -------------------------------------------------

	public ModelAndView createModelAndViewWithSystemConfiguration(final String viewName) {
		final ModelAndView result = new ModelAndView(viewName);
		result.addObject("systemConfiguration", this.systemConfigurationService.getSystemConfiguration());
		return result;
	}

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = this.createModelAndViewWithSystemConfiguration("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

}
