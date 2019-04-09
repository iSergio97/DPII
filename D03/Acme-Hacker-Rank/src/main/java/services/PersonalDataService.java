
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalDataRepository;
import domain.PersonalData;
import forms.PersonalDataForm;

@Service
@Transactional
public class PersonalDataService {

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	@Autowired
	private Validator				validator;


	public PersonalDataService() {
		super();
	}

	public PersonalData create() {
		final PersonalData pData = new PersonalData();

		pData.setFullName("");
		pData.setGitHubProfile("");
		pData.setLinkedInProfile("");
		pData.setPhoneNumber("");
		pData.setStatement("");

		return pData;
	}

	public PersonalData save(final PersonalData pData) {
		Assert.isTrue(pData != null);
		return this.personalDataRepository.save(pData);
	}

	public Iterable<PersonalData> save(final Iterable<PersonalData> pDatas) {
		Assert.isTrue(pDatas != null);
		return this.personalDataRepository.save(pDatas);
	}

	public void delete(final PersonalData pData) {
		Assert.isTrue(pData != null);
		this.personalDataRepository.delete(pData);
	}

	public void delete(final Iterable<PersonalData> pData) {
		Assert.isTrue(pData != null);
		this.personalDataRepository.delete(pData);
	}

	public PersonalData findOne(final int id) {
		return this.personalDataRepository.findOne(id);
	}

	public Collection<PersonalData> findAll() {
		return this.personalDataRepository.findAll();
	}

	public PersonalDataForm createForm() {
		final PersonalDataForm pdForm = new PersonalDataForm();

		pdForm.setFullName("");
		pdForm.setGitHubProfile("");
		pdForm.setLinkedInProfile("");
		pdForm.setPhoneNumber("");
		pdForm.setStatement("");

		return pdForm;
	}

	public PersonalData reconstructForm(final PersonalDataForm pdForm, final BindingResult bindingResult) {
		PersonalData result;

		if (pdForm.getId() == 0)
			result = this.create();
		else
			result = this.personalDataRepository.findOne(pdForm.getId());

		result.setFullName(pdForm.getFullName());
		result.setGitHubProfile(pdForm.getGitHubProfile());
		result.setLinkedInProfile(pdForm.getLinkedInProfile());
		result.setPhoneNumber(pdForm.getPhoneNumber());
		result.setStatement(pdForm.getStatement());

		this.validator.validate(result, bindingResult);
		//this.personalDataRepository.flush();

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}

	public PersonalDataForm deconstruct(final PersonalData pData) {
		final PersonalDataForm pdForm = this.createForm();

		pdForm.setFullName(pData.getFullName());
		pdForm.setGitHubProfile(pData.getGitHubProfile());
		pdForm.setLinkedInProfile(pData.getLinkedInProfile());
		pdForm.setId(pData.getId());
		pdForm.setPhoneNumber(pData.getPhoneNumber());
		pdForm.setStatement(pData.getStatement());

		return pdForm;
	}

}
