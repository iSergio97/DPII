
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.PositionData;
import forms.PositionDataForm;
import repositories.PositionDataRepository;

@Service
@Transactional
public class PositionDataService extends AbstractService<PositionData> {

	@Autowired
	private PositionDataRepository	positionDataRepository;

	@Autowired
	private Validator				validator;


	public PositionDataService() {
		super();
	}

	public PositionData create() {
		final PositionData pd = new PositionData();
		pd.setDescription("");
		pd.setTitle("");

		return pd;
	}

	public Iterable<PositionData> save(final Iterable<PositionData> pd) {
		Assert.isTrue(pd != null);
		return this.positionDataRepository.save(pd);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Additional methods

	public int findOwner(final int positionDataId) {
		return this.positionDataRepository.findOwner(positionDataId);
	}

	public int findCurriculum(final int positionDataId) {
		return this.positionDataRepository.findCurriculum(positionDataId);
	}

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
			result = this.positionDataRepository.findOne(positionDataForm.getId());

		result.setTitle(positionDataForm.getTitle());
		result.setDescription(positionDataForm.getDescription());
		result.setStartDate(positionDataForm.getStartDate());
		result.setEndDate(positionDataForm.getEndDate());

		this.validator.validate(result, binding);

		return result;
	}
}
