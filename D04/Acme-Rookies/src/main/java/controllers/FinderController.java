
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FinderForm finder;
		Rookie rookie = this.rookieService.findPrincipal();
		Finder finder2 = finderService.findPrincipal(rookie.getId());
		if (finder2 == null) {
			finder = this.finderService.createForm();
			result = this.createAndEditModelAndView(finder);
		} else {
			finder = this.finderService.deconstruct(finder2);
			result = this.createAndEditModelAndView(finder);
		}
		return result;
	}

	// Save ---------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("finder") final FinderForm finder, final BindingResult binding) {
		ModelAndView result;
		Finder finder2;
		Date date = new Date();
		Collection<Position> positions;
		try {
			finder2 = this.finderService.reconstruct(finder, binding);
			//finder2.setMoment(finder2.getMoment().getSeconds() + scs);
			//Revisar esta parte para el finder

			if (finder2.getMoment().compareTo(date) < 0 || finder2.getId() == 0) {
				positions = this.finderService.findPositions("%'" + finder.getKeyword() + "'%", "%'" + finder.getKeyword() + "'%", "%'" + finder.getKeyword() + "'%", "%'" + finder.getKeyword() + "'%", "%'" + finder.getKeyword() + "'%",
					"%'" + finder.getKeyword() + "'%", finder.getDeadline(), finder.getMaximumDeadline(), finder.getMinimumSalary());
				if (positions.isEmpty())
					positions = this.positionService.findAll();
				finder2.setMoment(new Date());
				finder2.setPositions(positions);
				Rookie rookie = this.rookieService.findPrincipal();
				finder2.setRookie(rookie);
				finder2.setPositions(positions);
				this.finderService.save(finder2);
			} else {
				positions = finder2.getPositions();
			}
			result = this.list();
		} catch (final Throwable oops) {
			result = this.createAndEditModelAndView(finder, "problem.commit.error");
		}
		for (ObjectError e : binding.getAllErrors()) {
			System.err.println(e);
		}
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
}
