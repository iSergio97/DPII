
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CurriculumService			curriculumService;


	// Constructors -----------------------------------------------------------

	public EndorserRecordService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public EndorserRecord create() {
		EndorserRecord endorserRecord = new EndorserRecord();
		endorserRecord.setEndorserFullName("");
		endorserRecord.setEndorserEmail("");
		endorserRecord.setEndorserPhoneNumber("");
		endorserRecord.setEndorserLinkedIn("");

		return endorserRecord;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.isTrue(endorserRecord != null);
		return this.endorserRecordRepository.save(endorserRecord);
	}

	public Iterable<EndorserRecord> save(final Iterable<EndorserRecord> endorserRecords) {
		Assert.isTrue(endorserRecords != null);
		return this.endorserRecordRepository.save(endorserRecords);
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.isTrue(endorserRecord != null);
		this.endorserRecordRepository.delete(endorserRecord);
	}

	public void delete(final Iterable<EndorserRecord> endorserRecords) {
		Assert.isTrue(endorserRecords != null);
		this.endorserRecordRepository.delete(endorserRecords);
	}

	public EndorserRecord findById(final int id) {
		return this.endorserRecordRepository.findOne(id);
	}

	public List<EndorserRecord> findAll() {
		return this.endorserRecordRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------

	public List<HandyWorker> getHandyWorkersWithEndorserRecordsWithWord(String word) {
		return this.endorserRecordRepository.getHandyWorkersWithEndorserRecordsWithWord(word);
	}

}
