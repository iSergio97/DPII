
package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	////////////////////////////////////////////////////////////////////////////////
	// Services

	@Autowired
	private ChapterService		chapterService;
	@Autowired
	private PublisherService	publisherService;
	@Autowired
	private SeasonService		seasonService;
	@Autowired
	private SerieService		serieService;
	@Autowired
	private UserService			userService;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public SerieController() {

	}

	////////////////////////////////////////////////////////////////////////////////
	// List

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

	@RequestMapping(value = "/public/list", method = RequestMethod.GET)
	public ModelAndView publicList(@RequestParam(required = false) final String keyword) {
		ModelAndView result;
		Collection<Serie> series;

		series = this.serieService.searchQuery(keyword == null ? "" : keyword);
		result = new ModelAndView("serie/public/list");
		result.addObject("series", series);

		final User user = this.userService.findPrincipal();
		if (user != null) {
			final Map<Serie, Boolean> seriesFavoritedByPrincipal = new HashMap<>();
			for (final Serie serieFavoritedByPrincipal : this.serieService.findFavouriteByUserId(user.getId()))
				seriesFavoritedByPrincipal.put(serieFavoritedByPrincipal, true);
			result.addObject("seriesFavoritedByPrincipal", seriesFavoritedByPrincipal);
			final Map<Serie, Boolean> seriesPendingByPrincipal = new HashMap<>();
			for (final Serie seriePendingByPrincipal : this.serieService.findPendingByUserId(user.getId()))
				seriesPendingByPrincipal.put(seriePendingByPrincipal, true);
			result.addObject("seriesPendingByPrincipal", seriesPendingByPrincipal);
			final Map<Serie, Boolean> seriesWatchingByPrincipal = new HashMap<>();
			for (final Serie serieWatchingByPrincipal : this.serieService.findWatchingByUserId(user.getId()))
				seriesWatchingByPrincipal.put(serieWatchingByPrincipal, true);
			result.addObject("seriesWatchingByPrincipal", seriesWatchingByPrincipal);
			final Map<Serie, Boolean> seriesWatchedByPrincipal = new HashMap<>();
			for (final Serie serieWatchedByPrincipal : this.serieService.findWatchedByUserId(user.getId()))
				seriesWatchedByPrincipal.put(serieWatchedByPrincipal, true);
			result.addObject("seriesWatchedByPrincipal", seriesWatchedByPrincipal);
		}

		return result;
	}

	@RequestMapping(value = "/user/sortedByFavoritesList", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final User user;
		final List<Serie> series;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");
		series = this.serieService.getSeriesSortedByNumberOfFavorites();

		result = new ModelAndView("serie/user/sortedByFavoritesList");
		result.addObject("series", series);

		final Map<Serie, Boolean> seriesFavoritedByPrincipal = new HashMap<>();
		for (final Serie serieFavoritedByPrincipal : this.serieService.findFavouriteByUserId(user.getId()))
			seriesFavoritedByPrincipal.put(serieFavoritedByPrincipal, true);
		result.addObject("seriesFavoritedByPrincipal", seriesFavoritedByPrincipal);
		final Map<Serie, Boolean> seriesPendingByPrincipal = new HashMap<>();
		for (final Serie seriePendingByPrincipal : this.serieService.findPendingByUserId(user.getId()))
			seriesPendingByPrincipal.put(seriePendingByPrincipal, true);
		result.addObject("seriesPendingByPrincipal", seriesPendingByPrincipal);
		final Map<Serie, Boolean> seriesWatchingByPrincipal = new HashMap<>();
		for (final Serie serieWatchingByPrincipal : this.serieService.findWatchingByUserId(user.getId()))
			seriesWatchingByPrincipal.put(serieWatchingByPrincipal, true);
		result.addObject("seriesWatchingByPrincipal", seriesWatchingByPrincipal);
		final Map<Serie, Boolean> seriesWatchedByPrincipal = new HashMap<>();
		for (final Serie serieWatchedByPrincipal : this.serieService.findWatchedByUserId(user.getId()))
			seriesWatchedByPrincipal.put(serieWatchedByPrincipal, true);
		result.addObject("seriesWatchedByPrincipal", seriesWatchedByPrincipal);

		return result;
	}

	@RequestMapping(value = "/user/favouriteList", method = RequestMethod.GET)
	public ModelAndView favouriteUserList() {
		ModelAndView result;
		User user;
		Collection<Serie> series;

		user = this.userService.findPrincipal();
		series = this.serieService.findFavouriteByUserId(user.getId());
		result = new ModelAndView("serie/user/favouriteList");
		result.addObject("series", series);

		final Map<Serie, Boolean> seriesFavoritedByPrincipal = new HashMap<>();
		for (final Serie serieFavoritedByPrincipal : this.serieService.findFavouriteByUserId(user.getId()))
			seriesFavoritedByPrincipal.put(serieFavoritedByPrincipal, true);
		result.addObject("seriesFavoritedByPrincipal", seriesFavoritedByPrincipal);
		final Map<Serie, Boolean> seriesPendingByPrincipal = new HashMap<>();
		for (final Serie seriePendingByPrincipal : this.serieService.findPendingByUserId(user.getId()))
			seriesPendingByPrincipal.put(seriePendingByPrincipal, true);
		result.addObject("seriesPendingByPrincipal", seriesPendingByPrincipal);
		final Map<Serie, Boolean> seriesWatchingByPrincipal = new HashMap<>();
		for (final Serie serieWatchingByPrincipal : this.serieService.findWatchingByUserId(user.getId()))
			seriesWatchingByPrincipal.put(serieWatchingByPrincipal, true);
		result.addObject("seriesWatchingByPrincipal", seriesWatchingByPrincipal);
		final Map<Serie, Boolean> seriesWatchedByPrincipal = new HashMap<>();
		for (final Serie serieWatchedByPrincipal : this.serieService.findWatchedByUserId(user.getId()))
			seriesWatchedByPrincipal.put(serieWatchedByPrincipal, true);
		result.addObject("seriesWatchedByPrincipal", seriesWatchedByPrincipal);

		return result;
	}

	@RequestMapping(value = "/user/pendingList", method = RequestMethod.GET)
	public ModelAndView pendingUserList() {
		ModelAndView result;
		User user;
		Collection<Serie> series;

		user = this.userService.findPrincipal();
		series = this.serieService.findPendingByUserId(user.getId());
		result = new ModelAndView("serie/user/pendingList");
		result.addObject("series", series);

		final Map<Serie, Boolean> seriesFavoritedByPrincipal = new HashMap<>();
		for (final Serie serieFavoritedByPrincipal : this.serieService.findFavouriteByUserId(user.getId()))
			seriesFavoritedByPrincipal.put(serieFavoritedByPrincipal, true);
		result.addObject("seriesFavoritedByPrincipal", seriesFavoritedByPrincipal);
		final Map<Serie, Boolean> seriesPendingByPrincipal = new HashMap<>();
		for (final Serie seriePendingByPrincipal : this.serieService.findPendingByUserId(user.getId()))
			seriesPendingByPrincipal.put(seriePendingByPrincipal, true);
		result.addObject("seriesPendingByPrincipal", seriesPendingByPrincipal);
		final Map<Serie, Boolean> seriesWatchingByPrincipal = new HashMap<>();
		for (final Serie serieWatchingByPrincipal : this.serieService.findWatchingByUserId(user.getId()))
			seriesWatchingByPrincipal.put(serieWatchingByPrincipal, true);
		result.addObject("seriesWatchingByPrincipal", seriesWatchingByPrincipal);
		final Map<Serie, Boolean> seriesWatchedByPrincipal = new HashMap<>();
		for (final Serie serieWatchedByPrincipal : this.serieService.findWatchedByUserId(user.getId()))
			seriesWatchedByPrincipal.put(serieWatchedByPrincipal, true);
		result.addObject("seriesWatchedByPrincipal", seriesWatchedByPrincipal);

		return result;
	}

	@RequestMapping(value = "/user/watchingList", method = RequestMethod.GET)
	public ModelAndView watchingList() {
		ModelAndView result;
		User user;
		Collection<Serie> series;

		user = this.userService.findPrincipal();
		series = this.serieService.findWatchingByUserId(user.getId());
		result = new ModelAndView("serie/user/watchingList");
		result.addObject("series", series);

		final Map<Serie, Boolean> seriesFavoritedByPrincipal = new HashMap<>();
		for (final Serie serieFavoritedByPrincipal : this.serieService.findFavouriteByUserId(user.getId()))
			seriesFavoritedByPrincipal.put(serieFavoritedByPrincipal, true);
		result.addObject("seriesFavoritedByPrincipal", seriesFavoritedByPrincipal);
		final Map<Serie, Boolean> seriesPendingByPrincipal = new HashMap<>();
		for (final Serie seriePendingByPrincipal : this.serieService.findPendingByUserId(user.getId()))
			seriesPendingByPrincipal.put(seriePendingByPrincipal, true);
		result.addObject("seriesPendingByPrincipal", seriesPendingByPrincipal);
		final Map<Serie, Boolean> seriesWatchingByPrincipal = new HashMap<>();
		for (final Serie serieWatchingByPrincipal : this.serieService.findWatchingByUserId(user.getId()))
			seriesWatchingByPrincipal.put(serieWatchingByPrincipal, true);
		result.addObject("seriesWatchingByPrincipal", seriesWatchingByPrincipal);
		final Map<Serie, Boolean> seriesWatchedByPrincipal = new HashMap<>();
		for (final Serie serieWatchedByPrincipal : this.serieService.findWatchedByUserId(user.getId()))
			seriesWatchedByPrincipal.put(serieWatchedByPrincipal, true);
		result.addObject("seriesWatchedByPrincipal", seriesWatchedByPrincipal);

		return result;
	}

	@RequestMapping(value = "/user/watchedList", method = RequestMethod.GET)
	public ModelAndView watchedList() {
		ModelAndView result;
		User user;
		Collection<Serie> series;

		user = this.userService.findPrincipal();
		series = this.serieService.findWatchedByUserId(user.getId());
		result = new ModelAndView("serie/user/watchedList");
		result.addObject("series", series);

		final Map<Serie, Boolean> seriesFavoritedByPrincipal = new HashMap<>();
		for (final Serie serieFavoritedByPrincipal : this.serieService.findFavouriteByUserId(user.getId()))
			seriesFavoritedByPrincipal.put(serieFavoritedByPrincipal, true);
		result.addObject("seriesFavoritedByPrincipal", seriesFavoritedByPrincipal);
		final Map<Serie, Boolean> seriesPendingByPrincipal = new HashMap<>();
		for (final Serie seriePendingByPrincipal : this.serieService.findPendingByUserId(user.getId()))
			seriesPendingByPrincipal.put(seriePendingByPrincipal, true);
		result.addObject("seriesPendingByPrincipal", seriesPendingByPrincipal);
		final Map<Serie, Boolean> seriesWatchingByPrincipal = new HashMap<>();
		for (final Serie serieWatchingByPrincipal : this.serieService.findWatchingByUserId(user.getId()))
			seriesWatchingByPrincipal.put(serieWatchingByPrincipal, true);
		result.addObject("seriesWatchingByPrincipal", seriesWatchingByPrincipal);
		final Map<Serie, Boolean> seriesWatchedByPrincipal = new HashMap<>();
		for (final Serie serieWatchedByPrincipal : this.serieService.findWatchedByUserId(user.getId()))
			seriesWatchedByPrincipal.put(serieWatchedByPrincipal, true);
		result.addObject("seriesWatchedByPrincipal", seriesWatchedByPrincipal);

		return result;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Show

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int serieId) {
		ModelAndView result;
		Serie serie;

		serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("serie/public/show");
		result.addObject("serie", serie);

		final User user = this.userService.findPrincipal();
		if (user != null) {
			final Map<Serie, Boolean> seriesFavoritedByPrincipal = new HashMap<>();
			for (final Serie serieFavoritedByPrincipal : this.serieService.findFavouriteByUserId(user.getId()))
				seriesFavoritedByPrincipal.put(serieFavoritedByPrincipal, true);
			result.addObject("seriesFavoritedByPrincipal", seriesFavoritedByPrincipal);
			final Map<Serie, Boolean> seriesPendingByPrincipal = new HashMap<>();
			for (final Serie seriePendingByPrincipal : this.serieService.findPendingByUserId(user.getId()))
				seriesPendingByPrincipal.put(seriePendingByPrincipal, true);
			result.addObject("seriesPendingByPrincipal", seriesPendingByPrincipal);
			final Map<Serie, Boolean> seriesWatchingByPrincipal = new HashMap<>();
			for (final Serie serieWatchingByPrincipal : this.serieService.findWatchingByUserId(user.getId()))
				seriesWatchingByPrincipal.put(serieWatchingByPrincipal, true);
			result.addObject("seriesWatchingByPrincipal", seriesWatchingByPrincipal);
			final Map<Serie, Boolean> seriesWatchedByPrincipal = new HashMap<>();
			for (final Serie serieWatchedByPrincipal : this.serieService.findWatchedByUserId(user.getId()))
				seriesWatchedByPrincipal.put(serieWatchedByPrincipal, true);
			result.addObject("seriesWatchedByPrincipal", seriesWatchedByPrincipal);
		}

		return result;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Create

	@RequestMapping(value = "/publisher/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final SerieForm serie;

		serie = this.serieService.createForm();
		result = this.createAndEditModelAndView(serie);

		return result;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Edit

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int serieId) {
		ModelAndView result;
		Serie serie;

		serie = this.serieService.findOne(serieId);
		result = new ModelAndView("serie/publisher/edit");
		result.addObject("serie", serie);

		return result;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Save

	@RequestMapping(value = "/publisher/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("serie") final SerieForm serieForm, final BindingResult binding) {
		ModelAndView result;
		Serie serie;

		serie = this.serieService.reconstruct(serieForm, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(serieForm);
		else
			try {
				if (serie.getSeasons().isEmpty()) {
					final Season season = this.seasonService.create();
					final Chapter chapter = this.chapterService.create();
					season.getChapters().add(chapter);
					serie.getSeasons().add(season);
				}
				this.serieService.save(serie);
				result = this.publisherList();
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(serieForm);
			}
		return result;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Mark as favorite

	@RequestMapping(value = "/user/markAsFavorite", method = RequestMethod.POST)
	public ModelAndView markAsFavorite(@RequestParam(value = "serieId") final int serieId) {
		final User user;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<User> favoritedUsers = serie.getFavouritedUsers();
		if (favoritedUsers.contains(user))
			return new ModelAndView("redirect:/welcome/index.do");
		favoritedUsers.add(user);
		serie.setFavouritedUsers(favoritedUsers);
		this.serieService.save(serie);

		return this.show(serieId);
	}

	@RequestMapping(value = "/user/unmarkAsFavorite", method = RequestMethod.POST)
	public ModelAndView unmarkAsFavorite(@RequestParam(value = "serieId") final int serieId) {
		final User user;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<User> favoritedUsers = serie.getFavouritedUsers();
		if (!favoritedUsers.contains(user))
			return new ModelAndView("redirect:/welcome/index.do");
		favoritedUsers.remove(user);
		serie.setFavouritedUsers(favoritedUsers);
		this.serieService.save(serie);

		return this.show(serieId);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Mark as pending

	@RequestMapping(value = "/user/markAsPending", method = RequestMethod.POST)
	public ModelAndView markAsPending(@RequestParam(value = "serieId") final int serieId) {
		final User user;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<User> pendingUsers = serie.getPendingUsers();
		if (pendingUsers.contains(user))
			return new ModelAndView("redirect:/welcome/index.do");
		pendingUsers.add(user);
		serie.setPendingUsers(pendingUsers);
		this.serieService.save(serie);

		return this.show(serieId);
	}

	@RequestMapping(value = "/user/unmarkAsPending", method = RequestMethod.POST)
	public ModelAndView unmarkAsPending(@RequestParam(value = "serieId") final int serieId) {
		final User user;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<User> pendingUsers = serie.getPendingUsers();
		if (!pendingUsers.contains(user))
			return new ModelAndView("redirect:/welcome/index.do");
		pendingUsers.remove(user);
		serie.setPendingUsers(pendingUsers);
		this.serieService.save(serie);

		return this.show(serieId);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Mark as watching

	@RequestMapping(value = "/user/markAsWatching", method = RequestMethod.POST)
	public ModelAndView markAsWatching(@RequestParam(value = "serieId") final int serieId) {
		final User user;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<User> watchingUsers = serie.getWatchingUsers();
		if (watchingUsers.contains(user))
			return new ModelAndView("redirect:/welcome/index.do");
		watchingUsers.add(user);
		serie.setWatchingUsers(watchingUsers);
		this.serieService.save(serie);

		return this.show(serieId);
	}

	@RequestMapping(value = "/user/unmarkAsWatching", method = RequestMethod.POST)
	public ModelAndView unmarkAsWatching(@RequestParam(value = "serieId") final int serieId) {
		final User user;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<User> watchingUsers = serie.getWatchingUsers();
		if (!watchingUsers.contains(user))
			return new ModelAndView("redirect:/welcome/index.do");
		watchingUsers.remove(user);
		serie.setWatchingUsers(watchingUsers);
		this.serieService.save(serie);

		return this.show(serieId);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Mark as watched

	@RequestMapping(value = "/user/markAsWatched", method = RequestMethod.POST)
	public ModelAndView markAsWatched(@RequestParam(value = "serieId") final int serieId) {
		final User user;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<User> watchedUsers = serie.getWatchedUsers();
		if (watchedUsers.contains(user))
			return new ModelAndView("redirect:/welcome/index.do");
		watchedUsers.add(user);
		serie.setWatchedUsers(watchedUsers);
		this.serieService.save(serie);

		return this.show(serieId);
	}

	@RequestMapping(value = "/user/unmarkAsWatched", method = RequestMethod.POST)
	public ModelAndView unmarkAsWatched(@RequestParam(value = "serieId") final int serieId) {
		final User user;

		user = this.userService.findPrincipal();
		if (user == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie serie = this.serieService.findOne(serieId);
		if (serie == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<User> watchedUsers = serie.getWatchedUsers();
		if (!watchedUsers.contains(user))
			return new ModelAndView("redirect:/welcome/index.do");
		watchedUsers.remove(user);
		serie.setWatchedUsers(watchedUsers);
		this.serieService.save(serie);

		return this.show(serieId);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary Methods

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
