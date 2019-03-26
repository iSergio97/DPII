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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final AcmeFloat acmeFloat;
		final Brotherhood brotherhood;

		acmeFloat = this.acmeFloatService.findOne(id);
		brotherhood = this.brotherhoodService.findPrincipal();
		Assert.isTrue(acmeFloat.getBrotherhood().equals(brotherhood));

		result = new ModelAndView("float/show");
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
		final Collection<Parade> parades = this.brotherhoodService.findPrincipal().getParades();
		final HashMap<Integer, String> paradesMap = new HashMap<>();
		for (final Parade parade : parades)
			paradesMap.put(parade.getId(), parade.getTitle());

		result = new ModelAndView("float/edit");
		result.addObject("acmeFloatForm", acmeFloatForm);
		result.addObject("paradesMap", paradesMap);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final AcmeFloat acmeFloat;
		final AcmeFloatForm acmeFloatForm;

		acmeFloat = this.acmeFloatService.findOne(id);
		Assert.isTrue(acmeFloat.getBrotherhood().equals(this.brotherhoodService.findPrincipal()));
		acmeFloatForm = this.acmeFloatService.deconstruct(acmeFloat);
		final Collection<Parade> parades = this.brotherhoodService.findPrincipal().getParades();
		final HashMap<Integer, String> paradesMap = new HashMap<>();
		for (final Parade parade : parades)
			paradesMap.put(parade.getId(), parade.getTitle());

		result = new ModelAndView("float/edit");
		result.addObject("acmeFloatForm", acmeFloatForm);
		result.addObject("paradesMap", paradesMap);

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid @ModelAttribute("acmeFloatForm") final AcmeFloatForm acmeFloatForm, final BindingResult bindingResult) {
		final ModelAndView result;

		if (!bindingResult.hasErrors()) {
			AcmeFloat acmeFloat;
			if (acmeFloatForm.getId() != 0) {
				acmeFloat = this.acmeFloatService.findOne(acmeFloatForm.getId());
				Assert.isTrue(acmeFloat.getBrotherhood().equals(this.brotherhoodService.findPrincipal()));
			}
			acmeFloat = this.acmeFloatService.reconstruct(acmeFloatForm, bindingResult);
			acmeFloat.setBrotherhood(this.brotherhoodService.findPrincipal());
			if (acmeFloat.getParades() == null)
				acmeFloat.setParades(new ArrayList<Parade>());
			for (final Parade parade : acmeFloat.getParades())
				Assert.isTrue(parade.getBrotherhood().equals(this.brotherhoodService.findPrincipal()));
			acmeFloat = this.acmeFloatService.save(acmeFloat);
			result = this.show(acmeFloat.getId());
		} else {
			final Collection<Parade> parades = this.brotherhoodService.findPrincipal().getParades();
			final HashMap<Integer, String> paradesMap = new HashMap<>();
			for (final Parade parade : parades)
				paradesMap.put(parade.getId(), parade.getTitle());
			result = new ModelAndView("float/edit");
			result.addObject("acmeFloatForm", acmeFloatForm);
			result.addObject("paradesMap", paradesMap);
		}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final AcmeFloat acmeFloat;
		final Brotherhood brotherhood;

		acmeFloat = this.acmeFloatService.findOne(id);
		brotherhood = this.brotherhoodService.findPrincipal();
		Assert.isTrue(acmeFloat.getBrotherhood().equals(brotherhood));
		this.acmeFloatService.delete(acmeFloat);

		result = this.list();

		return result;
	}

}
