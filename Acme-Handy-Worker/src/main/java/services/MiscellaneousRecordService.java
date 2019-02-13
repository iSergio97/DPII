
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services ----------------------------------------------------


	// Constructors -----------------------------------------------------------

	public MiscellaneousRecordService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public MiscellaneousRecord create() {
		MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setTitle("");
		miscellaneousRecord.setAttachment("");
		miscellaneousRecord.setComments("");
		return miscellaneousRecord;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.isTrue(miscellaneousRecord != null);
		return this.miscellaneousRecordRepository.save(miscellaneousRecord);
	}

	public Iterable<MiscellaneousRecord> save(final Iterable<MiscellaneousRecord> miscellaneousRecords) {
		Assert.isTrue(miscellaneousRecords != null);
		return this.miscellaneousRecordRepository.save(miscellaneousRecords);
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.isTrue(miscellaneousRecord != null);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	public void delete(final Iterable<MiscellaneousRecord> miscellaneousRecords) {
		Assert.isTrue(miscellaneousRecords != null);
		this.miscellaneousRecordRepository.delete(miscellaneousRecords);
	}

	public MiscellaneousRecord findById(final int id) {
		return this.miscellaneousRecordRepository.findOne(id);
	}

	public List<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	// Other methods ----------------------------------------------------------

	public List<HandyWorker> getHandyWorkersWithMiscellaneousRecordsWithWord(String word) {
		return this.miscellaneousRecordRepository.getHandyWorkersWithMiscellaneousRecordsWithWord(word);
	}

}
