
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.EducationData;
import forms.EducationDataForm;
import repositories.EducationDataRepository;

@Service
@Transactional
public class EducationDataService extends AbstractService<EducationData> {

	@Autowired
	private EducationDataRepository educationDataRepository;


	public EducationDataService() {
		super();
	}

	public EducationData create() {
		final EducationData eData = new EducationData();

		eData.setDegree("");
		eData.setInstitution("");
		eData.setMark(0.);

		return eData;
	}

	public Iterable<EducationData> save(final Iterable<EducationData> pDatas) {
		Assert.isTrue(pDatas != null);
		return this.educationDataRepository.save(pDatas);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Additional methods

	public int findOwner(final int educationDataId) {
		return this.educationDataRepository.findOwner(educationDataId);
	}

	public int findCurriculum(final int educationDataId) {
		return this.educationDataRepository.findCurriculum(educationDataId);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public EducationDataForm createForm(final EducationData educationData) {
		final EducationDataForm educationDataForm = new EducationDataForm();
		educationDataForm.setDegree(educationData.getDegree());
		educationDataForm.setInstitution(educationData.getInstitution());
		educationDataForm.setMark(educationData.getMark());
		educationDataForm.setStartDate(educationData.getStartDate());
		educationDataForm.setEndDate(educationData.getEndDate());
		educationDataForm.setId(educationData.getId());
		return educationDataForm;
	}

	public EducationDataForm createForm() {
		final EducationDataForm edForm = new EducationDataForm();

		edForm.setDegree("");
		edForm.setInstitution("");
		edForm.setMark(0.);
		return edForm;
	}

	public EducationData reconstruct(final EducationDataForm edForm, final BindingResult bindingResult) {
		EducationData result;

		if (edForm.getId() == 0)
			result = this.create();
		else
			result = this.educationDataRepository.findOne(edForm.getId());

		result.setDegree(edForm.getDegree());
		result.setEndDate(edForm.getEndDate());
		result.setStartDate(edForm.getStartDate());
		result.setInstitution(edForm.getInstitution());
		result.setMark(edForm.getMark());

		return result;
	}

	public EducationDataForm deconstruct(final EducationData pData) {
		final EducationDataForm edForm = this.createForm();

		return edForm;
	}
}
