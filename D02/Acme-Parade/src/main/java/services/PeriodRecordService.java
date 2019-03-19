
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.PeriodRecordRepository;
import domain.PeriodRecord;

public class PeriodRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private PeriodRecordRepository	periodRecordRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

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

}
