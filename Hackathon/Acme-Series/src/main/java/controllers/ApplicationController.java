
package controllers;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.Application;
import domain.Serie;
import forms.ApplicationForm;
import security.LoginService;
import services.AdministratorService;
import services.ApplicationService;
import services.SerieService;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SerieService			serieService;


	// Constructors -----------------------------------------------------------

	public ApplicationController() {
		super();
	}

	// List (Publisher) -------------------------------------------------------

	@RequestMapping(value = "/publisher/list", method = RequestMethod.GET)
	public ModelAndView publisherList() {
		final ModelAndView result;
		final List<Application> applications;

		final int userAccountId = LoginService.getPrincipal().getId();
		applications = this.applicationService.findAllByUserAccountId(userAccountId);

		result = this.createModelAndViewWithSystemConfiguration("application/publisher/list");
		result.addObject("applications", applications);
		result.addObject("role", "publisher");
		result.addObject("requestURI", "application/publisher/list.do");

		return result;
	}

	// List (Administrator) ---------------------------------------------------

	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView administratorList() {
		final ModelAndView result;
		final List<Application> applications;

		applications = this.applicationService.findAll();

		result = this.createModelAndViewWithSystemConfiguration("application/administrator/list");
		result.addObject("applications", applications);
		result.addObject("role", "administrator");
		result.addObject("requestURI", "application/administrator/list.do");

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/administrator,publisher/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		try {
			application = this.applicationService.findOne(applicationId);
			Assert.notNull(application);
			result = this.createEditModelAndView(application, "administrator,publisher", "show");
		} catch (final Throwable oops) {
			return this.createModelAndViewWithSystemConfiguration("redirect:/panic.do");
		}

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/publisher/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int serieId) {
		final ModelAndView result;
		final Application application;
		final Serie serie;

		serie = this.serieService.findOne(serieId);

		try {
			application = this.applicationService.create();
			final List<Application> acceptedApplications = this.applicationService.findAllAcceptedBySerieId(serieId);

			Assert.notNull(serie);
			Assert.isTrue(acceptedApplications.isEmpty());
			Assert.isTrue(LoginService.getPrincipal().equals(serie.getPublisher().getUserAccount()));
		} catch (final Throwable oops) {
			return this.createModelAndViewWithSystemConfiguration("redirect:/panic.do");
		}

		result = this.createEditModelAndView(application, "publisher", "create");
		result.addObject("serieId", serie.getId());

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("application") final ApplicationForm applicationForm, final BindingResult binding) {
		ModelAndView result;
		Application application;

		try {
			application = this.applicationService.reconstructForm(applicationForm, binding);
			this.applicationService.save(application);
			result = this.createModelAndViewWithSystemConfiguration("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(applicationForm, "publisher", "edit");
			result.addObject("serieId", applicationForm.getSerieId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(applicationForm, "application.commit.error", "publisher", "edit");
			result.addObject("serieId", applicationForm.getSerieId());
		}
		return result;
	}

	// Accept -----------------------------------------------------------------

	@RequestMapping(value = "/administrator/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView result;
		final Administrator administrator;
		final Application application;

		administrator = this.administratorService.findPrincipal();

		try {
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(administrator != null);
			Assert.isTrue(application != null && application.getStatus().equals("PENDING"));
			final Serie serie = application.getSerie();
			final List<Application> pendingApplications = this.applicationService.findAllPendingBySerieId(serie.getId());
			for (final Application a : pendingApplications) {
				a.setStatus("REJECTED");
				a.setAdministrator(administrator);
				this.applicationService.save(a);
			}
			application.setStatus("ACCEPTED");
			application.setAdministrator(administrator);
			this.applicationService.save(application);
			serie.setIsDraft(false);
			this.serieService.save(serie);
			// TODO: Send Notification
			result = this.createModelAndViewWithSystemConfiguration("redirect:list.do");
		} catch (final Throwable oops) {
			return this.createModelAndViewWithSystemConfiguration("redirect:/panic.do");
		}

		return result;
	}

	// Reject -----------------------------------------------------------------

	@RequestMapping(value = "/administrator/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView result;
		final Administrator administrator;
		final Application application;

		administrator = this.administratorService.findPrincipal();

		try {
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(administrator != null);
			Assert.isTrue(application != null && application.getStatus().equals("PENDING"));
			application.setStatus("REJECTED");
			application.setAdministrator(administrator);
			this.applicationService.save(application);
			// TODO: Send Notification
			result = this.createModelAndViewWithSystemConfiguration("redirect:list.do");
		} catch (final Throwable oops) {
			return this.createModelAndViewWithSystemConfiguration("redirect:/panic.do");
		}

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	private ModelAndView createEditModelAndView(final Application application, final String role, final String action) {
		return this.createEditModelAndView(application, null, role, action);
	}

	private ModelAndView createEditModelAndView(final Application application, final String messageCode, final String role, final String action) {
		final ModelAndView result;

		result = this.createModelAndViewWithSystemConfiguration("application/" + role + "/" + action);
		result.addObject("application", application);

		return result;
	}

	private ModelAndView createEditModelAndView(final ApplicationForm applicationForm, final String role, final String action) {
		return this.createEditModelAndView(applicationForm, null, role, action);
	}

	private ModelAndView createEditModelAndView(final ApplicationForm applicationForm, final String messageCode, final String role, final String action) {
		final ModelAndView result;

		result = this.createModelAndViewWithSystemConfiguration("application/" + role + "/" + action);
		result.addObject("application", applicationForm);

		return result;
	}

}
