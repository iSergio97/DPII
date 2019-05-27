
package controllers;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.Critic;
import domain.MessageBox;
import domain.Publisher;
import domain.User;
import forms.RegisterAdministratorForm;
import forms.RegisterCriticForm;
import forms.RegisterPublisherForm;
import forms.RegisterUserForm;
import security.UserAccount;
import security.UserAccountRepository;
import services.AdministratorService;
import services.CriticService;
import services.MessageBoxService;
import services.PublisherService;
import services.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

	// Services --------------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private PublisherService		publisherService;
	@Autowired
	private CriticService			criticService;
	@Autowired
	private UserService				userService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Constructors ----------------------------------------------------------------

	public RegisterController() {
		super();
	}

	// Administrator ---------------------------------------------------------------

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView registerAdministrator() {
		ModelAndView result;
		RegisterAdministratorForm administrator;
		administrator = this.administratorService.createForm();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST)
	public ModelAndView registerAdministratorPost(@ModelAttribute("administrator") final RegisterAdministratorForm registerAdministratorForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Administrator administrator2;

		try {
			administrator2 = this.administratorService.reconstructForm(registerAdministratorForm, bindingResult);
			final UserAccount ua = administrator2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(administrator2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			administrator2.setUserAccount(uaSaved);
			if (!administrator2.getPhoneNumber().startsWith("+"))
				administrator2.setPhoneNumber("+34" + administrator2.getPhoneNumber());
			final Administrator administratorSaved = this.administratorService.save(administrator2);
			if (administrator2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(administratorSaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerAdministratorForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerAdministratorForm, "register.user.error");
		}

		return result;
	}

	// Publisher -------------------------------------------------------------------

	@RequestMapping(value = "/publisher/create", method = RequestMethod.GET)
	public ModelAndView registerPublisher() {
		ModelAndView result;
		RegisterPublisherForm publisher;
		publisher = this.publisherService.createForm();
		result = this.createEditModelAndView(publisher);

		return result;
	}

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.POST)
	public ModelAndView registerPublisherPost(@ModelAttribute("publisher") final RegisterPublisherForm registerPublisherForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Publisher publisher2;
		try {
			publisher2 = this.publisherService.reconstructForm(registerPublisherForm, bindingResult);
			final UserAccount ua = publisher2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(publisher2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			publisher2.setUserAccount(uaSaved);
			final Publisher publisherSaved = this.publisherService.save(publisher2);
			if (publisher2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(publisherSaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerPublisherForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerPublisherForm, "register.publisher.error");
		}

		return result;
	}

	// Critic ----------------------------------------------------------------------

	@RequestMapping(value = "/critic/create", method = RequestMethod.GET)
	public ModelAndView registerCritic() {
		ModelAndView result;
		RegisterCriticForm critic;

		critic = this.criticService.createForm();
		result = this.createEditModelAndView(critic);

		return result;
	}

	@RequestMapping(value = "/critic/edit", method = RequestMethod.POST)
	public ModelAndView registerCriticPost(@ModelAttribute("critic") final RegisterCriticForm registerCriticForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Critic critic2;
		try {
			critic2 = this.criticService.reconstructForm(registerCriticForm, bindingResult);
			final UserAccount ua = critic2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(critic2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			critic2.setUserAccount(uaSaved);
			if (!critic2.getPhoneNumber().startsWith("+"))
				critic2.setPhoneNumber("+34" + critic2.getPhoneNumber());
			final Critic criticSaved = this.criticService.save(critic2);
			if (critic2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(criticSaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerCriticForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerCriticForm, "register.user.error");
		}

		return result;
	}

	// User ------------------------------------------------------------------------

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView registerUser() {
		ModelAndView result;
		RegisterUserForm user;

		user = this.userService.createForm();
		result = this.createEditModelAndView(user);

		return result;
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public ModelAndView registerUserPost(@ModelAttribute("user") final RegisterUserForm registerUserForm, final BindingResult bindingResult) {
		ModelAndView result;
		final User user2;

		try {
			user2 = this.userService.reconstructForm(registerUserForm, bindingResult);
			if (!user2.getPhoneNumber().startsWith("+"))
				user2.setPhoneNumber("+34 " + user2.getPhoneNumber());
			final UserAccount ua = user2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(user2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			user2.setUserAccount(uaSaved);
			final User userSaved = this.userService.save(user2);
			if (user2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(userSaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerUserForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerUserForm, "register.user.error");
			for (final ObjectError e : bindingResult.getAllErrors())
				System.out.println(e);
		}

		return result;
	}

	// Model and view methods ------------------------------------------------------

	protected <T> ModelAndView createEditModelAndView(final T t) {
		final ModelAndView result;

		result = this.createEditModelAndView(t, null);

		return result;
	}

	protected <T> ModelAndView createEditModelAndView(final T t, final String messageCode) {
		ModelAndView result = null;

		if (t instanceof RegisterAdministratorForm) {
			result = new ModelAndView("register/administrator/create");
			result.addObject("administrator", t);
		} else if (t instanceof RegisterPublisherForm) {
			result = new ModelAndView("register/publisher/create");
			result.addObject("publisher", t);
		} else if (t instanceof RegisterCriticForm) {
			result = new ModelAndView("register/critic/create");
			result.addObject("critic", t);
		} else if (t instanceof RegisterUserForm) {
			result = new ModelAndView("register/user/create");
			result.addObject("user", t);
		}

		return result;
	}

}
