
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChapterService;
import services.PublisherService;
import services.SeasonService;
import services.SerieService;
import services.UserService;
import domain.Chapter;
import domain.Publisher;
import domain.Season;
import domain.Serie;
import domain.User;
import forms.SerieForm;

@Controller
@RequestMapping("/serie")
public class SerieController extends AbstractController {

	@Autowired
	private SerieService		serieService;

	@Autowired
	private UserService			userService;

	@Autowired
	private PublisherService	publisherService;

	@Autowired
	private SeasonService		seasonService;

	@Autowired
	private ChapterService		chapterService;


	public SerieController() {

	}

	/////////////////////////////////////////////////////////////
	//List

	@RequestMapping(value = "/publisher/list", method = RequestMethod.GET)
	public ModelAndView publisherList() {
		ModelAndView result;
		Collection<Serie> series;
		final Publisher p = this.publisherService.findPrincipal();

		series = this.serieService.findByPublisherId(p.getId());
		result = new ModelAndView("serie/publisher/list");
		result.addObject("series", series);

		return result;
	}

	@RequestMapping(value = "/all/list", method = RequestMethod.GET)
	public ModelAndView publicList() {
		ModelAndView result;
		Collection<Serie> series;

		series = this.serieService.findPublicSeries();
		result = new ModelAndView("serie/all/list");
		result.addObject("series", series);

		return result;
	}

	@RequestMapping(value = "/user/favouriteList", method = RequestMethod.GET)
	public ModelAndView favouriteUserList() {
		ModelAndView result;
		User u;
		Collection<Serie> series;

		u = this.userService.findPrincipal();
		series = this.serieService.findFavouriteByUserId(u.getId());
		result = new ModelAndView("serie/user/favouriteList");
		result.addObject("series", series);

		return result;
	}

	@RequestMapping(value = "/user/pendingList", method = RequestMethod.GET)
	public ModelAndView pendingUserList() {
		ModelAndView result;
		User u;
		Collection<Serie> series;

		u = this.userService.findPrincipal();
		series = this.serieService.findPendingByUserId(u.getId());
		result = new ModelAndView("serie/user/pendingList");
		result.addObject("series", series);

		return result;
	}

	@RequestMapping(value = "/user/watchingList", method = RequestMethod.GET)
	public ModelAndView watchingList() {
		ModelAndView result;
		User u;
		Collection<Serie> series;

		u = this.userService.findPrincipal();
		series = this.serieService.findWatchingByUserId(u.getId());
		result = new ModelAndView("serie/user/watchingList");
		result.addObject("series", series);

		return result;
	}

	@RequestMapping(value = "/user/watchedList", method = RequestMethod.GET)
	public ModelAndView watchedList() {
		ModelAndView result;
		User u;
		Collection<Serie> series;

		u = this.userService.findPrincipal();
		series = this.serieService.findWatchedByUserId(u.getId());
		result = new ModelAndView("serie/user/watchedList");
		result.addObject("series", series);

		return result;
	}

	////////////////////////////////////////////////////////////////////////
	//Show

	@RequestMapping(value = "/publisher/show")
	public ModelAndView show(@RequestParam final int serieId) {
		ModelAndView result;
		Serie serie;

		serie = this.serieService.findOne(serieId);
		result = new ModelAndView("serie/publisher/show");
		result.addObject("serie", serie);

		return result;
	}

	@RequestMapping(value = "/all/show")
	public ModelAndView showPublic(@RequestParam final int serieId) {
		ModelAndView result;
		Serie serie;

		serie = this.serieService.findOne(serieId);
		result = new ModelAndView("serie/all/show");
		result.addObject("serie", serie);

		return result;
	}

	///////////////////////////////////////////////////////////////////////
	//Create

	@RequestMapping(value = "/publisher/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final SerieForm serie;

		serie = this.serieService.createForm();
		result = this.createAndEditModelAndView(serie);

		return result;
	}

	/////////////////////////////////////////////////////////////////////
	//Edit

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int serieId) {
		ModelAndView result;
		Serie serie;

		serie = this.serieService.findOne(serieId);
		result = new ModelAndView("serie/publisher/edit");
		result.addObject("serie", serie);

		return result;
	}

	////////////////////////////////////////////////////////////////////
	//Save

	@RequestMapping(value = "/publisher/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("serie") final SerieForm serie, final BindingResult binding) {
		ModelAndView result;
		Serie serie2;

		serie2 = this.serieService.reconstruct(serie, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(serie);
		else
			try {
				if (serie2.getSeasons().isEmpty()) {
					final Season s = this.seasonService.create();
					final Chapter c = this.chapterService.create();
					s.getChapters().add(c);
					serie2.getSeasons().add(s);
				}
				this.serieService.save(serie2);
				result = this.publisherList();
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(serie);
			}
		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final SerieForm serie) {
		ModelAndView result;

		result = this.createAndEditModelAndView(serie, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final SerieForm serie, final String message) {
		final ModelAndView result;

		result = new ModelAndView("serie/publisher/create");
		result.addObject("serie", serie);
		result.addObject("message", message);

		return result;
	}

}
