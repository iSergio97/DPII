
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionDataRepository;
import domain.PositionData;
import forms.PositionDataForm;

@Service
@Transactional
public class PositionDataService extends AbstractService<PositionDataRepository, PositionData> {

	@Autowired
	private Validator	validator;


	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public PositionDataForm createForm(final PositionData positionData) {
		final PositionDataForm positionDataForm = new PositionDataForm();
		positionDataForm.setTitle(positionData.getTitle());
		positionDataForm.setDescription(positionData.getDescription());
		positionDataForm.setStartDate(positionData.getStartDate());
		positionDataForm.setEndDate(positionData.getEndDate());
		positionDataForm.setId(positionData.getId());
		return positionDataForm;
	}

	public PositionData reconstruct(final PositionDataForm positionDataForm, final BindingResult binding) {
		final PositionData result;

		if (positionDataForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(positionDataForm.getId());

		result.setTitle(positionDataForm.getTitle());
		result.setDescription(positionDataForm.getDescription());
		result.setStartDate(positionDataForm.getStartDate());
		result.setEndDate(positionDataForm.getEndDate());

		this.validator.validate(result, binding);

		return result;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public PositionData copy(final PositionData positionData) {
		final PositionData copy = this.create();
		copy.setTitle(positionData.getTitle());
		copy.setDescription(positionData.getDescription());
		copy.setStartDate(positionData.getStartDate());
		copy.setEndDate(positionData.getEndDate());
		return this.save(copy);
	}

	public int findOwner(final int positionDataId) {
		return this.repository.findOwner(positionDataId);
	}

	public int findCurriculum(final int positionDataId) {
		return this.repository.findCurriculum(positionDataId);
	}

}
