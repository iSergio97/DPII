
package services;

import java.util.ArrayList;
import java.util.Collection;

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
public class MiscellaneousDataService {

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

	public MiscellaneousData save(final MiscellaneousData data) {
		Assert.isTrue(data != null);
		return this.miscellaneousDataRepository.save(data);
	}

	public Iterable<MiscellaneousData> save(final Iterable<MiscellaneousData> datas) {
		Assert.isTrue(datas != null);
		return this.miscellaneousDataRepository.save(datas);
	}

	public void delete(final MiscellaneousData data) {
		Assert.isTrue(data != null);
		this.miscellaneousDataRepository.delete(data);
	}

	public void delete(final Iterable<MiscellaneousData> data) {
		Assert.isTrue(data != null);
		this.miscellaneousDataRepository.delete(data);
	}

	public MiscellaneousData findOne(final int id) {
		return this.miscellaneousDataRepository.findOne(id);
	}

	public Collection<MiscellaneousData> findAll() {
		return this.miscellaneousDataRepository.findAll();
	}

	public MiscellaneousDataForm createForm() {
		final MiscellaneousDataForm mDataForm = new MiscellaneousDataForm();

		mDataForm.setAttachments(new ArrayList<String>());
		mDataForm.setFreeText("");

		return mDataForm;
	}

	public MiscellaneousData reconscructForm(final MiscellaneousDataForm mDataForm, final BindingResult bindingResult) {
		final MiscellaneousData result;

		if (mDataForm.getId() == 0)
			result = this.create();
		else
			result = this.miscellaneousDataRepository.findOne(mDataForm.getId());

		result.setAttachments(mDataForm.getAttachments());
		result.setFreeText(mDataForm.getFreeText());

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
