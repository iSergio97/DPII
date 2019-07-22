/*
 * ProfileController.java
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Company;
import domain.Provider;
import domain.Rookie;
import forms.RegisterAdministratorForm;
import forms.RegisterAuditorForm;
import forms.RegisterCompanyForm;
import forms.RegisterProviderForm;
import forms.RegisterRookieForm;
import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.CompanyService;
import services.ProviderService;
import services.RookieService;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ProviderService			providerService;


	@RequestMapping(value = "/administrator/show", method = RequestMethod.GET)
	public ModelAndView showAdmin() {
		final ModelAndView result;
		final Administrator admin = this.administratorService.findPrincipal();
		result = this.createEditModelAndView(admin, "show");

		return result;
	}

	@RequestMapping(value = "/rookie/show", method = RequestMethod.GET)
	public ModelAndView showRookie() {
		final ModelAndView result;
		final Rookie admin = this.rookieService.findPrincipal();
		result = this.createEditModelAndView(admin, "show");

		return result;
	}

	@RequestMapping(value = "/company/show", method = RequestMethod.GET)
	public ModelAndView showCompany() {
		final ModelAndView result;
		final Company admin = this.companyService.findPrincipal();
		result = this.createEditModelAndView(admin, "show");

		return result;
	}

	@RequestMapping(value = "/auditor/show", method = RequestMethod.GET)
	public ModelAndView showAuditor() {
		final ModelAndView result;
		final Auditor admin = this.auditorService.findPrincipal();
		result = this.createEditModelAndView(admin, "show");

		return result;
	}

	@RequestMapping(value = "/provider/show", method = RequestMethod.GET)
	public ModelAndView showProvider() {
		final ModelAndView result;
		final Provider admin = this.providerService.findPrincipal();
		result = this.createEditModelAndView(admin, "show");

		return result;
	}

	// Model and view methods ------------------------------------------------------

	protected <T> ModelAndView createEditModelAndView(final T t) {
		final ModelAndView result;

		result = this.createEditModelAndView(t, null);

		return result;
	}

	@RequestMapping(value = "/actor/export", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView exportActor(final HttpServletResponse response) {
		final Actor actor = this.actorService.findPrincipal();
		ServletOutputStream outStream;
		ModelAndView result;
		try {
			final StringBuilder sb = new StringBuilder();
			sb.append("name");
			sb.append(";");
			sb.append("surnames");
			sb.append(";");
			sb.append("email");
			sb.append(";");
			sb.append("phoneNumber");
			sb.append(";");
			sb.append("photo");
			sb.append(";");
			sb.append("address");
			sb.append(";");
			sb.append("username");
			sb.append(";");
			sb.append("hashed pass");
			sb.append(";");
			sb.append("authority");
			sb.append(";");
			sb.append("\n");
			sb.append(actor.getName());
			sb.append(";");
			sb.append(actor.getSurnames());
			sb.append(";");
			sb.append(actor.getEmail());
			sb.append(";");
			sb.append(actor.getPhoneNumber());
			sb.append(";");
			sb.append(actor.getPhoto());
			sb.append(";");
			sb.append(actor.getAddress());
			sb.append(";");
			sb.append(actor.getUserAccount().getUsername());
			sb.append(";");
			sb.append(actor.getUserAccount().getPassword());
			sb.append(";");
			sb.append(actor.getUserAccount().getAuthorities().toArray()[0]);
			sb.append(";");
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment;filename=data.csv");
			outStream = response.getOutputStream();
			outStream.println(sb.toString());
			outStream.flush();
			outStream.close();
		} catch (final IOException e) {
			e.printStackTrace();
			return this.createModelAndViewWithSystemConfiguration("/welcome/index.do");
		}

		if (actor.getUserAccount().getAuthorities().toArray()[0].equals("COMPANY"))
			result = this.showCompany();
		else if (actor.getUserAccount().getAuthorities().toArray()[0].equals("ADMINISTRATOR"))
			result = this.showAdmin();
		else if (actor.getUserAccount().getAuthorities().toArray()[0].equals("AUDITOR"))
			result = this.showAuditor();
		else if (actor.getUserAccount().getAuthorities().toArray()[0].equals("PROVIDER"))
			result = this.showProvider();
		else if (actor.getUserAccount().getAuthorities().toArray()[0].equals("ROOKIE"))
			result = this.showRookie();
		else
			result = this.createModelAndViewWithSystemConfiguration("/welcome/index.do");

		return result;
	}

	//
	protected <T> ModelAndView createEditModelAndView(final T t, final String messageCode) {
		ModelAndView result = null;

		if (t instanceof RegisterAdministratorForm || t instanceof Administrator) {
			result = this.createModelAndViewWithSystemConfiguration("profile/administrator/" + messageCode);
			result.addObject("actor", t);
		} else if (t instanceof RegisterRookieForm || t instanceof Rookie) {
			result = this.createModelAndViewWithSystemConfiguration("profile/rookie/" + messageCode);
			result.addObject("actor", t);
		} else if (t instanceof RegisterCompanyForm || t instanceof Company) {
			result = this.createModelAndViewWithSystemConfiguration("profile/company/" + messageCode);
			result.addObject("actor", t);
		} else if (t instanceof RegisterProviderForm || t instanceof Provider) {
			result = this.createModelAndViewWithSystemConfiguration("profile/provider/" + messageCode);
			result.addObject("actor", t);
		} else if (t instanceof RegisterAuditorForm || t instanceof Auditor) {
			result = this.createModelAndViewWithSystemConfiguration("profile/auditor/" + messageCode);
			result.addObject("actor", t);

		}

		return result;
	}
}
