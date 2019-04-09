
package services;

import java.util.ArrayList;

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


	public MiscellaneousDataService() {
		super();
	}

	public MiscellaneousData create() {
		final MiscellaneousData mData = new MiscellaneousData();

		mData.setAttachments(new ArrayList<String>());
		mData.setFreeText("");

		return mData;
	}

	public Iterable<MiscellaneousData> save(final Iterable<MiscellaneousData> datas) {
		Assert.isTrue(datas != null);
		return this.miscellaneousDataRepository.save(datas);
	}

	public MiscellaneousDataForm createForm() {
		final MiscellaneousDataForm mDataForm = new MiscellaneousDataForm();

		mDataForm.setAttachments(new ArrayList<String>());
		mDataForm.setFreeText("");

		return mDataForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Additional methods

	public int findOwner(final int miscellaneousDataId) {
		return this.miscellaneousDataRepository.findOwner(miscellaneousDataId);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public MiscellaneousData reconstruct(final MiscellaneousDataForm miscellaneousDataForm, final BindingResult bindingResult) {
		final MiscellaneousData result;

		if (miscellaneousDataForm.getId() == 0)
			result = this.create();
		else
			result = this.miscellaneousDataRepository.findOne(miscellaneousDataForm.getId());

		result.setAttachments(miscellaneousDataForm.getAttachments());
		result.setFreeText(miscellaneousDataForm.getFreeText());

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
