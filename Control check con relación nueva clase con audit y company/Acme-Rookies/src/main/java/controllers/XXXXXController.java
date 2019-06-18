
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.CompanyService;
import services.XXXXXService;
import domain.Audit;
import domain.Company;
import domain.XXXXX;
import forms.XXXXXForm;

@Controller
@RequestMapping("/xxxxx/company")
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Company company;
		final Collection<XXXXX> xxxxxs;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		xxxxxs = this.xxxxxService.findByCompany(company);

		result = this.createModelAndViewWithSystemConfiguration("xxxxx/company/list");
		result.addObject("xxxxxs", xxxxxs);

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
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

		result = this.createModelAndViewWithSystemConfiguration("xxxxx/all/show");

		result.addObject("xxxxx", xxxxx);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "auditId") final int auditId) {
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

		xxxxxForm = this.xxxxxService.createForm();
		xxxxxForm.setId(auditId);

		result = this.createModelAndViewWithSystemConfiguration("xxxxx/all/edit");
		result.addObject("xxxxxForm", xxxxxForm);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
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

		result = this.createModelAndViewWithSystemConfiguration("xxxxx/all/edit");

		result.addObject("xxxxxForm", this.xxxxxService.deconstruct(xxxxx));

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid @ModelAttribute("xxxxxForm") final XXXXXForm xxxxxForm, final BindingResult bindingResult) {
		final ModelAndView result;
		final Company company;
		XXXXX xxxxx;

		company = this.companyService.findPrincipal();
		if (company == null)
			return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		if (xxxxxForm.getId() != 0) {
			xxxxx = this.xxxxxService.findOne(xxxxxForm.getId());
			if (xxxxx == null)
				return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
			if (!xxxxx.getCompany().equals(company))
				return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
			if (!xxxxx.getDraftMode())
				return this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		}
		if (!bindingResult.hasErrors()) {
			xxxxx = this.xxxxxService.reconstruct(xxxxxForm, bindingResult);
			xxxxx = this.xxxxxService.save(xxxxx);
			result = this.show(xxxxx.getId());
		} else {
			result = this.createModelAndViewWithSystemConfiguration("xxxxx/all/edit");
			result.addObject("xxxxxForm", xxxxxForm);
		}

		return result;
	}

	// Declare as final -------------------------------------------------------

	@RequestMapping(value = "/saveAsFinal", method = RequestMethod.POST)
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

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
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

}
