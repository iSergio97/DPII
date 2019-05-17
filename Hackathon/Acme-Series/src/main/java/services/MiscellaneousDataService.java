
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousDataRepository;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

@Service
@Transactional
public class MiscellaneousDataService extends AbstractService<MiscellaneousDataRepository, MiscellaneousData> {

	@Autowired
	private Validator	validator;


	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public MiscellaneousDataForm createForm(final MiscellaneousData miscellaneousData) {
		final MiscellaneousDataForm miscellaneousDataForm = new MiscellaneousDataForm();
		miscellaneousDataForm.setAttachments(miscellaneousData.getAttachments());
		miscellaneousDataForm.setFreeText(miscellaneousData.getFreeText());
		miscellaneousDataForm.setId(miscellaneousData.getId());
		return miscellaneousDataForm;
	}

	public MiscellaneousDataForm createForm() {
		final MiscellaneousDataForm miscellaneousDataForm = new MiscellaneousDataForm();

		miscellaneousDataForm.setAttachments("");
		miscellaneousDataForm.setFreeText("");

		return miscellaneousDataForm;
	}

	public MiscellaneousData reconstruct(final MiscellaneousDataForm miscellaneousDataForm, final BindingResult binding) {
		final MiscellaneousData result;

		if (miscellaneousDataForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(miscellaneousDataForm.getId());

		result.setAttachments(miscellaneousDataForm.getAttachments());
		result.setFreeText(miscellaneousDataForm.getFreeText());

		this.validator.validate(result, binding);

		return result;
	}

	public MiscellaneousDataForm deconstruct(final MiscellaneousData mData) {
		final MiscellaneousDataForm form = this.createForm();

		form.setId(mData.getId());
		form.setAttachments(mData.getAttachments());
		form.setFreeText(mData.getFreeText());

		return form;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public MiscellaneousData copy(final MiscellaneousData miscellaneousData) {
		final MiscellaneousData copy = this.create();
		copy.setFreeText(miscellaneousData.getFreeText());
		copy.setAttachments(miscellaneousData.getAttachments());
		return this.save(copy);
	}

	public int findOwner(final int miscellaneousDataId) {
		return this.repository.findOwner(miscellaneousDataId);
	}

	public int findCurriculum(final int miscellaneousDataId) {
		return this.repository.findCurriculum(miscellaneousDataId);
	}

}
