
package controllers;

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
import services.AuditorService;
import services.PositionService;
import domain.Critique;
import domain.Critic;
import domain.Serie;
import forms.CritiqueForm;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService	auditService;
	@Autowired
	private AuditorService	auditorService;
	@Autowired
	private PositionService	positionService;


	// Constructors -----------------------------------------------------------

	public AuditController() {
		super();
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/auditor/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Critic auditor = this.auditorService.findPrincipal();
		if (auditor == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("audit/list");

		result.addObject("audits", this.auditService.getAuditsOfAuditor(auditor));

		return result;
	}

	@RequestMapping(value = "/all/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "positionId") final int positionId) {
		final ModelAndView result;

		final Serie position = this.positionService.findOne(positionId);
		if (position == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!position.getStatus().equals("ACCEPTED"))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("audit/list");

		result.addObject("audits", this.auditService.getAuditsOfPosition(position));

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/auditor/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "auditId") final int auditId) {
		final ModelAndView result;

		final Critic auditor = this.auditorService.findPrincipal();
		if (auditor == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Critique audit = this.auditService.findOne(auditId);
		if (audit == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!audit.getAuditor().equals(auditor))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("audit/show");

		result.addObject("audit", audit);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/auditor/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "positionId") final int positionId) {
		final ModelAndView result;

		final Critic auditor = this.auditorService.findPrincipal();
		if (auditor == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie position = this.positionService.findOne(positionId);
		if (position == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!position.getStatus().equals("ACCEPTED"))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("audit/edit");

		final CritiqueForm auditForm = this.auditService.createForm();
		auditForm.setPositionId(positionId);

		result.addObject("auditForm", auditForm);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/auditor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "auditId") final int auditId) {
		final ModelAndView result;

		final Critic auditor = this.auditorService.findPrincipal();
		if (auditor == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Critique audit = this.auditService.findOne(auditId);
		if (audit == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!audit.getIsDraft())
			return new ModelAndView("redirect:/welcome/index.do");
		if (!audit.getAuditor().equals(auditor))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("audit/edit");

		result.addObject("auditForm", this.auditService.deconstruct(audit));

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/auditor/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("auditForm") final CritiqueForm auditForm, final BindingResult bindingResult) {
		ModelAndView result;

		final Critic auditor = this.auditorService.findPrincipal();
		if (auditor == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Serie position = this.positionService.findOne(auditForm.getPositionId());
		if (position == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!position.getStatus().equals("ACCEPTED"))
			return new ModelAndView("redirect:/welcome/index.do");

		if (auditForm.getId() != 0) {
			final Critique audit = this.auditService.findOne(auditForm.getId());
			if (audit == null)
				return new ModelAndView("redirect:/welcome/index.do");
			if (!audit.getIsDraft())
				return new ModelAndView("redirect:/welcome/index.do");
			if (!audit.getAuditor().equals(auditor))
				return new ModelAndView("redirect:/welcome/index.do");
		}

		if (!bindingResult.hasErrors()) {
			Critique audit = this.auditService.reconstructForm(auditForm, bindingResult);
			audit.setAuditor(auditor);
			audit = this.auditService.save(audit);
			result = this.show(audit.getId());
		} else {
			result = new ModelAndView("audit/edit");
			result.addObject("auditForm", auditForm);
		}

		return result;
	}

	// Declare as final -------------------------------------------------------

	@RequestMapping(value = "/auditor/saveAsFinal", method = RequestMethod.POST)
	public ModelAndView saveAsFinal(@RequestParam(value = "auditId") final int auditId) {
		final Critic auditor = this.auditorService.findPrincipal();
		if (auditor == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Critique audit = this.auditService.findOne(auditId);
		if (audit == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!audit.getIsDraft())
			return new ModelAndView("redirect:/welcome/index.do");
		if (!audit.getAuditor().equals(auditor))
			return new ModelAndView("redirect:/welcome/index.do");

		audit.setIsDraft(false);
		this.auditService.save(audit);

		return this.show(auditId);
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/auditor/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "auditId") final int auditId) {
		final Critic auditor = this.auditorService.findPrincipal();
		if (auditor == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Critique audit = this.auditService.findOne(auditId);
		if (audit == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!audit.getIsDraft())
			return new ModelAndView("redirect:/welcome/index.do");
		if (!audit.getAuditor().equals(auditor))
			return new ModelAndView("redirect:/welcome/index.do");

		this.auditService.delete(audit);

		return this.list();
	}

}
