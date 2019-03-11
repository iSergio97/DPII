
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AcmeFloatService;
import services.BrotherhoodService;
import services.ParadeService;
import domain.AcmeFloat;
import domain.Brotherhood;
import domain.Parade;

@Controller
@RequestMapping("/parade/brotherhood")
public class ParadeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AcmeFloatService	acmeFloatService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ParadeService		paradeService;


	// Constructors -----------------------------------------------------------

	public ParadeController() {

	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Parade parade;

		parade = this.paradeService.create();
		result = this.createEditModelAndView(parade, "create");

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;

		parade = this.paradeService.findOne(paradeId);
		Assert.notNull(parade);
		result = this.createEditModelAndView(parade, "edit");

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Parade parade, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(parade, "edit");
		else
			try {
				this.paradeService.save(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Parade parade, final BindingResult binding) {
		ModelAndView result;

		try {
			this.paradeService.delete(parade);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error");
		}

		return result;
	}

	// Final Mode -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "finalMode")
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
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	private ModelAndView createEditModelAndView(final Parade parade, final String method) {
		ModelAndView result;

		result = this.createEditModelAndView(parade, null, method);

		return result;
	}

	private ModelAndView createEditModelAndView(final Parade parade, final String string, final String method) {
		final ModelAndView result;
		final Brotherhood brotherhood;
		final Collection<AcmeFloat> acmeFloats;

		brotherhood = this.brotherhoodService.findPrincipal();
		acmeFloats = this.acmeFloatService.findAcmeFloats();

		result = new ModelAndView("parade/brotherhood/" + method);
		result.addObject("brotherhood", brotherhood);
		result.addObject("acmeFloats", acmeFloats);

		result.addObject("parade", parade);

		return result;
	}

}
