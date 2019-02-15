
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ProfessionalRecordService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public ProfessionalRecord create() {
		final ProfessionalRecord professionalRecord = new ProfessionalRecord();
		professionalRecord.setCompanyName("");
		professionalRecord.setStartingTime(new Date());
		professionalRecord.setEndingTime(new Date());
		professionalRecord.setRole("");
		professionalRecord.setAttachment("");
		professionalRecord.setComments("");
		return professionalRecord;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.isTrue(professionalRecord != null);
		return this.professionalRecordRepository.save(professionalRecord);
	}

	public Iterable<ProfessionalRecord> save(final Iterable<ProfessionalRecord> professionalRecords) {
		Assert.isTrue(professionalRecords != null);
		return this.professionalRecordRepository.save(professionalRecords);
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.isTrue(professionalRecord != null);
		this.professionalRecordRepository.delete(professionalRecord);
	}

	public void delete(final Iterable<ProfessionalRecord> professionalRecords) {
		Assert.isTrue(professionalRecords != null);
		this.professionalRecordRepository.delete(professionalRecords);
	}

	public ProfessionalRecord findById(final int id) {
		return this.professionalRecordRepository.findOne(id);
	}

	public List<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	// Other methods ----------------------------------------------------------

	public List<HandyWorker> getHandyWorkersWithProfessionalRecordsWithWord(final String word) {
		return this.professionalRecordRepository.getHandyWorkersWithProfessionalRecordsWithWord(word);
	}

}
