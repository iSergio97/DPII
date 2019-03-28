
package controllers;

import java.util.Collection;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AcmeFloatService;
import services.BrotherhoodService;
import domain.AcmeFloat;
import domain.Brotherhood;
import domain.Parade;
import forms.AcmeFloatForm;

@Controller
@RequestMapping("/float")
public class AcmeFloatController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AcmeFloatService	acmeFloatService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors -----------------------------------------------------------

	public AcmeFloatController() {
		super();
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "floatId") final int acmeFloatId) {
		final ModelAndView result;
		final AcmeFloat acmeFloat;

		acmeFloat = this.acmeFloatService.findOne(acmeFloatId);
		if (acmeFloat == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("float/public/" + "show");

		result.addObject("acmeFloat", acmeFloat);

		return result;
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<AcmeFloat> acmeFloats;
		final Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.findPrincipal();
		if (brotherhood == null)
			return new ModelAndView("redirect:/welcome/index.do");
		acmeFloats = brotherhood.getAcmeFloats();

		result = new ModelAndView("float/list");
		result.addObject("acmeFloats", acmeFloats);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final AcmeFloatForm acmeFloatForm;

		acmeFloatForm = this.acmeFloatService.createForm();

		result = new ModelAndView("float/edit");
		result.addObject("acmeFloatForm", acmeFloatForm);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final AcmeFloat acmeFloat;
		final AcmeFloatForm acmeFloatForm;

		if (this.brotherhoodService.findPrincipal() == null)
			return new ModelAndView("redirect:/welcome/index.do");
		acmeFloat = this.acmeFloatService.findOne(id);
		if (acmeFloat == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!this.brotherhoodService.findPrincipal().equals(acmeFloat.getBrotherhood()))
			return new ModelAndView("redirect:/welcome/index.do");
		acmeFloatForm = this.acmeFloatService.deconstruct(acmeFloat);

		result = new ModelAndView("float/edit");
		result.addObject("acmeFloatForm", acmeFloatForm);

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid @ModelAttribute("acmeFloatForm") final AcmeFloatForm acmeFloatForm, final BindingResult bindingResult) {
		final ModelAndView result;

		if (this.brotherhoodService.findPrincipal() == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!bindingResult.hasErrors()) {
			AcmeFloat acmeFloat;
			if (acmeFloatForm.getId() != 0) {
				acmeFloat = this.acmeFloatService.findOne(acmeFloatForm.getId());
				if (!this.brotherhoodService.findPrincipal().equals(acmeFloat.getBrotherhood()))
					return new ModelAndView("redirect:/welcome/index.do");
				if (acmeFloat == null)
					return new ModelAndView("redirect:/welcome/index.do");
			}
			acmeFloat = this.acmeFloatService.reconstruct(acmeFloatForm, bindingResult);
			acmeFloat.setBrotherhood(this.brotherhoodService.findPrincipal());
			acmeFloat = this.acmeFloatService.save(acmeFloat);
			result = this.show(acmeFloat.getId());
		} else {
			result = new ModelAndView("float/edit");
			result.addObject("acmeFloatForm", acmeFloatForm);
		}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final AcmeFloat acmeFloat;

		if (this.brotherhoodService.findPrincipal() == null)
			return new ModelAndView("redirect:/welcome/index.do");
		acmeFloat = this.acmeFloatService.findOne(id);
		if (acmeFloat == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!this.brotherhoodService.findPrincipal().equals(acmeFloat.getBrotherhood()))
			return new ModelAndView("redirect:/welcome/index.do");
		this.acmeFloatService.delete(acmeFloat);

		result = this.list();

		return result;
	}

}
