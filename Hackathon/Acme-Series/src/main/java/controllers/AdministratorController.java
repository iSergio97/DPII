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

import services.ActorService;
import services.CommentService;
import services.MessageService;
import services.PublisherService;
import services.SeasonService;
import services.SerieService;
import services.SystemConfigurationService;
import domain.Actor;
import domain.Serie;
import domain.SystemConfiguration;
import forms.SystemConfigurationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private ActorService				actorService;
	@Autowired
	private CommentService				commentService;
	@Autowired
	private MessageService				messageService;
	@Autowired
	private PublisherService			publisherService;
	@Autowired
	private SeasonService				seasonService;
	@Autowired
	private SerieService				serieService;
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ----------------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// System configuration --------------------------------------------------------

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.GET)
	public ModelAndView systemConfiguration() {
		final ModelAndView result;
		final SystemConfigurationForm systemConfigurationForm;

		systemConfigurationForm = this.systemConfigurationService.deconstruct(this.systemConfigurationService.getSystemConfiguration());

		result = new ModelAndView("administrator/systemconfiguration");
		result.addObject("systemConfigurationForm", systemConfigurationForm);

		return result;
	}

	@RequestMapping(value = "/systemconfiguration", method = RequestMethod.POST)
	public ModelAndView systemConfiguration(@Valid @ModelAttribute("systemConfigurationForm") final SystemConfigurationForm systemConfigurationForm, final BindingResult bindingResult) {
		final ModelAndView result;
		SystemConfiguration systemConfiguration;

		if (bindingResult.hasErrors()) {
			result = new ModelAndView("administrator/systemconfiguration");
			result.addObject("systemConfigurationForm", systemConfigurationForm);
			result.addObject("error", true);
		} else {
			systemConfiguration = this.systemConfigurationService.reconstruct(systemConfigurationForm, bindingResult);
			this.systemConfigurationService.save(systemConfiguration);
			result = new ModelAndView("administrator/systemconfiguration");
			result.addObject("systemConfigurationForm", systemConfigurationForm);
			result.addObject("success", true);
		}

		return result;
	}

	// Dashboard -------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result = new ModelAndView("administrator/dashboard");

		// Mínimo, máximo, media y desviación típica del número de series por publicador.
		result.addObject("seriesPerPublisherStatistics", this.publisherService.getSeriesPerPublisherStatistics());

		// Mínimo, máximo, media y desviación típica del número de temporadas por serie.
		result.addObject("seasonsPerSerieStatistics", this.serieService.getSeasonsPerSerieStatistics());

		// Mínimo, máximo, media y desviación típica del número de capítulos por temporada.
		result.addObject("chaptersPerSeasonStatistics", this.seasonService.getChaptersPerSeasonStatistics());

		// Mínimo, máximo, media y desviación típica del número de comentarios por serie.
		result.addObject("commentsPerSerieStatistics", this.commentService.getCommentsPerSerieStatistics());

		// Top 5 series más comentadas.
		result.addObject("top5SeriesWithMostComments", this.serieService.getTop5SeriesWithMostComments());

		// Top 5 series mejor valoradas.
		result.addObject("top5SeriesWithBestAverageCritiqueScore", this.serieService.getTop5SeriesWithBestAverageCritiqueScore());

		// Mínimo, máximo, media y desviación típica de las valoraciones de los críticos por serie.
		final List<Serie> allSeries = this.serieService.findAll();
		final Map<Serie, Double[]> serieCritiqueScoreStatistics = new HashMap<>();
		for (final Serie serie : allSeries)
			serieCritiqueScoreStatistics.put(serie, this.serieService.getSerieCritiqueScoreStatistics(serie));
		result.addObject("allSeries", allSeries);
		result.addObject("serieCritiqueScoreStatistics", serieCritiqueScoreStatistics);

		return result;
	}

	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView actorList() {
		ModelAndView result;
		final List<Actor> listActors = this.actorService.findAll();
		double media;
		for (final Actor a : listActors) {
			if (this.messageService.countMails(a.getId()) != 0)
				media = this.messageService.countSpam(a.getId()) / this.messageService.countMails(a.getId());
			else
				media = 0.;
			if (media > 0.2)
				a.setIsFlagged(true);
		}
		result = new ModelAndView("administrator/actor/list");
		result.addObject("listActors", listActors);

		return result;

	}

	@RequestMapping(value = "/actor/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId) {
		final Actor a = this.actorService.findOne(actorId);
		a.setIsBanned(true);

		return this.actorList();
	}

	@RequestMapping(value = "/actor/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int actorId) {
		final Actor a = this.actorService.findOne(actorId);
		a.setIsBanned(false);

		return this.actorList();
	}

}
