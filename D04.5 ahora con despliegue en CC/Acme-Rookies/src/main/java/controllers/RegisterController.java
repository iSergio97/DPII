
package controllers;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.Auditor;
import domain.Company;
import domain.MessageBox;
import domain.Provider;
import domain.Rookie;
import forms.RegisterAdministratorForm;
import forms.RegisterAuditorForm;
import forms.RegisterCompanyForm;
import forms.RegisterProviderForm;
import forms.RegisterRookieForm;
import security.UserAccount;
import security.UserAccountRepository;
import services.AdministratorService;
import services.AuditorService;
import services.CompanyService;
import services.MessageBoxService;
import services.ProviderService;
import services.RookieService;

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
	private ProviderService			providerService;

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

	@RequestMapping(value = "/rookie/edit", method = RequestMethod.POST)
	public ModelAndView registerRookiePost(@ModelAttribute("rookie") final RegisterRookieForm registerRookieForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Rookie rookie2;

		try {
			rookie2 = this.rookieService.reconstructForm(registerRookieForm, bindingResult);
			final UserAccount ua = rookie2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(rookie2.getUserAccount().getPassword(), null));
			// Esto no hace falta: Spring te actualiza la variable de entrada al salir del método
			if (!rookie2.getPhoneNumber().startsWith("+"))
				rookie2.setPhoneNumber("+34 " + rookie2.getPhoneNumber());
			Assert.isTrue(!bindingResult.hasErrors());
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			rookie2.setUserAccount(uaSaved);
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

		try {
			company2 = this.companyService.reconstructForm(registerCompanyForm, bindingResult);
			final UserAccount ua = company2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(company2.getUserAccount().getPassword(), null));
			if (!company2.getPhoneNumber().startsWith("+"))
				company2.setPhoneNumber("+34" + company2.getPhoneNumber());
			Assert.isTrue(!bindingResult.hasErrors());
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
		try {
			auditor2 = this.auditorService.reconstructForm(registerAuditorForm, bindingResult);
			final UserAccount ua = auditor2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(auditor2.getUserAccount().getPassword(), null));
			if (!auditor2.getPhoneNumber().startsWith("+"))
				auditor2.setPhoneNumber("+34" + auditor2.getPhoneNumber());
			Assert.isTrue(!bindingResult.hasErrors());
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

	// Provider ------------------------------------------------------------------------

	@RequestMapping(value = "/provider/create", method = RequestMethod.GET)
	public ModelAndView registerProvider() {
		ModelAndView result;
		RegisterProviderForm provider;
		provider = this.providerService.createForm();
		result = this.createEditModelAndView(provider);

		return result;
	}

	@RequestMapping(value = "/provider/edit", method = RequestMethod.POST)
	public ModelAndView registerProviderPost(@ModelAttribute("provider") final RegisterProviderForm registerProviderForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Provider provider2;

		try {
			provider2 = this.providerService.reconstructForm(registerProviderForm, bindingResult);
			final UserAccount ua = provider2.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(provider2.getUserAccount().getPassword(), null));
			if (!provider2.getPhoneNumber().startsWith("+"))
				provider2.setPhoneNumber("+34" + provider2.getPhoneNumber());
			Assert.isTrue(!bindingResult.hasErrors());
			final UserAccount uaSaved = this.userAccountRepository.save(ua);
			provider2.setUserAccount(uaSaved);
			final Provider providerSaved = this.providerService.save(provider2);
			if (provider2.getId() == 0)
				for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
					mb.setActor(providerSaved);
					this.messageBoxService.save(mb);
				}
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(registerProviderForm);
		} catch (final Throwable valExp) {
			result = this.createEditModelAndView(registerProviderForm, "register.provider.error");
		}

		return result;
	}

	@RequestMapping(value = "/provider/edit", method = RequestMethod.GET)
	public ModelAndView editProvider() {
		final Provider provider = this.providerService.findPrincipal();
		final RegisterProviderForm rpf = this.providerService.deconstruct(provider);

		return this.createEditModelAndView(rpf);
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
		try {
			administrator2 = this.administratorService.reconstructForm(registerAdministratorForm, bindingResult);
			final UserAccount ua = administrator2.getUserAccount();
			if (!administrator2.getPhoneNumber().startsWith("+"))
				administrator2.setPhoneNumber("+34" + administrator2.getPhoneNumber());
			Assert.isTrue(!bindingResult.hasErrors());
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
		} else if (t instanceof RegisterProviderForm) {
			result = new ModelAndView("register/provider/create");
			result.addObject("provider", t);
		}

		return result;
	}

}
