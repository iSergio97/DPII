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
import java.util.List;

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
import domain.Procession;

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

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.POST, params = "addPicture")
	public ModelAndView show(@RequestParam(value = "id") final int id, @RequestParam(value = "picture") final String picture) {
		AcmeFloat acmeFloat;
		final Brotherhood brotherhood;

		acmeFloat = this.acmeFloatService.findOne(id);
		brotherhood = this.brotherhoodService.findPrincipal();
		Assert.isTrue(acmeFloat.getBrotherhood().equals(brotherhood));

		final List<String> pictures = acmeFloat.getPictures();
		pictures.add(picture);
		acmeFloat.setPictures(pictures);

		acmeFloat = this.acmeFloatService.save(acmeFloat);

		return this.show(acmeFloat.getId());
	}

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.POST, params = "deletePicture")
	public ModelAndView show(@RequestParam(value = "id") final int id, @RequestParam(value = "pictureIndex") final int pictureIndex) {
		AcmeFloat acmeFloat;
		final Brotherhood brotherhood;

		acmeFloat = this.acmeFloatService.findOne(id);
		brotherhood = this.brotherhoodService.findPrincipal();
		Assert.isTrue(acmeFloat.getBrotherhood().equals(brotherhood));

		final List<String> pictures = acmeFloat.getPictures();
		pictures.remove(pictureIndex);
		acmeFloat.setPictures(pictures);

		acmeFloat = this.acmeFloatService.save(acmeFloat);

		return this.show(acmeFloat.getId());
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
		final Brotherhood brotherhood;

		acmeFloat = this.acmeFloatService.create();
		brotherhood = this.brotherhoodService.findPrincipal();
		acmeFloat.setBrotherhood(brotherhood);
		final Collection<Procession> processions = this.brotherhoodService.findPrincipal().getProcessions();
		final HashMap<Integer, String> processionsMap = new HashMap<>();
		for (final Procession procession : processions)
			processionsMap.put(procession.getId(), procession.getTitle());

		result = new ModelAndView("acmefloat/create");
		result.addObject("acmeFloat", acmeFloat);
		result.addObject("processionsMap", processionsMap);

		return result;
	}

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.POST)
	public ModelAndView create(AcmeFloat acmeFloat, final BindingResult binding) {
		final ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.findPrincipal();
		Assert.isTrue(acmeFloat.getBrotherhood().equals(brotherhood));
		if (acmeFloat.getProcessions() == null)
			acmeFloat.setProcessions(new ArrayList<Procession>());
		for (final Procession procession : acmeFloat.getProcessions())
			Assert.isTrue(procession.getBrotherhood().equals(brotherhood));
		if (!binding.hasErrors()) {
			acmeFloat.setBrotherhood(brotherhood);
			acmeFloat = this.acmeFloatService.save(acmeFloat);
			result = this.show(acmeFloat.getId());
		} else {
			result = new ModelAndView("acmefloat/create");
			final Collection<Procession> processions = this.brotherhoodService.findPrincipal().getProcessions();
			final HashMap<Integer, String> processionsMap = new HashMap<>();
			for (final Procession procession : processions)
				processionsMap.put(procession.getId(), procession.getTitle());
			result.addObject("acmeFloat", acmeFloat);
			result.addObject("processionsMap", processionsMap);
			result.addObject("showError", binding);
			result.addObject("erroresBinding", binding.getAllErrors());
			for (int i = 0; i < binding.getAllErrors().size(); i++)
				System.out.println("Error " + i + ": " + binding.getAllErrors().get(i));
		}

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final AcmeFloat acmeFloat;
		final Brotherhood brotherhood;

		acmeFloat = this.acmeFloatService.findOne(id);
		brotherhood = this.brotherhoodService.findPrincipal();
		Assert.isTrue(acmeFloat.getBrotherhood().equals(brotherhood));
		final Collection<Procession> processions = this.brotherhoodService.findPrincipal().getProcessions();
		final HashMap<Integer, String> processionsMap = new HashMap<>();
		for (final Procession procession : processions)
			processionsMap.put(procession.getId(), procession.getTitle());

		result = new ModelAndView("acmefloat/edit");
		result.addObject("acmeFloat", acmeFloat);
		result.addObject("processionsMap", processionsMap);

		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST)
	public ModelAndView edit(AcmeFloat acmeFloat, final BindingResult binding) {
		final ModelAndView result;
		final Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.findPrincipal();
		Assert.isTrue(acmeFloat.getBrotherhood().equals(brotherhood));
		if (acmeFloat.getProcessions() == null)
			acmeFloat.setProcessions(new ArrayList<Procession>());
		for (final Procession procession : acmeFloat.getProcessions())
			Assert.isTrue(procession.getBrotherhood().equals(brotherhood));

		if (!binding.hasErrors()) {
			acmeFloat = this.acmeFloatService.save(acmeFloat);
			result = this.show(acmeFloat.getId());
		} else {
			result = new ModelAndView("acmefloat/edit");
			final Collection<Procession> processions = this.brotherhoodService.findPrincipal().getProcessions();
			final HashMap<Integer, String> processionsMap = new HashMap<>();
			for (final Procession procession : processions)
				processionsMap.put(procession.getId(), procession.getTitle());
			result.addObject("acmeFloat", acmeFloat);
			result.addObject("processionsMap", processionsMap);
			result.addObject("showError", binding);
			result.addObject("erroresBinding", binding.getAllErrors());
			for (int i = 0; i < binding.getAllErrors().size(); i++)
				System.out.println("Error " + i + ": " + binding.getAllErrors().get(i));
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
