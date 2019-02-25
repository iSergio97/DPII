/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AcmeFloatService;
import services.BrotherhoodService;
import domain.AcmeFloat;
import domain.Brotherhood;

@Controller
@RequestMapping("/acmefloat")
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

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final AcmeFloat acmeFloat;
		final Brotherhood brotherhood;

		acmeFloat = this.acmeFloatService.findOne(id);
		brotherhood = this.brotherhoodService.findPrincipal();
		Assert.isTrue(acmeFloat.getBrotherhood().equals(brotherhood));

		result = new ModelAndView("acmefloat/show");
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
		acmeFloats = brotherhood.getAcmeFloats();

		result = new ModelAndView("acmefloat/list");
		result.addObject("acmeFloats", acmeFloats);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final AcmeFloat acmeFloat;

		acmeFloat = this.acmeFloatService.create();
		result = new ModelAndView("acmefloat/create");
		result.addObject("acmeFloat", acmeFloat);

		return result;
	}

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.POST)
	public ModelAndView create(AcmeFloat acmeFloat, final BindingResult binding) {
		final ModelAndView result;

		if (!binding.hasErrors()) {
			acmeFloat = this.acmeFloatService.save(acmeFloat);
			result = new ModelAndView("redirect:list.do");
		} else {
			result = new ModelAndView("acmefloat/create");
			result.addObject("acmeFloat", acmeFloat);
			result.addObject("showError", binding);
			result.addObject("erroresBinding", binding.getAllErrors());
			for (int i = 0; i < binding.getAllErrors().size(); i++)
				System.out.println("Error " + i + ": " + binding.getAllErrors().get(i));
		}

		return result;
	}

	// Update -----------------------------------------------------------------

	// TODO: update method

	// Delete -----------------------------------------------------------------

	// TODO: delete method

}
