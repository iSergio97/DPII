/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.SystemConfigurationService;
import domain.Actor;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService				actorService;
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "user") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		String systemMessage;

		try {
			final int id = LoginService.getPrincipal().getId();
			final Actor actor = this.actorService.findByUserAccountId(id);
			name = actor.getName() + " " + actor.getSurname();
		} catch (final IllegalArgumentException e) {
			/*
			 * LoginService.getPrincipal() throws and IllegalArgumentException
			 * if no actor is logged in.
			 */
		}

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		systemMessage = "master.page.message." + this.systemConfigurationService.getSystemConfiguration().getMessage();

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("systemMessage", systemMessage);

		return result;
	}

}
