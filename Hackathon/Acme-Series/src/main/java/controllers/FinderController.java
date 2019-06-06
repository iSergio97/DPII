
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Finder;
import domain.Serie;
import forms.FinderForm;
import services.FinderService;
import services.SystemConfigurationService;
import services.UserService;

@Controller

@RequestMapping("/finder/user")
public class FinderController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private FinderService				finderService;
	@Autowired
	private SystemConfigurationService	scs;
	@Autowired
	private UserService					userService;


	// Constructors -----------------------------------------------

	public FinderController() {

		super();
	}

	// List ------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Finder finder;
		Collection<Serie> series = new ArrayList<Serie>();

		finder = this.finderService.findByPrincipal();
		if (finder == null)
			result = this.create();
		else {
			result = this.createModelAndViewWithSystemConfiguration("finder/user/list");
			finder = this.finderService.findByPrincipal();
			series = finder.getSeries();
			result.addObject("series", series);
			result.addObject("requestURI", "finder/user/list.do");
		}
		return result;
	}

	// Create --------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		FinderForm finder;
		final Finder finder2 = this.finderService.findByPrincipal();
		if (finder2 == null)
			finder = this.finderService.createForm();
		else
			finder = this.finderService.deconstruct(finder2);
		return this.createAndEditModelAndView(finder);
	}

	// Save ---------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("finder") final FinderForm finder, final BindingResult binding) {
		ModelAndView result;
		Finder finder2;
		final List<Serie> series;
		try {
			finder2 = this.finderService.reconstruct(finder, binding);
			series = (List<Serie>) this.finderService.findSeries(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate());
			if (series.size() > this.scs.getSystemConfiguration().getMaximumFinderResults())
				finder2.setSeries(series.subList(0, this.scs.getSystemConfiguration().getMaximumFinderResults()));
			else
				finder2.setSeries(series);

			this.finderService.save(finder2);

			result = this.list();
		} catch (final Throwable oops) {
			result = this.createAndEditModelAndView(finder, "problem.commit.error");
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

		result = this.createModelAndViewWithSystemConfiguration("finder/user/create");
		result.addObject("finder", finder);
		result.addObject("message", message);

		return result;
	}
}
