
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalRecordRepository;
import domain.LegalRecord;

@Service
@Transactional
public class LegalRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private LegalRecordRepository	legalRecordRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public LegalRecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public LegalRecord create() {
		final LegalRecord result = new LegalRecord();

		result.setTitle("");
		result.setDescription("");
		result.setLegalName("");
		result.setApplicableLaws("");
		result.setVAT(0.0);
		result.setHistory(null);

		return result;
	}

	public LegalRecord save(final LegalRecord record) {
		Assert.isTrue(record != null);
		return this.legalRecordRepository.save(record);
	}

	public Iterable<LegalRecord> save(final Iterable<LegalRecord> records) {
		Assert.isTrue(records != null);
		return this.legalRecordRepository.save(records);
	}

	public void delete(final LegalRecord record) {
		Assert.isTrue(record != null);
		this.legalRecordRepository.delete(record);
	}

	public void delete(final Iterable<LegalRecord> records) {
		Assert.isTrue(records != null);
		this.legalRecordRepository.delete(records);
	}

	public LegalRecord findOne(final int id) {
		return this.legalRecordRepository.findOne(id);
	}

	public List<LegalRecord> findAll() {
		return this.legalRecordRepository.findAll();
	}

}
