
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ProviderService;
import domain.Item;
import domain.Provider;
import forms.ItemForm;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	/////////////////////////////////////////////////////////////////////////
	//Services

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ProviderService	providerService;


	////////////////////////////////////////////////////////////////////////
	//Contructors

	public ItemController() {

	}

	///////////////////////////////////////////////////////////////////////
	//list

	@RequestMapping(value = "/provider/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Provider provider = this.providerService.findPrincipal();
		final Collection<Item> items = this.itemService.findByProviderId(provider.getId());

		result = new ModelAndView("item/provider/list");
		result.addObject("items", items);

		return result;
	}

	@RequestMapping(value = "/public/list", method = RequestMethod.GET)
	public ModelAndView publicList() {
		ModelAndView result;
		final Collection<Item> items = this.itemService.findAll();

		result = new ModelAndView("item/public/list");
		result.addObject("items", items);

		return result;
	}

	@RequestMapping(value = "/public/listP", method = RequestMethod.GET)
	public ModelAndView publicListProviders(@RequestParam final int providerId) {
		ModelAndView result;
		final Collection<Item> items = this.itemService.findByProviderId(providerId);

		result = new ModelAndView("item/public/listP");
		result.addObject("items", items);

		return result;
	}

	//////////////////////////////////////////////////////////////////////
	//Show

	@RequestMapping(value = "/provider/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int itemId) {
		ModelAndView result;
		Item item;

		item = this.itemService.findOne(itemId);
		Assert.notNull(item);

		result = new ModelAndView("item/provider/show");
		result.addObject("item", item);

		return result;
	}

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView publicShow(@RequestParam final int itemId) {
		ModelAndView result;
		final Item item = this.itemService.findOne(itemId);

		result = new ModelAndView("item/public/show");
		result.addObject("item", item);

		return result;
	}

	/////////////////////////////////////////////////////////////////////
	//Create

	@RequestMapping(value = "/provider/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ItemForm item;

		item = this.itemService.createForm();
		result = this.createAndEditModelAndView(item);

		return result;
	}

	///////////////////////////////////////////////////////////////////
	//Edit

	@RequestMapping(value = "/provider/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int itemId) {
		ModelAndView result;
		Item item;

		item = this.itemService.findOne(itemId);
		Assert.notNull(item);

		result = new ModelAndView("item/provider/edit");
		result.addObject("item", item);

		return result;
	}

	///////////////////////////////////////////////////////////////////
	//Save

	@RequestMapping(value = "/provider/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("item") final ItemForm item, final BindingResult binding) {
		ModelAndView result;
		Item item2;

		item2 = this.itemService.reconstruct(item, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(item);
		else
			try {
				this.itemService.save(item2);
				result = this.list();
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(item, "item.commit.error");
			}
		return result;
	}

	///////////////////////////////////////////////////////////////////
	//Delete

	@RequestMapping(value = "/provider/edit", params = "delete", method = RequestMethod.POST)
	public ModelAndView delete(@ModelAttribute("item") final ItemForm itemForm, final BindingResult binding) {
		ModelAndView result;
		Item item;

		item = this.itemService.reconstruct(itemForm, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(itemForm);
		else
			try {
				this.itemService.delete(item);
				result = this.list();
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(itemForm, "item.commit.error");
			}
		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final ItemForm item) {
		ModelAndView result;

		result = this.createAndEditModelAndView(item, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final ItemForm item, final String message) {
		final ModelAndView result;

		result = new ModelAndView("item/provider/create");
		result.addObject("item", item);
		result.addObject("message", message);

		return result;
	}

}
