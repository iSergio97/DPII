
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;
import repositories.MiscellaneousDataRepository;

@Service
@Transactional
public class MiscellaneousDataService extends AbstractService<MiscellaneousData> {

	@Autowired
	private MiscellaneousDataRepository miscellaneousDataRepository;
	
	@Autowired
	private Validator validator;


	public MiscellaneousDataService() {
		super();
	}

	public MiscellaneousData create() {
		final MiscellaneousData miscellaneousData = new MiscellaneousData();

		miscellaneousData.setAttachments("");
		miscellaneousData.setFreeText("");

		return miscellaneousData;
	}

	public MiscellaneousDataForm createForm(final MiscellaneousData miscellaneousData) {
		final MiscellaneousDataForm miscellaneousDataForm = new MiscellaneousDataForm();
		miscellaneousDataForm.setAttachments(miscellaneousData.getAttachments());
		miscellaneousDataForm.setFreeText(miscellaneousData.getFreeText());
		miscellaneousDataForm.setId(miscellaneousData.getId());
		return miscellaneousDataForm;
	}

	public Iterable<MiscellaneousData> save(final Iterable<MiscellaneousData> datas) {
		Assert.isTrue(datas != null);
		return this.miscellaneousDataRepository.save(datas);
	}

	public MiscellaneousDataForm createForm() {
		final MiscellaneousDataForm miscellaneousDataForm = new MiscellaneousDataForm();

		miscellaneousDataForm.setAttachments("");
		miscellaneousDataForm.setFreeText("");

		return miscellaneousDataForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Additional methods

	public int findOwner(final int miscellaneousDataId) {
		return this.miscellaneousDataRepository.findOwner(miscellaneousDataId);
	}

	public int findCurriculum(final int miscellaneousDataId) {
		return this.miscellaneousDataRepository.findCurriculum(miscellaneousDataId);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public MiscellaneousData reconstruct(final MiscellaneousDataForm miscellaneousDataForm, final BindingResult binding) {
		final MiscellaneousData result;

		if (miscellaneousDataForm.getId() == 0)
			result = this.create();
		else
			result = this.miscellaneousDataRepository.findOne(miscellaneousDataForm.getId());

		result.setAttachments(miscellaneousDataForm.getAttachments());
		result.setFreeText(miscellaneousDataForm.getFreeText());

		this.validator.Validate(result, binding);
		
		return result;
	}

	public MiscellaneousDataForm deconstruct(final MiscellaneousData mData) {
		final MiscellaneousDataForm form = this.createForm();

		form.setId(mData.getId());
		form.setAttachments(mData.getAttachments());
		form.setFreeText(mData.getFreeText());

		return form;
	}

}
