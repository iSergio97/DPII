
package controllers;

import java.util.Collection;

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
import domain.Company;
import domain.XXXXX;
import forms.XXXXXForm;
import services.AuditService;
import services.CompanyService;
import services.XXXXXService;

@Controller
@RequestMapping("/xxxxx")
public class XXXXXController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService	auditService;
	@Autowired
	private CompanyService	companyService;
	@Autowired
	private XXXXXService	xxxxxService;


	// Constructors -----------------------------------------------------------

	public XXXXXController() {
		super();
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Company company;
		final Collection<XXXXX> xxxxxs;
		Boolean logged;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		xxxxxs = this.xxxxxService.findByCompany(company);
		logged = true;

		result = this.createModelAndViewWithSystemConfiguration("xxxxx/company/list");
		result.addObject("xxxxxs", xxxxxs);
		result.addObject("logged", logged);

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/company/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "xxxxxId") final int xxxxxId) {
		final ModelAndView result;
		final Company company;
		final XXXXX xxxxx;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		xxxxx = this.xxxxxService.findOne(xxxxxId);
		if (xxxxx == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!xxxxx.getCompany().equals(company))
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		result = this.createModelAndViewWithSystemConfiguration("xxxxx/company/show");

		result.addObject("xxxxx", xxxxx);
		result.addObject("logged", "logged");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/company/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "auditId") final Integer auditId) {
		final ModelAndView result;
		final Company company;
		final Audit audit;
		final XXXXXForm xxxxxForm;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		audit = this.auditService.findOne(auditId);
		if (audit == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (auditId == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		xxxxxForm = this.xxxxxService.createForm();
		xxxxxForm.setAuditId(auditId);

		result = this.createModelAndViewWithSystemConfiguration("xxxxx/company/edit");
		result.addObject("xxxxx", xxxxxForm);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "xxxxxId") final int xxxxxId) {
		final ModelAndView result;
		final Company company;
		final XXXXX xxxxx;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		xxxxx = this.xxxxxService.findOne(xxxxxId);
		if (xxxxx == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!xxxxx.getCompany().equals(company))
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!xxxxx.getDraftMode())
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		result = this.createModelAndViewWithSystemConfiguration("xxxxx/company/edit");

		result.addObject("xxxxxForm", this.xxxxxService.deconstruct(xxxxx));

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/company/save", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("xxxxx") final XXXXXForm xxxxxForm, final BindingResult bindingResult) {
		ModelAndView result;
		XXXXX xxxxx;

		if (bindingResult.hasErrors())
			result = this.createModelAndViewWithSystemConfiguration("xxxxx/company/edit");
		else
			try {
				xxxxx = this.xxxxxService.reconstruct(xxxxxForm, bindingResult);
				xxxxx = this.xxxxxService.save(xxxxx);
				result = this.show(xxxxx.getId());
			} catch (final ValidationException valExp) {
				result = this.createModelAndViewWithSystemConfiguration("xxxxx/company/save");
			} catch (final Throwable oops) {
				result = this.createModelAndViewWithSystemConfiguration("xxxxx/company/save");
			}
		return result;
	}

	// Declare as final -------------------------------------------------------

	@RequestMapping(value = "/company/saveAsFinal", method = RequestMethod.POST)
	public ModelAndView saveAsFinal(@RequestParam(value = "xxxxxId") final int xxxxxId) {
		final Company company;
		XXXXX xxxxx;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		xxxxx = this.xxxxxService.findOne(xxxxxId);
		if (xxxxx == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!xxxxx.getCompany().equals(company))
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!xxxxx.getDraftMode())
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		xxxxx.setDraftMode(false);
		this.xxxxxService.save(xxxxx);

		return this.show(xxxxxId);
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/company/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "xxxxxId") final int xxxxxId) {
		final Company company;
		XXXXX xxxxx;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		xxxxx = this.xxxxxService.findOne(xxxxxId);
		if (xxxxx == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!xxxxx.getCompany().equals(company))
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (!xxxxx.getDraftMode())
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		this.xxxxxService.delete(xxxxx);

		return this.list();
	}

	@RequestMapping(value = "/public/list", method = RequestMethod.GET)
	public ModelAndView publicList(@RequestParam final int auditId) {
		final Collection<XXXXX> xxxxx = this.xxxxxService.findPublicByAudit(auditId);
		final ModelAndView result = this.createModelAndViewWithSystemConfiguration("xxxxx/public/list");
		result.addObject("xxxxx", xxxxx);

		return result;
	}

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView publicShow(@RequestParam final int xxxxxId) {
		final XXXXX x = this.xxxxxService.findOne(xxxxxId);
		final ModelAndView result = this.createModelAndViewWithSystemConfiguration("xxxxx/public/show");
		result.addObject("x", x);

		return result;
	}

}
