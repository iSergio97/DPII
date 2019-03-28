
package services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PriorityRepository;
import utilities.ConversionUtils;
import domain.Priority;
import forms.PriorityForm;

@Service
@Transactional
public class PriorityService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private PriorityRepository	priorityRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator			validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public PriorityService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Priority create() {
		final Priority priority = new Priority();
		priority.setStrings(new HashMap<String, String>());
		return priority;
	}

	public Priority save(final Priority priority) {
		Assert.isTrue(priority != null);
		return this.priorityRepository.save(priority);
	}

	public Iterable<Priority> save(final Iterable<Priority> priorities) {
		Assert.isTrue(priorities != null);
		return this.priorityRepository.save(priorities);
	}

	public void delete(final Priority priority) {
		Assert.isTrue(priority != null);
		this.priorityRepository.delete(priority);
	}

	public void delete(final Iterable<Priority> priorities) {
		Assert.isTrue(priorities != null);
		this.priorityRepository.delete(priorities);
	}

	public Priority findOne(final int id) {
		return this.priorityRepository.findOne(id);
	}

	public List<Priority> findAll() {
		return this.priorityRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public PriorityForm createForm() {
		final PriorityForm priorityForm = new PriorityForm();
		priorityForm.setStrings("");
		return priorityForm;
	}

	public Priority reconstruct(final PriorityForm priorityForm, final BindingResult bindingResult) {
		Priority result;

		if (priorityForm.getId() == 0)
			result = this.create();
		else
			result = this.priorityRepository.findOne(priorityForm.getId());

		result.setStrings(ConversionUtils.stringToMap(priorityForm.getStrings(), ":", ";"));

		this.validator.validate(result, bindingResult);

		return result;
	}

	public PriorityForm deconstruct(final Priority priority) {
		final PriorityForm priorityForm = this.createForm();

		priorityForm.setId(priority.getId());
		priorityForm.setStrings(ConversionUtils.mapToString(priority.getStrings(), ":", ";"));

		return priorityForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
