
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecordRepository;
import domain.Record;

@Service
@Transactional
public class RecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private RecordRepository	recordRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public RecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Record save(final Record record) {
		Assert.isTrue(record != null);
		return this.recordRepository.save(record);
	}

	public Iterable<Record> save(final Iterable<Record> records) {
		Assert.isTrue(records != null);
		return this.recordRepository.save(records);
	}

	public void delete(final Record record) {
		Assert.isTrue(record != null);
		this.recordRepository.delete(record);
	}

	public void delete(final Iterable<Record> records) {
		Assert.isTrue(records != null);
		this.recordRepository.delete(records);
	}

	public Record findOne(final int id) {
		return this.recordRepository.findOne(id);
	}

	public List<Record> findAll() {
		return this.recordRepository.findAll();
	}

}
