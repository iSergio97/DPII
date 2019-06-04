
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
import services.SeasonService;
import services.SerieService;
import domain.Chapter;
import domain.Season;
import domain.Serie;
import forms.SeasonForm;

@Controller
@RequestMapping("/season")
public class SeasonController extends AbstractController {

	@Autowired
	private SeasonService	seasonService;

	@Autowired
	private SerieService	serieService;

	@Autowired
	private ChapterService	chapterService;


	public SeasonController() {

	}

	///////////////////////////////////////////////////////////////
	//List

	@RequestMapping(value = "/publisher/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int serieId) {
		ModelAndView result;
		Collection<Season> seasons;
		final Serie s = this.serieService.findOne(serieId);

		seasons = s.getSeasons();
		result = new ModelAndView("season/publisher/list");
		result.addObject("seasons", seasons);
		result.addObject("serieId", serieId);

		return result;
	}

	@RequestMapping(value = "/public/list", method = RequestMethod.GET)
	public ModelAndView publicList(@RequestParam final int serieId) {
		ModelAndView result;
		Collection<Season> seasons;
		final Serie s = this.serieService.findOne(serieId);

		seasons = s.getSeasons();
		result = new ModelAndView("season/public/list");
		result.addObject("seasons", seasons);
		result.addObject("serieId", serieId);

		return result;
	}
	/////////////////////////////////////////////////////////////
	//Show

	@RequestMapping(value = "/publisher/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int seasonId) {
		ModelAndView result;
		Season season;

		season = this.seasonService.findOne(seasonId);
		result = new ModelAndView("season/publisher/show");
		result.addObject("season", season);

		return result;
	}

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView publicShow(@RequestParam final int seasonId) {
		ModelAndView result;
		Season season;

		season = this.seasonService.findOne(seasonId);
		result = new ModelAndView("season/public/show");
		result.addObject("season", season);

		return result;
	}

	//////////////////////////////////////////////////////////
	//Create

	@RequestMapping(value = "/publisher/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int serieId) {
		final ModelAndView result;
		final Season s = this.seasonService.create();
		final SeasonForm season = this.seasonService.createForm(s);
		season.setSerieId(serieId);

		result = this.createAndEditModelAndView(season);
		return result;
	}

	///////////////////////////////////////////////////////////////////
	//Edit

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int seasonId) {
		ModelAndView result;
		final Season season = this.seasonService.findOne(seasonId);
		final SeasonForm form = this.seasonService.createForm(season);
		final Serie serie = this.serieService.findSerieAssociated(seasonId);

		serie.getSeasons().remove(season);
		this.serieService.save(serie);
		form.setSerieId(serie.getId());

		result = new ModelAndView("season/publisher/edit");
		result.addObject("season", form);
		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView adminEdit(@RequestParam final int seasonId) {
		ModelAndView result;
		final Season season = this.seasonService.findOne(seasonId);
		final SeasonForm form = this.seasonService.createForm(season);
		final Serie serie = this.serieService.findSerieAssociated(seasonId);

		serie.getSeasons().remove(season);
		this.serieService.save(serie);
		form.setSerieId(serie.getId());

		result = new ModelAndView("season/administrator/edit");
		result.addObject("season", form);
		return result;
	}

	/////////////////////////////////////////////////////////////////
	//Save

	@RequestMapping(value = "/publisher/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("season") final SeasonForm season, final BindingResult binding) {
		ModelAndView result;
		Season season2;
		final Serie serie = this.serieService.findOne(season.getSerieId());

		season2 = this.seasonService.reconstruct(season, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(season);
		else
			try {
				if (season2.getChapters().isEmpty()) {
					final Chapter c = this.chapterService.create();
					season2.getChapters().add(c);
				}
				this.seasonService.save(season2);
				final Collection<Season> seasons = serie.getSeasons();
				seasons.add(season2);
				serie.setSeasons(seasons);
				this.serieService.save(serie);
				result = this.list(serie.getId());
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(season, "season.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/administrator/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView adminSave(@ModelAttribute("season") final SeasonForm season, final BindingResult binding) {
		ModelAndView result;
		Season season2;
		final Serie serie = this.serieService.findOne(season.getSerieId());

		season2 = this.seasonService.reconstruct(season, binding);
		if (binding.hasErrors())
			result = this.adminEditModelAndView(season);
		else
			try {
				if (season2.getChapters().isEmpty()) {
					final Chapter c = this.chapterService.create();
					season2.getChapters().add(c);
				}
				this.seasonService.save(season2);
				final Collection<Season> seasons = serie.getSeasons();
				seasons.add(season2);
				serie.setSeasons(seasons);
				this.serieService.save(serie);
				result = this.publicList(serie.getId());
			} catch (final Throwable oops) {
				result = this.adminEditModelAndView(season, "season.commit.error");
			}
		return result;
	}

	///////////////////////////////////////////////////////////////////
	//Delete

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("season") final SeasonForm seasonForm, final BindingResult binding) {
		ModelAndView result;
		Season season;
		final Serie s = this.serieService.findOne(seasonForm.getSerieId());

		season = this.seasonService.reconstruct(seasonForm, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(seasonForm);
		else
			try {
				final Collection<Season> seasons = s.getSeasons();
				seasons.remove(season);
				s.setSeasons(seasons);
				this.serieService.save(s);
				this.seasonService.delete(season);
				result = this.list(s.getId());

			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(seasonForm, "season.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView adminDelete(@ModelAttribute("season") final SeasonForm seasonForm, final BindingResult binding) {
		ModelAndView result;
		Season season;
		final Serie s = this.serieService.findOne(seasonForm.getSerieId());

		season = this.seasonService.reconstruct(seasonForm, binding);
		if (binding.hasErrors())
			result = this.adminEditModelAndView(seasonForm);
		else
			try {
				final Collection<Season> seasons = s.getSeasons();
				seasons.remove(season);
				s.setSeasons(seasons);
				this.serieService.save(s);
				this.seasonService.delete(season);
				result = this.publicList(s.getId());

			} catch (final Throwable oops) {
				result = this.adminEditModelAndView(seasonForm, "season.commit.error");
			}
		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final SeasonForm season) {
		ModelAndView result;

		result = this.createAndEditModelAndView(season, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final SeasonForm season, final String message) {
		final ModelAndView result;

		result = new ModelAndView("season/publisher/create");
		result.addObject("season", season);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView adminEditModelAndView(final SeasonForm season) {
		ModelAndView result;

		result = this.adminEditModelAndView(season, null);

		return result;
	}

	protected ModelAndView adminEditModelAndView(final SeasonForm season, final String message) {
		final ModelAndView result;

		result = new ModelAndView("season/administrator/edit");
		result.addObject("season", season);
		result.addObject("message", message);

		return result;
	}

}
