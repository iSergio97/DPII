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

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.AuditService;
import services.PositionService;
import services.SystemConfigurationService;
import domain.Company;
import domain.SystemConfiguration;
import forms.SystemConfigurationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private ApplicationService			applicationService;
	@Autowired
	private AuditService				auditService;
	@Autowired
	private PositionService				positionService;
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

	// Dashboard -------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result = new ModelAndView("administrator/dashboard");

		result.addObject("minC", this.positionService.min());
		result.addObject("maxC", this.positionService.max());
		result.addObject("avgC", this.positionService.media());
		result.addObject("stdDevC", this.positionService.stdDev());

		result.addObject("minA", this.applicationService.min());
		result.addObject("maxA", this.applicationService.max());
		result.addObject("avgA", this.applicationService.media());
		result.addObject("stdDevA", this.applicationService.stdDev());

		result.addObject("minPositionAuditScore", this.auditService.getMinimumScorePosition());
		result.addObject("maxPositionAuditScore", this.auditService.getMaximumScorePosition());
		result.addObject("avgPositionAuditScore", this.auditService.getAverageScorePosition());
		result.addObject("stdDevPositionAuditScore", this.auditService.getStandardDeviationScorePosition());

		result.addObject("minCompanyAuditScore", this.auditService.getMinimumScorePosition());
		result.addObject("maxCompanyAuditScore", this.auditService.getMaximumScorePosition());
		result.addObject("avgCompanyAuditScore", this.auditService.getAverageScorePosition());
		result.addObject("stdDevCompanyAuditScore", this.auditService.getStandardDeviationScorePosition());

		final Map<Company, Double> companiesWithTheHighestAuditScoreAndTheirAverageSalary = this.auditService.getCompaniesWithTheHighestAuditScoreAndTheirAverageSalary(3);
		result.addObject("companiesWithTheHighestAuditScoreAndTheirAverageSalary", companiesWithTheHighestAuditScoreAndTheirAverageSalary);
		result.addObject("companiesWithTheHighestAuditScore", companiesWithTheHighestAuditScoreAndTheirAverageSalary.keySet());

		final Map<Company, Double> companiesAndTheirScore = this.auditService.getScoresByCompany();
		result.addObject("companiesAndTheirScore", companiesAndTheirScore);
		result.addObject("companies", companiesAndTheirScore.keySet());

		return result;
	}

}
