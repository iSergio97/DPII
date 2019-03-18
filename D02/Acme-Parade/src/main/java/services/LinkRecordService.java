
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.LinkRecordRepository;
import domain.LinkRecord;

public class LinkRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private LinkRecordRepository	linkRecordRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public LinkRecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public LinkRecord create() {
		final LinkRecord result = new LinkRecord();

		result.setTitle("");
		result.setDescription("");

		result.setBrotherhood(null);
		result.setHistory(null);

		return result;
	}

	public LinkRecord save(final LinkRecord record) {
		Assert.isTrue(record != null);
		return this.linkRecordRepository.save(record);
	}

	public Iterable<LinkRecord> save(final Iterable<LinkRecord> records) {
		Assert.isTrue(records != null);
		return this.linkRecordRepository.save(records);
	}

	public void delete(final LinkRecord record) {
		Assert.isTrue(record != null);
		this.linkRecordRepository.delete(record);
	}

	public void delete(final Iterable<LinkRecord> records) {
		Assert.isTrue(records != null);
		this.linkRecordRepository.delete(records);
	}

	public LinkRecord findOne(final int id) {
		return this.linkRecordRepository.findOne(id);
	}

	public List<LinkRecord> findAll() {
		return this.linkRecordRepository.findAll();
	}

}
