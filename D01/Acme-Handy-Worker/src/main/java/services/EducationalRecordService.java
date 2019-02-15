
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.*;

import repositories.EducationalRecordRepository;
import domain.EducationalRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EducationalRecordRepository	educationalRecordRepository;

	// Supporting services ----------------------------------------------------


	// Constructors -----------------------------------------------------------

	public EducationalRecordService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public EducationalRecord create() {
		EducationalRecord educationalRecord = new EducationalRecord();
		educationalRecord.setDiplomaTitle("");
		educationalRecord.setStartingTime(new Date());
		educationalRecord.setEndingTime(new Date());
		educationalRecord.setInstitution("");
		educationalRecord.setAttachment("");
		educationalRecord.setComments("");
		return educationalRecord;
	}

	public EducationalRecord save(final EducationalRecord educationalRecord) {
		Assert.isTrue(educationalRecord != null);
		return this.educationalRecordRepository.save(educationalRecord);
	}

	public Iterable<EducationalRecord> save(final Iterable<EducationalRecord> educationalRecords) {
		Assert.isTrue(educationalRecords != null);
		return this.educationalRecordRepository.save(educationalRecords);
	}

	public void delete(final EducationalRecord educationalRecord) {
		Assert.isTrue(educationalRecord != null);
		this.educationalRecordRepository.delete(educationalRecord);
	}

	public void delete(final Iterable<EducationalRecord> educationalRecords) {
		Assert.isTrue(educationalRecords != null);
		this.educationalRecordRepository.delete(educationalRecords);
	}

	public EducationalRecord findById(final int id) {
		return this.educationalRecordRepository.findOne(id);
	}

	public List<EducationalRecord> findAll() {
		return this.educationalRecordRepository.findAll();
	}

	// Other methods ----------------------------------------------------------

	public List<HandyWorker> getHandyWorkersWithEducationalRecordsWithWord(String word) {
		return this.educationalRecordRepository.getHandyWorkersWithEducationalRecordsWithWord(word);
	}

}
