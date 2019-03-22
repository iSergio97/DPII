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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.MessageService;
import services.ParadeService;
import services.PositionService;
import services.PriorityService;
import services.RequestService;
import services.SystemConfigurationService;
import utilities.ConversionUtils;
import domain.Area;
import domain.Position;
import domain.Priority;
import domain.SystemConfiguration;
import forms.SystemConfigurationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private AreaService					areaService;
	@Autowired
	private BrotherhoodService			brotherhoodService;
	@Autowired
	private EnrolmentService			enrolmentService;
	@Autowired
	private MessageService				messageService;
	@Autowired
	private ParadeService				paradeService;
	@Autowired
	private PositionService				positionService;
	@Autowired
	private PriorityService				priorityService;
	@Autowired
	private RequestService				requestService;
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ----------------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Dashboard -------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		// QUERY 1.C.1
		// The average, the minimum, the maximum, and the standard deviation of the number of members per brotherhood.

		final Double[] brotherhoodMemberStatistics = this.brotherhoodService.getMemberStatistics();
		result.addObject("brotherhoodMemberStatisticsMinimum", brotherhoodMemberStatistics[0]);
		result.addObject("brotherhoodMemberStatisticsMaximum", brotherhoodMemberStatistics[1]);
		result.addObject("brotherhoodMemberStatisticsAverage", brotherhoodMemberStatistics[2]);
		result.addObject("brotherhoodMemberStatisticsStandardDeviation", brotherhoodMemberStatistics[3]);

		// QUERY 1.C.2
		// The largest brotherhoods.

		result.addObject("largestBrotherhoods", this.brotherhoodService.findLargestBrotherhoods(3));

		// QUERY 1.C.3
		// The smallest brotherhoods.

		result.addObject("smallestBrotherhoods", this.brotherhoodService.findSmallestBrotherhoods(3));

		// QUERY 1.C.4
		// The ratio of requests to march in a parade, grouped by their status.

		result.addObject("acceptedRequestRatio", this.requestService.getAcceptedRatio());
		result.addObject("rejectedRequestRatio", this.requestService.getRejectedRatio());
		result.addObject("pendingRequestRatio", this.requestService.getPendingRatio());

		// QUERY 1.C.5
		// The parades that are going to be organised in 30 days or less.

		result.addObject("paradesWithin30Days", this.paradeService.findWithin30Days());

		// QUERY 1.C.6
		// The ratio of requests to march grouped by status.

		// QUERY 1.C.7
		// The listing of members who have got at least 10% the maximum number of request to march accepted.

		result.addObject("membersWithAtLeastTenPercentOfTheMaximumNumberOfAcceptedRequests", this.requestService.getMembersWithAtLeastTenPercentOfTheMaximumNumberOfAcceptedRequests());

		// QUERY 1.C.8
		// A histogram of positions.

		final List<Position> allPositions = this.positionService.findAll();
		final double totalPositions = allPositions.size();
		final Map<String, Double> positionHistogram = new HashMap<>();
		for (final Position position : allPositions)
			positionHistogram.put(position.getStrings().get("en"), this.enrolmentService.countWithPosition(position) * 100.0d / totalPositions);
		result.addObject("positionHistogram", positionHistogram);

		// QUERY 2.C.1
		// The average, the minimum, the maximum, and the standard deviation of the size of the history of brotherhood.

		final Double[] brotherhoodHistoryStatistics = this.brotherhoodService.getHistoryStatistics();
		result.addObject("brotherhoodHistoryStatisticsMinimum", brotherhoodHistoryStatistics[0]);
		result.addObject("brotherhoodHistoryStatisticsMaximum", brotherhoodHistoryStatistics[1]);
		result.addObject("brotherhoodHistoryMemberStatisticsAverage", brotherhoodHistoryStatistics[2]);
		result.addObject("brotherhoodHistoryStatisticsStandardDeviation", brotherhoodHistoryStatistics[3]);

		// QUERY 1.C.2
		// The brotherhoods with the largest history.

		result.addObject("brotherhoodsWithLargestHistory", this.brotherhoodService.findBrotherhoodsWithTheLargestHistory(3));

		// QUERY 1.C.3
		// The brotherhoods with history larger than the average.

		result.addObject("brotherhoodsWithHistoryLargerThanTheAverage", this.brotherhoodService.findBrotherhoodsWithHistoryLargerThanAverage());

		return result;
	}

	// System configuration --------------------------------------------------------

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.GET)
	public ModelAndView systemConfiguration() {
		final ModelAndView result;
		final SystemConfigurationForm systemConfigurationForm;

		systemConfigurationForm = this.systemConfigurationService.deconstruct(this.systemConfigurationService.getSystemConfiguration());

		result = new ModelAndView("administrator/systemconfiguration");
		result.addObject("systemConfigurationForm", systemConfigurationForm);
		final Map<Integer, String> positionsMap = new HashMap<>();
		for (final Position position : this.positionService.findAll())
			positionsMap.put(position.getId(), position.getStrings().get("en"));
		result.addObject("positionsMap", positionsMap);

		return result;
	}

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.POST)
	public ModelAndView systemConfiguration(final SystemConfigurationForm systemConfigurationForm, final BindingResult bindingResult) {
		final ModelAndView result;
		SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService.reconstruct(systemConfigurationForm, bindingResult);

		if (bindingResult.hasErrors()) {
			result = new ModelAndView("administrator/systemconfiguration");

			result.addObject("systemConfigurationForm", systemConfigurationForm);
			final Map<Integer, String> positionsMap = new HashMap<>();
			for (final Position position : this.positionService.findAll())
				positionsMap.put(position.getId(), position.getStrings().get("en"));
			result.addObject("positionsMap", positionsMap);
			result.addObject("error", true);
		} else {
			this.systemConfigurationService.save(systemConfiguration);

			result = new ModelAndView("administrator/systemconfiguration");
			result.addObject("systemConfigurationForm", systemConfigurationForm);
			final Map<Integer, String> positionsMap = new HashMap<>();
			for (final Position position : this.positionService.findAll())
				positionsMap.put(position.getId(), position.getStrings().get("en"));
			result.addObject("positionsMap", positionsMap);
			result.addObject("success", true);
		}

		return result;
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
		area.setPictures(ConversionUtils.stringToList(pictures, " "));
		this.areaService.save(area);
		return this.viewAreas();
	}

	@RequestMapping(value = "/editarea", method = RequestMethod.POST)
	public ModelAndView editArea(@RequestParam(value = "id") final int id, @RequestParam(value = "name") final String name, @RequestParam(value = "pictures") final String pictures) {
		final Area area = this.areaService.findOne(id);
		area.setName(name);
		area.setPictures(ConversionUtils.stringToList(pictures, " "));
		this.areaService.save(area);
		return this.viewAreas();
	}

	@RequestMapping(value = "/deletearea", method = RequestMethod.POST)
	public ModelAndView deleteArea(@RequestParam(value = "id") final int id) {
		final Area area = this.areaService.findOne(id);
		if (!this.brotherhoodService.existWithArea(area))
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
		position.setStrings(ConversionUtils.stringToMap(positionString, ":", ";"));
		this.positionService.save(position);
		return this.viewPositions();
	}

	@RequestMapping(value = "/editposition", method = RequestMethod.POST)
	public ModelAndView editPosition(@RequestParam(value = "id") final int id, @RequestParam(value = "position") final String positionString) {
		final Position position = this.positionService.findOne(id);
		position.setStrings(ConversionUtils.stringToMap(positionString, ":", ";"));
		this.positionService.save(position);
		return this.viewPositions();
	}

	@RequestMapping(value = "/deleteposition", method = RequestMethod.POST)
	public ModelAndView deletePosition(@RequestParam(value = "id") final int id) {
		final Position position = this.positionService.findOne(id);
		if (!this.enrolmentService.existWithPosition(position))
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
		priority.setStrings(ConversionUtils.stringToMap(priorityString, ":", ";"));
		this.priorityService.save(priority);
		return this.viewPriorities();
	}

	@RequestMapping(value = "/editpriority", method = RequestMethod.POST)
	public ModelAndView editPriority(@RequestParam(value = "id") final int id, @RequestParam(value = "priority") final String priorityString) {
		final Priority priority = this.priorityService.findOne(id);
		priority.setStrings(ConversionUtils.stringToMap(priorityString, ":", ";"));
		this.priorityService.save(priority);
		return this.viewPriorities();
	}

	@RequestMapping(value = "/deletepriority", method = RequestMethod.POST)
	public ModelAndView deletePriority(@RequestParam(value = "id") final int id) {
		final Priority priority = this.priorityService.findOne(id);
		if (!this.messageService.existWithPriority(priority))
			this.priorityService.delete(priority);
		return this.viewPriorities();
	}

}
