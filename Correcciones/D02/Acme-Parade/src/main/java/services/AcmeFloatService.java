
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AcmeFloatRepository;
import utilities.ConversionUtils;
import domain.AcmeFloat;
import forms.AcmeFloatForm;

@Service
@Transactional
public class AcmeFloatService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private AcmeFloatRepository	acmeFloatRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator			validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public AcmeFloatService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public AcmeFloat create() {
		final AcmeFloat result = new AcmeFloat();

		// set fields
		result.setTitle("");
		result.setDescription("");
		result.setPictures(new ArrayList<String>());
		// set relationships
		result.setBrotherhood(null);

		return result;
	}

	public AcmeFloat save(final AcmeFloat acmeFloat) {
		Assert.isTrue(acmeFloat != null);
		return this.acmeFloatRepository.save(acmeFloat);
	}

	public Iterable<AcmeFloat> save(final Iterable<AcmeFloat> acmeFloats) {
		Assert.isTrue(acmeFloats != null);
		return this.acmeFloatRepository.save(acmeFloats);
	}

	public void delete(final AcmeFloat acmeFloat) {
		Assert.isTrue(acmeFloat != null);
		this.acmeFloatRepository.delete(acmeFloat);
	}

	public void delete(final Iterable<AcmeFloat> acmeFloats) {
		Assert.isTrue(acmeFloats != null);
		this.acmeFloatRepository.delete(acmeFloats);
	}

	public AcmeFloat findOne(final int id) {
		return this.acmeFloatRepository.findOne(id);
	}

	public List<AcmeFloat> findAll() {
		return this.acmeFloatRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public AcmeFloatForm createForm() {
		final AcmeFloatForm acmeFloatForm = new AcmeFloatForm();
		acmeFloatForm.setTitle("");
		acmeFloatForm.setDescription("");
		acmeFloatForm.setPictures("");
		return acmeFloatForm;
	}

	public AcmeFloat reconstruct(final AcmeFloatForm acmeFloatForm, final BindingResult bindingResult) {
		AcmeFloat result;

		if (acmeFloatForm.getId() == 0)
			result = this.create();
		else
			result = this.acmeFloatRepository.findOne(acmeFloatForm.getId());

		result.setTitle(acmeFloatForm.getTitle());
		result.setDescription(acmeFloatForm.getDescription());
		result.setPictures(ConversionUtils.stringToList(acmeFloatForm.getPictures(), " "));

		this.validator.validate(result, bindingResult);

		return result;
	}

	public AcmeFloatForm deconstruct(final AcmeFloat acmeFloat) {
		final AcmeFloatForm acmeFloatForm = this.createForm();

		acmeFloatForm.setId(acmeFloat.getId());
		acmeFloatForm.setTitle(acmeFloat.getTitle());
		acmeFloatForm.setDescription(acmeFloat.getDescription());
		acmeFloatForm.setPictures(ConversionUtils.listToString(acmeFloat.getPictures(), " "));

		return acmeFloatForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Collection<AcmeFloat> findFloatsByBrotherhoodUserAccount(final int id) {
		return this.acmeFloatRepository.findFloatsByBrotherhoodUserAccount(id);
	}

}
