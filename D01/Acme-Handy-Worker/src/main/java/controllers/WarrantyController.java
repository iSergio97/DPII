/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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

import services.FixUpTaskService;
import services.WarrantyService;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/administrator")
public class WarrantyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Create/Edit Fix-Up Task ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		// Create result object
		ModelAndView result;
		Collection<Warranty> warranties;
		Collection<Warranty> draftedWarranties;
		result = new ModelAndView("warranty/list");

		warranties = this.warrantyService.findAll();
		draftedWarranties = this.warrantyService.findDrafted();
		warranties.removeAll(draftedWarranties);
		result.addObject("warranties", warranties);
		result.addObject("draftedWarranties", draftedWarranties);
		result.addObject("requestURI", "warranty/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int warrantyId) {
		// Create result object
		ModelAndView result;
		Warranty warranty;
		result = new ModelAndView("warranty/show");
		warranty = this.warrantyService.findById(warrantyId);
		result.addObject("warranty", warranty);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Warranty warranty;
		warranty = this.warrantyService.create();
		result = this.createEditModelAndView(warranty);

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView saveDraft(@RequestParam final int warrantyId) {
		ModelAndView result;
		Warranty warranty;
		warranty = this.warrantyService.findById(warrantyId);
		warranty.setDraft(false);
		this.warrantyService.save(warranty);
		result = new ModelAndView("warranty/show");
		Warranty warranty2;
		warranty2 = this.warrantyService.findById(warrantyId);
		result.addObject("warranty", warranty2);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId) {
		ModelAndView result;
		Warranty warranty;
		warranty = this.warrantyService.findById(warrantyId);
		Assert.notNull(warranty);
		result = this.createEditModelAndView(warranty);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Warranty warranty, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(warranty);
		else
			try {
				this.warrantyService.save(warranty);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(warranty, "warranty.commit.error");
			}

		return result;

	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int warrantyId) {
		ModelAndView result;
		Warranty warranty;
		warranty = this.warrantyService.findById(warrantyId);
		try {
			this.warrantyService.delete(warranty);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(warranty, "warranty.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Warranty warranty) {
		final ModelAndView result;
		result = this.createEditModelAndView(warranty, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Warranty warranty, final String messageCode) {
		final ModelAndView result;
		Integer s = warranty.getApplicableLaws().size();
		if (s <= 0)
			s = 1;

		result = new ModelAndView("warranty/edit");
		result.addObject("warranty", warranty);
		result.addObject("message", messageCode);
		result.addObject("size", s);
		return result;

	}
}
