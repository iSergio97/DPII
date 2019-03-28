/*
 * ParadeController.java
 * 
 * Copyright (C) 2019 Group 16 Desing & Testing II
 */

package controllers;

import java.util.Collection;
import java.util.Date;

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

import security.LoginService;
import security.UserAccount;
import services.AcmeFloatService;
import services.BrotherhoodService;
import services.ParadeService;
import domain.AcmeFloat;
import domain.Brotherhood;
import domain.Parade;
import forms.ParadeForm;

@Controller
@RequestMapping("/parade")
public class ParadeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private AcmeFloatService	acmeFloatService;


	// Constructors -----------------------------------------------------------

	public ParadeController() {

	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Parade> parades;

		parades = this.paradeService.findAllByBrotherhoodAccountId(LoginService.getPrincipal().getId());

		result = new ModelAndView("parade/brotherhood/list");
		result.addObject("parades", parades);
		result.addObject("requestURI", "parade/brotherhood/list.do");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Parade parade;

		parade = this.paradeService.create();
		parade.setIsDraft(true);
		result = this.createEditModelAndView(parade, "create");

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;

		parade = this.paradeService.findOne(paradeId);
		Assert.notNull(parade);
		if (LoginService.getPrincipal().getId() != parade.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:/welcome/index.do");
		else if (this.paradeService.findOne(parade.getId()).getIsDraft() == false)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = this.createEditModelAndView(parade, "edit");

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("parade") final ParadeForm paradeForm, final BindingResult binding) {
		ModelAndView result;
		Parade parade;

		if (paradeForm.getMoment() == null)
			// final ObjectError momentError = new ObjectError("moment", "Fecha no válida.");
			// binding.addError(momentError);
			binding.rejectValue("moment", "error.moment");
		else if (paradeForm.getMoment() != null && paradeForm.getMoment().before(new Date()))
			// final ObjectError momentBeforeError = new ObjectError("moment", "La fecha es anterior a la actual.");
			// binding.addError(momentBeforeError);
			binding.rejectValue("moment", "error.momentBefore");

		if (paradeForm.getId() != 0 && this.paradeService.findOne(paradeForm.getId()).getIsDraft() == false)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				parade = this.paradeService.reconstruct(paradeForm, binding);
				if (LoginService.getPrincipal().getId() != parade.getBrotherhood().getUserAccount().getId())
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					this.paradeService.save(parade);
					result = new ModelAndView("redirect:list.do");
				}
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(paradeForm, "edit");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(paradeForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Save in Final Mode -----------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "finalMode")
	public ModelAndView finalMode(final ParadeForm paradeForm, final BindingResult binding) {
		ModelAndView result;
		Parade parade;

		if (paradeForm.getMoment() == null)
			// final ObjectError momentError = new ObjectError("moment", "Fecha no válida.");
			// binding.addError(momentError);
			binding.rejectValue("moment", "error.moment");
		else if (paradeForm.getMoment() != null && paradeForm.getMoment().before(new Date()))
			// final ObjectError momentBeforeError = new ObjectError("moment", "La fecha es anterior a la actual.");
			// binding.addError(momentBeforeError);
			binding.rejectValue("moment", "error.momentBefore");

		if (paradeForm.getId() != 0 && this.paradeService.findOne(paradeForm.getId()).getIsDraft() == false)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				parade = this.paradeService.reconstruct(paradeForm, binding);
				if (LoginService.getPrincipal().getId() != parade.getBrotherhood().getUserAccount().getId())
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					parade.setIsDraft(false);
					this.paradeService.save(parade);
					result = new ModelAndView("redirect:list.do");
				}
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(paradeForm, "edit");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(paradeForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ParadeForm paradeForm, final BindingResult binding) {
		ModelAndView result;
		Parade parade;

		if (paradeForm.getId() != 0 && this.paradeService.findOne(paradeForm.getId()).getIsDraft() == false)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				parade = this.paradeService.reconstruct(paradeForm, binding);
				if (LoginService.getPrincipal().getId() != parade.getBrotherhood().getUserAccount().getId())
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					this.paradeService.delete(parade);
					result = new ModelAndView("redirect:list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(paradeForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;

		parade = this.paradeService.findOne(paradeId);
		Assert.notNull(parade);
		Assert.isTrue((!parade.getIsDraft()) || parade.getBrotherhood().getUserAccount().getId() == LoginService.getPrincipal().getId());

		result = new ModelAndView("parade/public/" + "show");

		result.addObject("parade", parade);

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Parade parade, final String method) {
		ModelAndView result;

		result = this.createEditModelAndView(parade, null, method);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Parade parade, final String messageCode, final String method) {
		final ModelAndView result;
		final Brotherhood brotherhood;
		final Collection<AcmeFloat> acmeFloats;
		final UserAccount userAccount = LoginService.getPrincipal();

		brotherhood = this.brotherhoodService.findPrincipal();
		acmeFloats = this.acmeFloatService.findFloatsByBrotherhoodUserAccount(userAccount.getId());

		result = new ModelAndView("parade/brotherhood/" + method);
		result.addObject("brotherhood", brotherhood);
		result.addObject("acmeFloats", acmeFloats);

		result.addObject("parade", parade);

		result.addObject("messageCode", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ParadeForm paradeForm, final String method) {
		ModelAndView result;

		result = this.createEditModelAndView(paradeForm, null, method);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ParadeForm paradeForm, final String messageCode, final String method) {
		final ModelAndView result;
		final Brotherhood brotherhood;
		final Collection<AcmeFloat> acmeFloats;
		final UserAccount userAccount = LoginService.getPrincipal();

		brotherhood = this.brotherhoodService.findPrincipal();
		acmeFloats = this.acmeFloatService.findFloatsByBrotherhoodUserAccount(userAccount.getId());

		result = new ModelAndView("parade/brotherhood/" + method);
		result.addObject("brotherhood", brotherhood);
		result.addObject("acmeFloats", acmeFloats);

		result.addObject("parade", paradeForm);

		result.addObject("messageCode", messageCode);

		return result;
	}

}
