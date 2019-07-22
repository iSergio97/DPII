
package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.Quolet;
import forms.QuoletForm;
import services.AuditService;
import services.AuditorService;
import services.CompanyService;
import services.QuoletService;

@Controller
@RequestMapping("/quolet")
public class QuoletController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService	auditService;
	@Autowired
	private CompanyService	companyService;
	@Autowired
	private QuoletService	quoletService;
	@Autowired
	private AuditorService	auditorService;


	// Constructors -----------------------------------------------------------

	public QuoletController() {
		super();
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Company company;
		final List<Quolet> quolets;
		Boolean logged;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		quolets = (List<Quolet>) this.quoletService.findByCompany(company);
		logged = true;

		result = this.createModelAndViewWithSystemConfiguration("quolet/company/list");

		final int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		final int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final Date currentDate = new Date();

		result.addObject("currentDate", currentDate);
		result.addObject("currentDay", day);
		result.addObject("currentMonth", month);
		result.addObject("currentYear", year);
		result.addObject("quolets", quolets);
		result.addObject("logged", logged);

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/company/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "quoletId") final int quoletId) {
		final ModelAndView result;
		final Company company;
		final Quolet quolet;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		quolet = this.quoletService.findOne(quoletId);
		if (quolet == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!quolet.getCompany().equals(company))
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		result = this.createModelAndViewWithSystemConfiguration("quolet/company/show");

		result.addObject("quolet", quolet);
		result.addObject("logged", "logged");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/company/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "auditId") final Integer auditId) {
		final ModelAndView result;
		final Company company;
		final Audit audit;
		final QuoletForm quoletForm;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		audit = this.auditService.findOne(auditId);
		if (audit == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (auditId == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		quoletForm = this.quoletService.createForm();
		quoletForm.setAuditId(auditId);

		result = this.createModelAndViewWithSystemConfiguration("quolet/company/edit");
		result.addObject("quolet", quoletForm);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "quoletId") final int quoletId) {
		final ModelAndView result;
		final Company company;
		final Quolet quolet;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		quolet = this.quoletService.findOne(quoletId);
		if (quolet == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!quolet.getCompany().equals(company))
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!quolet.getDraftMode())
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		result = this.createModelAndViewWithSystemConfiguration("quolet/company/edit");

		result.addObject("quoletOriginal", quolet);
		result.addObject("quolet", this.quoletService.deconstruct(quolet));

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/company/save", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("quolet") final QuoletForm quoletForm, final BindingResult bindingResult) {
		ModelAndView result;
		Quolet quolet;

		if (bindingResult.hasErrors())
			result = this.createModelAndViewWithSystemConfiguration("quolet/company/edit");
		else
			try {
				quolet = this.quoletService.reconstruct(quoletForm, bindingResult);
				quolet = this.quoletService.save(quolet);
				result = this.show(quolet.getId());
			} catch (final ValidationException valExp) {
				result = this.createModelAndViewWithSystemConfiguration("quolet/company/save");
			} catch (final Throwable oops) {
				result = this.createModelAndViewWithSystemConfiguration("quolet/company/save");
			}
		return result;
	}

	// Declare as final -------------------------------------------------------

	@RequestMapping(value = "/company/saveAsFinal", method = RequestMethod.GET)
	public ModelAndView saveAsFinal(@RequestParam(value = "quoletId") final int quoletId) {
		final Company company;
		Quolet quolet;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		quolet = this.quoletService.findOne(quoletId);
		if (quolet == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!quolet.getCompany().equals(company))
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!quolet.getDraftMode())
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		quolet.setDraftMode(false);
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		final Date date = calendar.getTime();
		quolet.setPublicationMoment(date);
		this.quoletService.save(quolet);

		return this.show(quoletId);
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/company/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "quoletId") final int quoletId) {
		final Company company;
		Quolet quolet;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		quolet = this.quoletService.findOne(quoletId);
		if (quolet == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!quolet.getCompany().equals(company))
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!quolet.getDraftMode())
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		this.quoletService.delete(quolet);

		return this.list();
	}

	// Auditors methods

	@RequestMapping(value = "/auditor/show", method = RequestMethod.GET)
	public ModelAndView showAuditor(@RequestParam(value = "quoletId") final int quoletId) {
		final ModelAndView result;
		final Auditor principal;
		List<Quolet> quolet = new ArrayList<>();

		principal = this.auditorService.findPrincipal();
		final Audit audit = this.auditService.findOne(quolet.get(0).getAudit().getId());
		quolet = (List<Quolet>) this.quoletService.findByAudit(audit);

		if (quolet.size() == 0)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		final Auditor auditor = this.auditorService.findOne(audit.getId());

		if (principal == null || principal.getId() != auditor.getId())
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		if (principal.getId() != audit.getId())
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		result = this.createModelAndViewWithSystemConfiguration("quolet/company/show");

		result.addObject("quolet", quolet);
		result.addObject("logged", "logged");

		return result;
	}

	//Public methods

	@RequestMapping(value = "/public/list", method = RequestMethod.GET)
	public ModelAndView publicList(@RequestParam final int auditId) {
		final Collection<Quolet> quolet = this.quoletService.findPublicByAudit(auditId);
		final ModelAndView result = this.createModelAndViewWithSystemConfiguration("quolet/public/list");
		result.addObject("quolet", quolet);

		return result;
	}

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView publicShow(@RequestParam final int quoletId) {
		final Quolet x = this.quoletService.findOne(quoletId);
		final ModelAndView result = this.createModelAndViewWithSystemConfiguration("quolet/public/show");
		result.addObject("x", x);

		return result;
	}

}
