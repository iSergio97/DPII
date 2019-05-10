
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Finder;
import domain.Position;
import domain.Rookie;
import forms.FinderForm;
import services.FinderService;
import services.PositionService;
import services.RookieService;
import services.SystemConfigurationService;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private FinderService				finderService;

	@Autowired
	private RookieService				rookieService;

	@Autowired
	private PositionService				positionService;

	@Autowired
	private SystemConfigurationService	scs;


	// Constructors -----------------------------------------------

	public FinderController() {

	}

	// List ------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Rookie rookie;
		Finder finder;
		Collection<Position> positions = new ArrayList<Position>();
		//		rookie = this.rookieService.findPrincipal();
		//		finder = this.finderService.findPrincipal(rookie.getId());
		//		Date now = Calendar.getInstance().getTime();
		//		Date finderDate = finder.getMoment();
		//
		//		if (finder.getId() == 0 || finder == null || now.compareTo(finderDate) > 0) {
		//			result = this.create();
		//			positions = this.finderService.findPositions(kw1, kw2, kw3, kw4, kw5, kw6, deadline, maximumDeadline, minimumSalary);
		//		} else {
		//			positions.retainAll(finder.getPositions());
		//		}
		//
		//		result = new ModelAndView("finder/list");
		//		result.addObject("positions", positions);
		//		result.addObject("requestURI", "finder/list.do");
		//
		//		return result;

		rookie = this.rookieService.findPrincipal();
		finder = this.finderService.findPrincipal(rookie.getId());
		if (finder == null)
			result = this.create();
		else {
			final Date now = new Date();
			final Date overCache = finder.getMoment();
			overCache.setHours(finder.getMoment().getHours() + 1);

			positions = this.positionService.findAll();
			if (now.before(overCache) && !finder.getPositions().isEmpty())
				positions.retainAll(finder.getPositions());
			result = new ModelAndView("/finder/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "finder/list.do");
		}
		return result;
	}

	// Create --------------------------------------------------

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FinderForm finder;
		Rookie rookie = this.rookieService.findPrincipal();
		Finder finder2 = finderService.findPrincipal(rookie.getId());
		Date date = new Date();
		date.setHours(date.getHours() + scs.getSystemConfiguration().getFinderCacheTime());
		if (finder2 == null) {
			finder = this.finderService.createForm();
			result = this.createAndEditModelAndView(finder);
		} else if (finder2.getMoment().compareTo(date) < 0) {
			finder = this.finderService.deconstruct(finder2);
			result = this.createAndEditModelAndView(finder);
		} else {
			result = this.list();
		}

		return result;
	}

	// Edit ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int finderId) {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);

		result = new ModelAndView("/finder/edit");
		result.addObject("finder", finder);

		return result;
	}

	// Save ---------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("finder") final FinderForm finder, final BindingResult binding) {
		ModelAndView result;
		Finder finder2;

		finder2 = this.finderService.reconstruct(finder, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(finder);
		else
			try {

				Collection<Position> positions = this.finderService.findPositions("%'" + finder.getKeyword() + "'%", "%'" + finder.getKeyword() + "'%", "%'" + finder.getKeyword() + "'%", "%'" + finder.getKeyword() + "'%", "%'" + finder.getKeyword() + "'%",
					"%'" + finder.getKeyword() + "'%", finder.getDeadline(), finder.getMaximumDeadline(), finder.getMinimumSalary());
				if (positions.isEmpty())
					positions = this.positionService.findAll();
				finder2.setMoment(new Date());
				finder2.setPositions(positions);
				Rookie rookie = this.rookieService.findPrincipal();
				finder2.setRookie(rookie);
				finder2.setPositions(positions);
				this.finderService.save(finder2);
				result = this.list();
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(finder, "problem.commit.error");
			}

		return result;
	}

	//Delete------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam("finderId") final int finderId) {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.findOne(finderId);
		this.finderService.delete(finder);
		result = this.list();

		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final FinderForm finder) {
		ModelAndView result;

		result = this.createAndEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final FinderForm finder, final String message) {
		final ModelAndView result;

		result = new ModelAndView("/finder/create");
		result.addObject("finder", finder);
		result.addObject("message", message);

		return result;
	}

	private String dateFormatter(final Date date) {
		String s = "'" + (date.getYear() % 100 + 2000) + "-";
		if (date.getMonth() < 10)
			s = s + "0" + date.getMonth() + "-";
		else
			s = s + date.getMonth() + "-";
		if (date.getDay() < 10)
			s = s + "0" + date.getDay();
		else
			s = s + date.getDay();

		return s += "'";
	}
}
