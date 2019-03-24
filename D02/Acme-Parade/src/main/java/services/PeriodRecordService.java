
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PeriodRecordRepository;
import domain.PeriodRecord;
import forms.PeriodRecordForm;

@Service
@Transactional
public class PeriodRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private PeriodRecordRepository	periodRecordRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private Validator				validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public PeriodRecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public PeriodRecord create() {
		final PeriodRecord result = new PeriodRecord();

		result.setTitle("");
		result.setDescription("");
		result.setStartYear(0);
		result.setEndYear(0);
		result.setPhotos(new ArrayList<String>());

		result.setHistory(null);

		return result;
	}

	public PeriodRecordForm createForm() {
		final PeriodRecordForm record = new PeriodRecordForm();

		record.setTitle("");
		record.setDescription("");
		record.setStartYear(0);
		record.setEndYear(0);
		record.setPhotos(new ArrayList<String>());
		record.setId(0);

		return record;
	}

	public PeriodRecord save(final PeriodRecord record) {
		Assert.isTrue(record != null);
		return this.periodRecordRepository.save(record);
	}

	public Iterable<PeriodRecord> save(final Iterable<PeriodRecord> records) {
		Assert.isTrue(records != null);
		return this.periodRecordRepository.save(records);
	}

	public void delete(final PeriodRecord record) {
		Assert.isTrue(record != null);
		this.periodRecordRepository.delete(record);
	}

	public void delete(final Iterable<PeriodRecord> records) {
		Assert.isTrue(records != null);
		this.periodRecordRepository.delete(records);
	}

	public PeriodRecord findOne(final int id) {
		return this.periodRecordRepository.findOne(id);
	}

	public List<PeriodRecord> findAll() {
		return this.periodRecordRepository.findAll();
	}

	//Ancillary Methods -----------------------------------------

	public Collection<PeriodRecord> getPeriodRecordsByHistory(final int historyId) {
		return this.periodRecordRepository.getPeriodRecordsByHistory(historyId);
	}

	public PeriodRecord reconstruct(final PeriodRecordForm record, final BindingResult binding) {
		PeriodRecord result;

		if (record.getId() == 0)
			result = this.create();
		else
			result = this.periodRecordRepository.findOne(record.getId());

		result.setTitle(record.getTitle());
		result.setDescription(record.getDescription());
		result.setStartYear(record.getStartYear());
		result.setEndYear(record.getEndYear());
		result.setPhotos(record.getPhotos());
		result.setHistory(this.brotherhoodService.findPrincipal().getHistory());

		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

}
