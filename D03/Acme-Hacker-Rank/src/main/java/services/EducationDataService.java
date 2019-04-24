
package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.EducationDataRepository;
import domain.EducationData;
import forms.EducationDataForm;

@Service
@Transactional
public class EducationDataService extends AbstractService<EducationDataRepository, EducationData> {

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

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
			result = this.repository.findOne(edForm.getId());

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

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public EducationData copy(final EducationData educationData) {
		final EducationData copy = this.create();
		copy.setDegree(educationData.getDegree());
		copy.setInstitution(educationData.getInstitution());
		copy.setMark(educationData.getMark());
		copy.setStartDate(educationData.getStartDate());
		copy.setEndDate(educationData.getEndDate());
		return this.save(copy);
	}

	public int findOwner(final int educationDataId) {
		return this.repository.findOwner(educationDataId);
	}

	public int findCurriculum(final int educationDataId) {
		return this.repository.findCurriculum(educationDataId);
	}

}
