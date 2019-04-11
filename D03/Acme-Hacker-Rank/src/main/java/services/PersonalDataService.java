
package services;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Curriculum;
import domain.PersonalData;
import forms.PersonalDataForm;
import repositories.PersonalDataRepository;

@Service
@Transactional
public class PersonalDataService extends AbstractService<PersonalData> {

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private CurriculumService		curriculumService;


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

	public Iterable<PersonalData> save(final Iterable<PersonalData> pDatas) {
		Assert.isTrue(pDatas != null);
		return this.personalDataRepository.save(pDatas);
	}

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
		final Curriculum cr = this.curriculumService.findCurriculumByPDId(pData.getId());

		pdForm.setCurriculumName(cr.getName());
		pdForm.setFullName(pData.getFullName());
		pdForm.setGitHubProfile(pData.getGitHubProfile());
		pdForm.setLinkedInProfile(pData.getLinkedInProfile());
		pdForm.setId(pData.getId());
		pdForm.setPhoneNumber(pData.getPhoneNumber());
		pdForm.setStatement(pData.getStatement());
		pdForm.setId(pData.getId());

		return pdForm;
	}

	public Integer findHackerByPDId(final int id) {
		return this.personalDataRepository.findHackerByPDID(id);
	}

}
