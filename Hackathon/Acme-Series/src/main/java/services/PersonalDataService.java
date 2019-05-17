
package services;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalDataRepository;
import domain.Curriculum;
import domain.PersonalData;
import forms.PersonalDataForm;

@Service
@Transactional
public class PersonalDataService extends AbstractService<PersonalDataRepository, PersonalData> {

	@Autowired
	private Validator			validator;

	@Autowired
	private CurriculumService	curriculumService;


	public PersonalDataForm createForm() {
		final PersonalDataForm pdForm = new PersonalDataForm();

		pdForm.setCurriculumName("");
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
			result = this.repository.findOne(pdForm.getId());

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
		final Curriculum cr = this.curriculumService.findCurriculumByPDId(pData.getId());

		pdForm.setCurriculumName(cr.getName());
		pdForm.setFullName(pData.getFullName());
		pdForm.setGitHubProfile(pData.getGitHubProfile());
		pdForm.setLinkedInProfile(pData.getLinkedInProfile());
		pdForm.setId(pData.getId());
		pdForm.setPhoneNumber(pData.getPhoneNumber());
		pdForm.setStatement(pData.getStatement());

		return pdForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public PersonalData copy(final PersonalData personalData) {
		final PersonalData copy = this.create();
		copy.setFullName(personalData.getFullName());
		copy.setStatement(personalData.getStatement());
		copy.setPhoneNumber(personalData.getPhoneNumber());
		copy.setGitHubProfile(personalData.getGitHubProfile());
		copy.setLinkedInProfile(personalData.getLinkedInProfile());
		return this.save(copy);
	}

}
