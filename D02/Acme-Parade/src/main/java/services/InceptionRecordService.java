
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InceptionRecordRepository;
import domain.InceptionRecord;

@Service
@Transactional
public class InceptionRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public InceptionRecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public InceptionRecord create() {
		final InceptionRecord result = new InceptionRecord();
		result.setTitle("");
		result.setDescription("");
		result.setPhotos(new ArrayList<String>());

		result.setHistory(null);

		return result;
	}

	public InceptionRecord save(final InceptionRecord record) {
		Assert.isTrue(record != null);
		return this.inceptionRecordRepository.save(record);
	}

	public Iterable<InceptionRecord> save(final Iterable<InceptionRecord> records) {
		Assert.isTrue(records != null);
		return this.inceptionRecordRepository.save(records);
	}

	public void delete(final InceptionRecord record) {
		Assert.isTrue(record != null);
		this.inceptionRecordRepository.delete(record);
	}

	public void delete(final Iterable<InceptionRecord> records) {
		Assert.isTrue(records != null);
		this.inceptionRecordRepository.delete(records);
	}

	public InceptionRecord findOne(final int id) {
		return this.inceptionRecordRepository.findOne(id);
	}

	public List<InceptionRecord> findAll() {
		return this.inceptionRecordRepository.findAll();
	}

}
