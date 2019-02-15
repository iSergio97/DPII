/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TutorialService;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TutorialService	tutorialService;


	// Create/Edit Fix-Up Task ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		// Create result object
		ModelAndView result;
		final Collection<Tutorial> tutorials;
		result = new ModelAndView("tutorial/list");

		tutorials = this.tutorialService.findAll();
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/list.do");

		return result;
	}

	@RequestMapping(value = "/listhandyworker", method = RequestMethod.GET)
	public ModelAndView listHandyWorker() {
		// Create result object
		ModelAndView result;
		final Collection<Tutorial> tutorials;
		result = new ModelAndView("tutorial/handy-worker/list");

		tutorials = this.tutorialService.findAll();
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {
		// Create result object
		ModelAndView result;
		Tutorial tutorial;
		Sponsorship sponsorship;
		result = new ModelAndView("tutorial/display");
		tutorial = this.tutorialService.findById(tutorialId);
		final List<Sponsorship> sponsorships = (List<Sponsorship>) tutorial.getSponsorships();
		final Random rand = new Random();
		if (!(sponsorships.isEmpty() || sponsorships == null))
			sponsorship = sponsorships.get(rand.nextInt(sponsorships.size()));

		else
			sponsorship = null;
		result.addObject("tutorial", tutorial);
		result.addObject("sponsorship", sponsorship);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tutorial tutorial;
		tutorial = this.tutorialService.create();
		result = this.createEditModelAndView(tutorial);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;
		tutorial = this.tutorialService.findById(tutorialId);

		Assert.notNull(tutorial);
		result = this.createEditModelAndView(tutorial);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial);
		else
			try {
				this.tutorialService.save(tutorial);

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;
		tutorial = this.tutorialService.findById(tutorialId);
		try {
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		final ModelAndView result;
		result = this.createEditModelAndView(tutorial, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("tutorial/handy-worker/edit");
		final Integer size = tutorial.getPictures().size() - 1;

		result.addObject("tutorial", tutorial);
		result.addObject("size", size);
		result.addObject("message", messageCode);
		return result;

	}
}
