
package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import security.UserAccount;
import security.UserAccountRepository;
import services.AdministratorService;
import services.AuditorService;
import services.CompanyService;
import services.MessageBoxService;
import services.RookieService;
import domain.Administrator;
import domain.Auditor;
import domain.Company;
import domain.MessageBox;
import domain.Rookie;
import forms.RegisterAdministratorForm;
import forms.RegisterAuditorForm;
import forms.RegisterCompanyForm;
import forms.RegisterRookieForm;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private MessageBoxService		messageBoxService;


	public RegisterController() {
		super();
	}

	// Rookie -------------------------------------------------------------------------

	@RequestMapping(value = "/rookie/create", method = RequestMethod.GET)
	public ModelAndView registerRookie() {
		ModelAndView result;
		RegisterRookieForm rookie;

		rookie = this.rookieService.createForm();
		result = this.createEditModelAndView(rookie);

		return result;
	}

	@RequestMapping(value = "/rookie/edit", method = RequestMethod.GET)
	public ModelAndView editRookie() {
		final Rookie rookie = this.rookieService.findPrincipal();
		final RegisterRookieForm rhf = this.rookieService.deconstruct(rookie);

		return this.createEditModelAndView(rhf);
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/rookie/edit", method = RequestMethod.POST)
	public ModelAndView registerRookiePost(@ModelAttribute("rookie") final RegisterRookieForm registerRookieForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Rookie rookie2;
		final List<String> usernames = this.userAccountRepository.getUserNames();

		final Calendar calendar = Calendar.getInstance();
		final Date date = calendar.getTime();

		if (registerRookieForm.getExpirationYear() < (date.getYear() % 100)) {
			final ObjectError error = new ObjectError("expirationYear", "The year of the credit card is older than the current year");
			bindingResult.addError(error);
			bindingResult.rejectValue("expirationYear", "error.oldYear");
		}
		if (registerRookieForm.getExpirationYear() == (date.getYear() % 100) && registerRookieForm.getExpirationMonth() < (date.getMonth() + 1)) {
			final ObjectError error = new ObjectError("expirationMonth", "The month of the credit card is older than the current month");
			bindingResult.addError(error);
			bindingResult.rejectValue("expirationMonth", "error.oldMonth");
		}

		if (registerRookieForm.getId() == 0) {
			if (usernames.contains(registerRookieForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Rookie rookie3 = this.rookieService.findPrincipal();
			usernames.remove(rookie3.getUserAccount().getUsername());
			if (usernames.contains(registerRookieForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (registerRookieForm.getUsername().length() < 5 || registerRookieForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!registerRookieForm.getPassword().equals(registerRookieForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (registerRookieForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (registerRookieForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		try {
			rookie2 = this.rookieService.reconstructForm(registerRookieForm, bindingResult);
			final UserAccount ua = rookie2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(rookie2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			rookie2.setUserAccount(uaSaved);
			// Esto no hace falta: Spring te actualiza la variable de entrada al salir del método
			final Rookie rookieSaved = this.rookieService.save(rookie2);
			if (rookie2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(rookieSaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerRookieForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerRookieForm, "register.rookie.error");
			for (final ObjectError e : bindingResult.getAllErrors())
				System.out.println(e);
		}

		return result;
	}

	// Company ------------------------------------------------------------------------

	@RequestMapping(value = "/company/create", method = RequestMethod.GET)
	public ModelAndView registerCompany() {
		ModelAndView result;
		RegisterCompanyForm company;

		company = this.companyService.createForm();
		result = this.createEditModelAndView(company);

		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.POST)
	public ModelAndView registerCompanyPost(@ModelAttribute("company") final RegisterCompanyForm registerCompanyForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Company company2;
		final List<String> usernames = this.userAccountRepository.getUserNames();

		final Date date = new Date();
		if (registerCompanyForm.getExpirationMonth() < date.getMonth() && registerCompanyForm.getExpirationYear() < (date.getYear() % 100))
			bindingResult.reject("creditCard", "This credit card is expired. Please introduce other");

		if (registerCompanyForm.getId() == 0) {
			if (usernames.contains(registerCompanyForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Company company3 = this.companyService.findPrincipal();
			usernames.remove(company3.getUserAccount().getUsername());
			if (usernames.contains(registerCompanyForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (registerCompanyForm.getUsername().length() < 5 || registerCompanyForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!registerCompanyForm.getPassword().equals(registerCompanyForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (registerCompanyForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (registerCompanyForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		try {
			company2 = this.companyService.reconstructForm(registerCompanyForm, bindingResult);
			final UserAccount ua = company2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(company2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			company2.setUserAccount(uaSaved);
			final Company companySaved = this.companyService.save(company2);
			if (company2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(companySaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerCompanyForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerCompanyForm, "register.rookie.error");
		}

		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView editC() {
		final Company company = this.companyService.findPrincipal();
		final RegisterCompanyForm rhf = this.companyService.deconstruct(company);

		return this.createEditModelAndView(rhf);
	}

	// Auditor ------------------------------------------------------------------------

	@RequestMapping(value = "/auditor/create", method = RequestMethod.GET)
	public ModelAndView registerAuditor() {
		ModelAndView result;
		RegisterAuditorForm auditor;
		auditor = this.auditorService.createForm();
		result = this.createEditModelAndView(auditor);

		return result;
	}

	@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST)
	public ModelAndView registerAuditorPost(@ModelAttribute("auditor") final RegisterAuditorForm registerAuditorForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Auditor auditor2;
		final List<String> usernames = this.userAccountRepository.getUserNames();

		final Date date = new Date();
		if (registerAuditorForm.getExpirationMonth() < date.getMonth() && registerAuditorForm.getExpirationYear() < (date.getYear() % 100))
			bindingResult.reject("creditCard", "This credit card is expired. Please introduce other");

		if (registerAuditorForm.getId() == 0) {
			if (usernames.contains(registerAuditorForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Auditor auditor3 = this.auditorService.findPrincipal();
			usernames.remove(auditor3.getUserAccount().getUsername());
			if (usernames.contains(registerAuditorForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (registerAuditorForm.getUsername().length() < 5 || registerAuditorForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!registerAuditorForm.getPassword().equals(registerAuditorForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (registerAuditorForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (registerAuditorForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		try {
			auditor2 = this.auditorService.reconstructForm(registerAuditorForm, bindingResult);
			final UserAccount ua = auditor2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(auditor2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			auditor2.setUserAccount(uaSaved);
			final Auditor auditorSaved = this.auditorService.save(auditor2);
			if (auditor2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(auditorSaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerAuditorForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerAuditorForm, "register.rookie.error");
		}

		return result;
	}

	@RequestMapping(value = "/auditor/edit", method = RequestMethod.GET)
	public ModelAndView editAuditor() {
		final Auditor auditor = this.auditorService.findPrincipal();
		final RegisterAuditorForm raf = this.auditorService.deconstruct(auditor);

		return this.createEditModelAndView(raf);
	}

	// Administrator ------------------------------------------------------------------

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
		final List<String> usernames = this.userAccountRepository.getUserNames();

		final Date date = new Date();
		if (registerAdministratorForm.getExpirationMonth() < date.getMonth() && registerAdministratorForm.getExpirationYear() < (date.getYear() % 100))
			bindingResult.reject("creditCard", "This credit card is expired. Please introduce other");

		if (registerAdministratorForm.getId() == 0) {
			if (usernames.contains(registerAdministratorForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Administrator administrator3 = this.administratorService.findPrincipal();
			usernames.remove(administrator3.getUserAccount().getUsername());
			if (usernames.contains(registerAdministratorForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (registerAdministratorForm.getUsername().length() < 5 || registerAdministratorForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!registerAdministratorForm.getPassword().equals(registerAdministratorForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (registerAdministratorForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (registerAdministratorForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		try {
			administrator2 = this.administratorService.reconstructForm(registerAdministratorForm, bindingResult);
			final UserAccount ua = administrator2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(administrator2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			administrator2.setUserAccount(uaSaved);
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
			result = this.createEditModelAndView(registerAdministratorForm, "register.rookie.error");
		}

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView editA() {
		final Administrator admin = this.administratorService.findPrincipal();
		final RegisterAdministratorForm raf = this.administratorService.deconstruct(admin);

		return this.createEditModelAndView(raf);
	}

	// Model and view methods ---------------------------------------------------------

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
		} else if (t instanceof RegisterAuditorForm) {
			result = new ModelAndView("register/auditor/create");
			result.addObject("auditor", t);
		} else if (t instanceof RegisterRookieForm) {
			result = new ModelAndView("register/rookie/create");
			result.addObject("rookie", t);
		} else if (t instanceof RegisterCompanyForm) {
			result = new ModelAndView("register/company/create");
			result.addObject("company", t);
		}

		return result;
	}

}
