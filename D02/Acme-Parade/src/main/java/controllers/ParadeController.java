
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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
	private ParadeService	paradeService;

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
		result = this.createEditModelAndView(parade, "edit");

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ParadeForm paradeForm, final BindingResult binding) {
		ModelAndView result;
		final Parade parade = this.paradeService.findOne(paradeForm.getId());

		try {
			final Parade paradeUpdated = this.paradeService.reconstruct(paradeForm, binding);
			List<AcmeFloat> paradesRemoved = new ArrayList<>(); //Cambiar list por collection
			if (paradeForm.getId() != 0)
				paradesRemoved = (List<AcmeFloat>) parade.getAcmeFloats();
			if (paradeForm.getId() != 0) //Cambiar paradeForm por paradeUpdated
				paradesRemoved.removeAll(paradeUpdated.getAcmeFloats());
			final Parade paradeSaved = this.paradeService.save(paradeUpdated);
			for (final AcmeFloat f : paradeUpdated.getAcmeFloats()) {
				final Collection<Parade> parades = f.getParades();
				parades.add(paradeSaved);
				f.setParades(parades);
				this.acmeFloatService.save(f);
			}
			if (paradeUpdated.getId() != 0)
				for (final AcmeFloat f : paradesRemoved) {
					final Collection<Parade> parades = f.getParades();
					parades.remove(parade);
					f.setParades(parades);
					this.acmeFloatService.save(f);
				}
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(parade, "edit");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error", "edit");
		}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Parade parade, final BindingResult binding) {
		ModelAndView result;

		try {
			this.paradeService.delete(parade);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error", "edit");
		}

		return result;
	}

	// Save in Final Mode -----------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "finalMode")
	public ModelAndView finalMode(@Valid final Parade parade, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(parade, "edit");
		else
			try {
				parade.setIsDraft(false);
				this.paradeService.save(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "parade.commit.error", "edit");
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
		Assert.isTrue(parade.getIsDraft());

		result = new ModelAndView("parade/public/" + "show");

		result.addObject("parade", parade);

		// result.addObject("messageCode", null);

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
		acmeFloats = this.acmeFloatService.findAcmeFloats(userAccount.getId());

		result = new ModelAndView("parade/brotherhood/" + method);
		result.addObject("brotherhood", brotherhood);
		result.addObject("acmeFloats", acmeFloats);

		result.addObject("parade", parade);

		result.addObject("messageCode", messageCode);

		return result;
	}

}
