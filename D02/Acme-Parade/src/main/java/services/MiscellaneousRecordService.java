
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

public class MiscellaneousRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MiscellaneousRecordRepository	miscRecordRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

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

}
