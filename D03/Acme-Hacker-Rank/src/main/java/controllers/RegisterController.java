
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import forms.RegisterAdministratorForm;
import forms.RegisterCompanyForm;
import forms.RegisterHackerForm;
import services.AdministratorService;
import services.CompanyService;
import services.HackerService;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CompanyService			companyService;


	public RegisterController() {
		super();
	}

	@RequestMapping(value = "/administrator", method = RequestMethod.GET)
	public ModelAndView registerAdministrator() {
		ModelAndView result;
		RegisterAdministratorForm administrator;

		administrator = this.administratorService.createForm();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/hacker", method = RequestMethod.GET)
	public ModelAndView registerHacker() {
		ModelAndView result;
		RegisterHackerForm hacker;

		hacker = this.hackerService.createForm();
		result = this.createEditModelAndView(hacker);

		return result;
	}

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView registerCompany() {
		ModelAndView result;
		RegisterCompanyForm company;

		company = this.companyService.createForm();
		result = this.createEditModelAndView(company);

		return result;
	}

	protected <T> ModelAndView createEditModelAndView(final T t) {
		final ModelAndView result;

		result = this.createEditModelAndView(t, null);

		return result;
	}

	protected <T> ModelAndView createEditModelAndView(final T t, final String messageCode) {
		ModelAndView result = null;
		String type = null;

		if (t instanceof RegisterAdministratorForm) {
			type = "administrator";
			result = new ModelAndView("register/administrator/");
			result.addObject("actor", t);
			result.addObject("type", type);
		} else if (t instanceof RegisterHackerForm) {
			type = "hacker";
			result = new ModelAndView("register/hacker/");
		} else if (t instanceof RegisterCompanyForm) {
			type = "company";
			result = new ModelAndView("register/company/");
		}
		result.addObject("actor", t);
		result.addObject("type", type);

		return result;
	}

}
