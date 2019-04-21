
package controllers;

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

import domain.Company;
import domain.Hacker;
import domain.MessageBox;
import forms.RegisterAdministratorForm;
import forms.RegisterCompanyForm;
import forms.RegisterHackerForm;
import security.UserAccount;
import security.UserAccountRepository;
import services.AdministratorService;
import services.CompanyService;
import services.HackerService;
import services.MessageBoxService;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private MessageBoxService		messageBoxService;


	public RegisterController() {
		super();
	}

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView registerAdministrator() {
		ModelAndView result;
		RegisterAdministratorForm administrator;

		administrator = this.administratorService.createForm();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/hacker/create", method = RequestMethod.GET)
	public ModelAndView registerHacker() {
		ModelAndView result;
		RegisterHackerForm hacker;

		hacker = this.hackerService.createForm();
		result = this.createEditModelAndView(hacker);

		return result;
	}

	@RequestMapping(value = "/hacker/edit", method = RequestMethod.GET)
	public ModelAndView editH() {
		final Hacker hacker = this.hackerService.findPrincipal();
		final RegisterHackerForm rhf = this.hackerService.deconstruct(hacker);

		return this.createEditModelAndView(rhf);
	}

	@RequestMapping(value = "/hacker/edit", method = RequestMethod.POST)
	public ModelAndView registerHackerPost(@ModelAttribute("hacker") final RegisterHackerForm registerHackerForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Hacker hacker2;
		final List<String> usernames = this.userAccountRepository.getUserNames();

		final Date date = new Date();
		if (registerHackerForm.getExpirationMonth() < date.getMonth() && registerHackerForm.getExpirationYear() < (date.getYear() % 100))
			bindingResult.reject("creditCard", "This credit card is expired. Please introduce other");

		if (registerHackerForm.getId() == 0) {
			if (usernames.contains(registerHackerForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Hacker hacker3 = this.hackerService.findPrincipal();
			usernames.remove(hacker3.getUserAccount().getUsername());
			if (usernames.contains(registerHackerForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (registerHackerForm.getUsername().length() < 5 || registerHackerForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!registerHackerForm.getPassword().equals(registerHackerForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (registerHackerForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (registerHackerForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		try {
			hacker2 = this.hackerService.reconstructForm(registerHackerForm, bindingResult);
			final UserAccount ua = hacker2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(hacker2.getUserAccount().getPassword(), null));
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			hacker2.setUserAccount(uaSaved);
			final Hacker hackerSaved = this.hackerService.save(hacker2);
			if (hacker2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(hackerSaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerHackerForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerHackerForm, "register.hacker.error");
			for (final ObjectError e : bindingResult.getAllErrors())
				System.out.println(e);
		}

		return result;
	}

	//COMPANY -----------------------------------------------------------------
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
			result = this.createEditModelAndView(registerCompanyForm, "register.hacker.error");
		}

		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView editC() {
		final Company company = this.companyService.findPrincipal();
		final RegisterCompanyForm rhf = this.companyService.deconstruct(company);

		return this.createEditModelAndView(rhf);
	}

	//PROTECTED METHODS -------------------------------------------------------

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
		} else if (t instanceof RegisterHackerForm) {
			result = new ModelAndView("register/hacker/create");
			result.addObject("hacker", t);
		} else if (t instanceof RegisterCompanyForm) {
			result = new ModelAndView("register/company/create");
			result.addObject("company", t);
		}

		return result;
	}

}
