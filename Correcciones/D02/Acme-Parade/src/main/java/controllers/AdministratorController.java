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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import domain.Area;
import domain.Position;
import domain.Priority;
import domain.SystemConfiguration;
import forms.AreaForm;
import forms.PositionForm;
import forms.PriorityForm;
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

		result = this.createModelAndViewWithSystemConfiguration("administrator/dashboard");

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

		// QUERY 1.C.7
		// The listing of members who have got at least 10% the maximum number of request to march accepted.

		result.addObject("membersWithAtLeastTenPercentOfTheMaximumNumberOfAcceptedRequests", this.requestService.getMembersWithAtLeastTenPercentOfTheMaximumNumberOfAcceptedRequests());

		// QUERY 1.C.8
		// A histogram of positions.

		final List<Position> allPositions = this.positionService.findAll();
		final Map<String, Double> positionHistogram = new HashMap<>();
		for (final Position position : allPositions) {
			Double proportion = (double) this.enrolmentService.countWithPosition(position);
			proportion *= 100.0d;
			proportion /= this.enrolmentService.count();
			positionHistogram.put(position.getStrings().get("en"), proportion);
		}
		result.addObject("positionHistogram", positionHistogram);

		// QUERY 2.C.1
		// The average, the minimum, the maximum, and the standard deviation of the size of the history of brotherhood.

		final Double[] brotherhoodHistoryStatistics = this.brotherhoodService.getHistoryStatistics();
		result.addObject("brotherhoodHistoryStatisticsMinimum", brotherhoodHistoryStatistics[0]);
		result.addObject("brotherhoodHistoryStatisticsMaximum", brotherhoodHistoryStatistics[1]);
		result.addObject("brotherhoodHistoryMemberStatisticsAverage", brotherhoodHistoryStatistics[2]);
		result.addObject("brotherhoodHistoryStatisticsStandardDeviation", brotherhoodHistoryStatistics[3]);

		// QUERY 2.C.2
		// The brotherhoods with the largest history.

		result.addObject("brotherhoodsWithLargestHistory", this.brotherhoodService.findBrotherhoodsWithTheLargestHistory(3));

		// QUERY 2.C.3
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

		result = this.createModelAndViewWithSystemConfiguration("administrator/systemconfiguration");
		result.addObject("systemConfigurationForm", systemConfigurationForm);
		final Map<Integer, String> positionsMap = new HashMap<>();
		for (final Position position : this.positionService.findAll())
			positionsMap.put(position.getId(), position.getStrings().get("en"));
		result.addObject("positionsMap", positionsMap);

		return result;
	}

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.POST)
	public ModelAndView systemConfiguration(@Valid @ModelAttribute("systemConfigurationForm") final SystemConfigurationForm systemConfigurationForm, final BindingResult bindingResult) {
		final ModelAndView result;
		SystemConfiguration systemConfiguration;

		if (bindingResult.hasErrors()) {
			result = this.createModelAndViewWithSystemConfiguration("administrator/systemconfiguration");

			result.addObject("systemConfigurationForm", systemConfigurationForm);
			final Map<Integer, String> positionsMap = new HashMap<>();
			for (final Position position : this.positionService.findAll())
				positionsMap.put(position.getId(), position.getStrings().get("en"));
			result.addObject("positionsMap", positionsMap);
			result.addObject("error", true);
		} else {
			systemConfiguration = this.systemConfigurationService.reconstruct(systemConfigurationForm, bindingResult);
			this.systemConfigurationService.save(systemConfiguration);

			result = this.createModelAndViewWithSystemConfiguration("administrator/systemconfiguration");
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

		result = this.createModelAndViewWithSystemConfiguration("administrator/viewareas");
		result.addObject("areas", this.areaService.findAll());

		return result;
	}

	@RequestMapping(value = "/createarea", method = RequestMethod.GET)
	public ModelAndView createArea() {
		final ModelAndView result;
		final AreaForm areaForm;

		result = this.createModelAndViewWithSystemConfiguration("administrator/editarea");
		areaForm = this.areaService.createForm();
		result.addObject("areaForm", areaForm);

		return result;
	}

	@RequestMapping(value = "/editarea", method = RequestMethod.GET)
	public ModelAndView editArea(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final AreaForm areaForm;

		result = this.createModelAndViewWithSystemConfiguration("administrator/editarea");
		areaForm = this.areaService.deconstruct(this.areaService.findOne(id));
		result.addObject("areaForm", areaForm);

		return result;
	}

	@RequestMapping(value = "/savearea", method = RequestMethod.POST)
	public ModelAndView saveArea(@Valid @ModelAttribute("areaForm") final AreaForm areaForm, final BindingResult bindingResult) {
		final ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = this.createModelAndViewWithSystemConfiguration("administrator/editarea");
			result.addObject("areaForm", areaForm);
		} else {
			final Area area = this.areaService.reconstruct(areaForm, bindingResult);
			this.areaService.save(area);
			result = this.viewAreas();
		}

		return result;
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

		result = this.createModelAndViewWithSystemConfiguration("administrator/viewpositions");
		result.addObject("positions", this.positionService.findAll());

		return result;
	}

	@RequestMapping(value = "/createposition", method = RequestMethod.GET)
	public ModelAndView createPosition() {
		final ModelAndView result;
		final PositionForm positionForm;

		result = this.createModelAndViewWithSystemConfiguration("administrator/editposition");
		positionForm = this.positionService.createForm();
		result.addObject("positionForm", positionForm);

		return result;
	}

	@RequestMapping(value = "/editposition", method = RequestMethod.GET)
	public ModelAndView editPosition(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final PositionForm positionForm;

		result = this.createModelAndViewWithSystemConfiguration("administrator/editposition");
		positionForm = this.positionService.deconstruct(this.positionService.findOne(id));
		result.addObject("positionForm", positionForm);

		return result;
	}

	@RequestMapping(value = "/saveposition", method = RequestMethod.POST)
	public ModelAndView savePosition(@Valid @ModelAttribute("positionForm") final PositionForm positionForm, final BindingResult bindingResult) {
		final ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = this.createModelAndViewWithSystemConfiguration("administrator/editposition");
			result.addObject("positionForm", positionForm);
		} else {
			final Position position = this.positionService.reconstruct(positionForm, bindingResult);
			this.positionService.save(position);
			result = this.viewPositions();
		}

		return result;
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

		result = this.createModelAndViewWithSystemConfiguration("administrator/viewpriorities");
		result.addObject("priorities", this.priorityService.findAll());

		return result;
	}

	@RequestMapping(value = "/createpriority", method = RequestMethod.GET)
	public ModelAndView createPriority() {
		final ModelAndView result;
		final PriorityForm priorityForm;

		result = this.createModelAndViewWithSystemConfiguration("administrator/editpriority");
		priorityForm = this.priorityService.createForm();
		result.addObject("priorityForm", priorityForm);

		return result;
	}

	@RequestMapping(value = "/editpriority", method = RequestMethod.GET)
	public ModelAndView editPriority(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final PriorityForm priorityForm;

		result = this.createModelAndViewWithSystemConfiguration("administrator/editpriority");
		priorityForm = this.priorityService.deconstruct(this.priorityService.findOne(id));
		result.addObject("priorityForm", priorityForm);

		return result;
	}

	@RequestMapping(value = "/savepriority", method = RequestMethod.POST)
	public ModelAndView savePriority(@Valid @ModelAttribute("priorityForm") final PriorityForm priorityForm, final BindingResult bindingResult) {
		final ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = this.createModelAndViewWithSystemConfiguration("administrator/editpriority");
			result.addObject("priorityForm", priorityForm);
		} else {
			final Priority priority = this.priorityService.reconstruct(priorityForm, bindingResult);
			this.priorityService.save(priority);
			result = this.viewPriorities();
		}

		return result;
	}

	@RequestMapping(value = "/deletepriority", method = RequestMethod.POST)
	public ModelAndView deletePriority(@RequestParam(value = "id") final int id) {
		final Priority priority = this.priorityService.findOne(id);
		if (!this.messageService.existWithPriority(priority))
			this.priorityService.delete(priority);
		return this.viewPriorities();
	}

}
