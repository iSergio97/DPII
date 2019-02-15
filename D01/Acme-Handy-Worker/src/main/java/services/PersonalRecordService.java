
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.HandyWorker;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services ----------------------------------------------------


	// Constructors -----------------------------------------------------------

	public PersonalRecordService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public PersonalRecord create() {
		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setFullName("");
		personalRecord.setPhoto("");
		personalRecord.setEmail("");
		personalRecord.setPhoneNumber("");
		personalRecord.setLinkedIn("");
		return personalRecord;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.isTrue(personalRecord != null);
		return this.personalRecordRepository.save(personalRecord);
	}

	public Iterable<PersonalRecord> save(final Iterable<PersonalRecord> personalRecords) {
		Assert.isTrue(personalRecords != null);
		return this.personalRecordRepository.save(personalRecords);
	}

	public void delete(final PersonalRecord personalRecord) {
		Assert.isTrue(personalRecord != null);
		this.personalRecordRepository.delete(personalRecord);
	}

	public void delete(final Iterable<PersonalRecord> personalRecords) {
		Assert.isTrue(personalRecords != null);
		this.personalRecordRepository.delete(personalRecords);
	}

	public PersonalRecord findById(final int id) {
		return this.personalRecordRepository.findOne(id);
	}

	public List<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	// Other methods ----------------------------------------------------------

	public List<HandyWorker> getHandyWorkersWithPersonalRecordsWithWord(String word) {
		return this.personalRecordRepository.getHandyWorkersWithPersonalRecordsWithWord(word);
	}

}
