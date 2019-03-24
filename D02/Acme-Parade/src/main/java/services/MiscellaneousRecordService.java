
package services;

import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;
import forms.MiscellaneousRecordForm;

@Service
@Transactional
public class MiscellaneousRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MiscellaneousRecordRepository	miscRecordRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private Validator						validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public MiscellaneousRecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public MiscellaneousRecord create() {
		final MiscellaneousRecord result = new MiscellaneousRecord();

		result.setTitle("");
		result.setDescription("");

		result.setHistory(null);

		return result;
	}

	public MiscellaneousRecordForm createForm() {
		final MiscellaneousRecordForm record = new MiscellaneousRecordForm();

		record.setTitle("");
		record.setDescription("");
		record.setId(0);

		return record;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord record) {
		Assert.isTrue(record != null);
		return this.miscRecordRepository.save(record);
	}

	public Iterable<MiscellaneousRecord> save(final Iterable<MiscellaneousRecord> records) {
		Assert.isTrue(records != null);
		return this.miscRecordRepository.save(records);
	}

	public void delete(final MiscellaneousRecord record) {
		Assert.isTrue(record != null);
		this.miscRecordRepository.delete(record);
	}

	public void delete(final Iterable<MiscellaneousRecord> records) {
		Assert.isTrue(records != null);
		this.miscRecordRepository.delete(records);
	}

	public MiscellaneousRecord findOne(final int id) {
		return this.miscRecordRepository.findOne(id);
	}

	public List<MiscellaneousRecord> findAll() {
		return this.miscRecordRepository.findAll();
	}

	public Collection<MiscellaneousRecord> getMiscellaneousRecordsByHistory(final int historyId) {
		return this.miscRecordRepository.getMiscellaneousRecordsByHistory(historyId);
	}

	public MiscellaneousRecord reconstruct(final MiscellaneousRecordForm record, final BindingResult binding) {
		MiscellaneousRecord result;

		if (record.getId() == 0)
			result = this.create();
		else
			result = this.miscRecordRepository.findOne(record.getId());

		result.setTitle(record.getTitle());
		result.setDescription(record.getDescription());
		result.setHistory(this.brotherhoodService.findPrincipal().getHistory());

		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

}
