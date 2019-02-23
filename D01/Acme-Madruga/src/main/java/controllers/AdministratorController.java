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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigurationService;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Utility methods -------------------------------------------------------------

	private static String join(final Collection<String> strings) {
		String result = "";
		for (final String string : strings)
			result = result + string + ",";
		return result.substring(0, result.length() - 1);
	}

	private static List<String> breakUp(final String string) {
		final List<String> result = new ArrayList<>();
		for (final String s : string.split(","))
			result.add(s);
		return result;
	}

	// System configuration --------------------------------------------------------	

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.GET)
	public ModelAndView systemConfiguration() {
		final ModelAndView result;
		final SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService.getSystemConfiguration();

		result = new ModelAndView("administrator/systemconfiguration");
		result.addObject("defaultCountryCode", systemConfiguration.getDefaultCountryCode());
		result.addObject("positions", AdministratorController.join(systemConfiguration.getPositions()));
		result.addObject("systemName", systemConfiguration.getSystemName());
		result.addObject("banner", systemConfiguration.getBanner());
		result.addObject("welcomeMessage", systemConfiguration.getWelcomeMessage());
		result.addObject("welcomeMessageEs", systemConfiguration.getWelcomeMessageEs());
		result.addObject("finderDuration", systemConfiguration.getFinderDuration());
		result.addObject("maximumFinderResults", systemConfiguration.getMaximumFinderResults());
		result.addObject("additionalPriorities", AdministratorController.join(systemConfiguration.getAdditionalPriorities()));
		result.addObject("positiveWords", AdministratorController.join(systemConfiguration.getPositiveWords()));
		result.addObject("negativeWords", AdministratorController.join(systemConfiguration.getNegativeWords()));
		result.addObject("spamWords", AdministratorController.join(systemConfiguration.getSpamWords()));
		return result;
	}

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.POST)
	public ModelAndView systemConfiguration(@RequestParam(value = "defaultCountryCode") final String defaultCountryCode, @RequestParam(value = "positions") final String positions, @RequestParam(value = "systemName") final String systemName, @RequestParam(
		value = "banner") final String banner, @RequestParam(value = "welcomeMessage") final String welcomeMessage, @RequestParam(value = "welcomeMessageEs") final String welcomeMessageEs, @RequestParam(value = "finderDuration") final int finderDuration,
		@RequestParam(value = "maximumFinderResults") final int maximumFinderResults, @RequestParam(value = "additionalPriorities") final String additionalPriorities, @RequestParam(value = "positiveWords") final String positiveWords, @RequestParam(
			value = "negativeWords") final String negativeWords, @RequestParam(value = "spamWords") final String spamWords) {
		final ModelAndView result;
		final SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService.getSystemConfiguration();
		systemConfiguration.setDefaultCountryCode(defaultCountryCode);
		systemConfiguration.setPositions(AdministratorController.breakUp(positions));
		systemConfiguration.setSystemName(systemName);
		systemConfiguration.setBanner(banner);
		systemConfiguration.setWelcomeMessage(welcomeMessage);
		systemConfiguration.setWelcomeMessageEs(welcomeMessageEs);
		systemConfiguration.setFinderDuration(finderDuration);
		systemConfiguration.setMaximumFinderResults(maximumFinderResults);
		systemConfiguration.setAdditionalPriorities(AdministratorController.breakUp(additionalPriorities));
		systemConfiguration.setPositiveWords(AdministratorController.breakUp(positiveWords));
		systemConfiguration.setNegativeWords(AdministratorController.breakUp(negativeWords));
		systemConfiguration.setSpamWords(AdministratorController.breakUp(spamWords));
		this.systemConfigurationService.save(systemConfiguration);

		result = this.systemConfiguration();

		return result;
	}

}
