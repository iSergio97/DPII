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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import security.UserAccountRepository;
import services.AdministratorService;
import services.AreaService;
import services.MessageBoxService;
import services.PositionService;
import services.PriorityService;
import services.SystemConfigurationService;
import domain.Administrator;
import domain.Area;
import domain.MessageBox;
import domain.Position;
import domain.Priority;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private AdministratorService		administratorService;
	@Autowired
	private AreaService					areaService;
	@Autowired
	private MessageBoxService			messageBoxService;
	@Autowired
	private PositionService				positionService;
	@Autowired
	private PriorityService				priorityService;
	@Autowired
	private SystemConfigurationService	systemConfigurationService;
	@Autowired
	private UserAccountRepository		userAccountRepository;


	// Constructors ----------------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Utility methods -------------------------------------------------------------

	private static String listToString(final List<String> list) {
		String result = "";
		for (final String string : list)
			result = result + string + ",";
		return result.substring(0, result.length() - 1);
	}

	private static List<String> stringToList(final String string) {
		final List<String> result = new ArrayList<>();
		for (final String s : string.split(","))
			result.add(s);
		return result;
	}

	private static String mapToString(final Map<String, String> map) {
		String result = "";
		for (final Entry<String, String> entry : map.entrySet())
			result = result + entry.getKey() + ":" + entry.getValue() + ";";
		return result.substring(0, result.length() - 1);
	}

	private static Map<String, String> stringToMap(final String string) {
		final Map<String, String> result = new HashMap<>();
		for (final String entry : string.split(";")) {
			final String[] pair = entry.split(":");
			result.put(pair[0], pair[1]);
		}
		return result;
	}

	// System configuration --------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		// TODO: add queries

		return result;
	}

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.GET)
	public ModelAndView systemConfiguration() {
		final ModelAndView result;
		final SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService.getSystemConfiguration();

		result = new ModelAndView("administrator/systemconfiguration");
		result.addObject("defaultCountryCode", systemConfiguration.getDefaultCountryCode());
		result.addObject("systemName", systemConfiguration.getSystemName());
		result.addObject("banner", systemConfiguration.getBanner());
		result.addObject("finderDuration", systemConfiguration.getFinderDuration());
		result.addObject("maximumFinderResults", systemConfiguration.getMaximumFinderResults());
		result.addObject("positiveWords", AdministratorController.listToString(systemConfiguration.getPositiveWords()));
		result.addObject("negativeWords", AdministratorController.listToString(systemConfiguration.getNegativeWords()));
		result.addObject("spamWords", AdministratorController.listToString(systemConfiguration.getSpamWords()));
		result.addObject("welcomeMessages", AdministratorController.mapToString(systemConfiguration.getWelcomeMessages()));
		return result;
	}

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.POST)
	public ModelAndView systemConfiguration(@RequestParam(value = "defaultCountryCode") final String defaultCountryCode, @RequestParam(value = "systemName") final String systemName, @RequestParam(value = "banner") final String banner, @RequestParam(
		value = "finderDuration") final int finderDuration, @RequestParam(value = "maximumFinderResults") final int maximumFinderResults, @RequestParam(value = "positiveWords") final String positiveWords,
		@RequestParam(value = "negativeWords") final String negativeWords, @RequestParam(value = "spamWords") final String spamWords, @RequestParam(value = "welcomeMessages") final String welcomeMessages) {
		final SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService.getSystemConfiguration();
		systemConfiguration.setDefaultCountryCode(defaultCountryCode);
		systemConfiguration.setSystemName(systemName);
		systemConfiguration.setBanner(banner);
		systemConfiguration.setFinderDuration(finderDuration);
		systemConfiguration.setMaximumFinderResults(maximumFinderResults);
		systemConfiguration.setPositiveWords(AdministratorController.stringToList(positiveWords));
		systemConfiguration.setNegativeWords(AdministratorController.stringToList(negativeWords));
		systemConfiguration.setSpamWords(AdministratorController.stringToList(spamWords));
		systemConfiguration.setWelcomeMessages(AdministratorController.stringToMap(welcomeMessages));
		this.systemConfigurationService.save(systemConfiguration);

		return this.systemConfiguration();
	}

	// Area ------------------------------------------------------------------------

	@RequestMapping(value = "/viewareas", method = RequestMethod.GET)
	public ModelAndView viewAreas() {
		final ModelAndView result;

		result = new ModelAndView("administrator/viewareas");
		result.addObject("areas", this.areaService.findAll());

		return result;
	}

	@RequestMapping(value = "/addarea", method = RequestMethod.POST)
	public ModelAndView addArea(@RequestParam(value = "name") final String name, @RequestParam(value = "pictures") final String pictures) {
		final Area area = this.areaService.create();
		area.setName(name);
		area.setPictures(AdministratorController.stringToList(pictures));
		this.areaService.save(area);
		return this.viewAreas();
	}

	@RequestMapping(value = "/editarea", method = RequestMethod.POST)
	public ModelAndView editArea(@RequestParam(value = "id") final int id, @RequestParam(value = "name") final String name, @RequestParam(value = "pictures") final String pictures) {
		final Area area = this.areaService.findOne(id);
		area.setName(name);
		area.setPictures(AdministratorController.stringToList(pictures));
		this.areaService.save(area);
		return this.viewAreas();
	}

	@RequestMapping(value = "/deletearea", method = RequestMethod.POST)
	public ModelAndView deleteArea(@RequestParam(value = "id") final int id) {
		final Area area = this.areaService.findOne(id);
		this.areaService.delete(area);
		return this.viewAreas();
	}

	// Position --------------------------------------------------------------------

	@RequestMapping(value = "/viewpositions", method = RequestMethod.GET)
	public ModelAndView viewPositions() {
		final ModelAndView result;

		result = new ModelAndView("administrator/viewpositions");
		result.addObject("positions", this.positionService.findAll());

		return result;
	}

	@RequestMapping(value = "/addposition", method = RequestMethod.POST)
	public ModelAndView addPosition(@RequestParam(value = "position") final String positionString) {
		final Position position = this.positionService.create();
		position.setStrings(AdministratorController.stringToMap(positionString));
		this.positionService.save(position);
		return this.viewPositions();
	}

	@RequestMapping(value = "/editposition", method = RequestMethod.POST)
	public ModelAndView editPosition(@RequestParam(value = "id") final int id, @RequestParam(value = "position") final String positionString) {
		final Position position = this.positionService.findOne(id);
		position.setStrings(AdministratorController.stringToMap(positionString));
		this.positionService.save(position);
		return this.viewPositions();
	}

	@RequestMapping(value = "/deleteposition", method = RequestMethod.POST)
	public ModelAndView deletePosition(@RequestParam(value = "id") final int id) {
		final Position position = this.positionService.findOne(id);
		this.positionService.delete(position);
		return this.viewPositions();
	}

	// Priority --------------------------------------------------------------------

	@RequestMapping(value = "/viewpriorities", method = RequestMethod.GET)
	public ModelAndView viewPriorities() {
		final ModelAndView result;

		result = new ModelAndView("administrator/viewpriorities");
		result.addObject("priorities", this.priorityService.findAll());

		return result;
	}

	@RequestMapping(value = "/addpriority", method = RequestMethod.POST)
	public ModelAndView addPriority(@RequestParam(value = "priority") final String priorityString) {
		final Priority priority = this.priorityService.create();
		priority.setStrings(AdministratorController.stringToMap(priorityString));
		this.priorityService.save(priority);
		return this.viewPriorities();
	}

	@RequestMapping(value = "/editpriority", method = RequestMethod.POST)
	public ModelAndView editPriority(@RequestParam(value = "id") final int id, @RequestParam(value = "priority") final String priorityString) {
		final Priority priority = this.priorityService.findOne(id);
		priority.setStrings(AdministratorController.stringToMap(priorityString));
		this.priorityService.save(priority);
		return this.viewPriorities();
	}

	@RequestMapping(value = "/deletepriority", method = RequestMethod.POST)
	public ModelAndView deletePriority(@RequestParam(value = "id") final int id) {
		final Priority priority = this.priorityService.findOne(id);
		this.priorityService.delete(priority);
		return this.viewPriorities();
	}

	// Register administrator ------------------------------------------------------

	@RequestMapping(value = "/registeradministrator", method = RequestMethod.GET)
	public ModelAndView registerAdmin() {
		final ModelAndView result;
		final Administrator administrator;

		administrator = this.administratorService.create();
		result = new ModelAndView("administrator/registeradministrator");
		result.addObject("administrator", administrator);

		return result;
	}

	@RequestMapping(value = "/registeradministrator", method = RequestMethod.POST)
	public ModelAndView registerAdmin(Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			UserAccount userAccount = administrator.getUserAccount();
			administrator = this.administratorService.save(administrator);

			final String password = new Md5PasswordEncoder().encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(password);
			userAccount = this.userAccountRepository.save(userAccount);
			administrator.setUserAccount(userAccount);
			administrator = this.administratorService.save(administrator);

			final ArrayList<MessageBox> savedMessageBoxes = new ArrayList<MessageBox>();
			for (MessageBox messageBox : this.messageBoxService.createSystemBoxes()) {
				messageBox.setActor(administrator);
				messageBox = this.messageBoxService.save(messageBox);
				savedMessageBoxes.add(messageBox);
			}
			administrator.setMessageBoxes(savedMessageBoxes);
			administrator = this.administratorService.save(administrator);

			result = new ModelAndView("redirect:show.do");
		} else {
			result = new ModelAndView("administrator/registeradministrator");
			result.addObject("administrator", administrator);
			result.addObject("showError", binding);
			result.addObject("erroresBinding", binding.getAllErrors());
			for (int i = 0; i < binding.getAllErrors().size(); i++)
				System.out.println("Error " + i + ": " + binding.getAllErrors().get(i));
		}

		return result;
	}

}
