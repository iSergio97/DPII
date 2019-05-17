
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import domain.Item;
import domain.Provider;
import forms.ItemForm;

@Service
@Transactional
public class ItemService extends AbstractService<ItemRepository, Item> {

	@Autowired
	private ItemRepository	itemRepository;

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private Validator		validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public ItemService() {
		super();
	}

	@Override
	public Item create() {
		final Item item = new Item();
		final Provider provider = this.providerService.findPrincipal();

		item.setProvider(provider);
		item.setName("");
		item.setDescription("");
		item.setLink("");
		item.setPictures("");

		return item;
	}

	///////////////////////////////////////////////////////////////////////////
	// Form methods

	public ItemForm createForm() {
		final ItemForm form = new ItemForm();

		form.setName("");
		form.setDescription("");
		form.setLink("");
		form.setPictures("");

		return form;
	}

	public Item reconstruct(final ItemForm form, final BindingResult binding) {
		Item result;

		if (form.getId() == 0)
			result = this.create();
		else
			result = this.itemRepository.findOne(form.getId());

		result.setName(form.getName());
		result.setDescription(form.getDescription());
		result.setLink(form.getLink());
		result.setPictures(form.getPictures());

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Item> findByProviderId(final int providerId) {
		return this.itemRepository.findByProviderId(providerId);
	}

	public Double min() {
		return this.itemRepository.min();
	}

	public Double max() {
		return this.itemRepository.max();
	}

	public Double media() {
		return this.itemRepository.media();
	}

	public Double stdDev() {
		return this.itemRepository.stdDev();
	}

}
