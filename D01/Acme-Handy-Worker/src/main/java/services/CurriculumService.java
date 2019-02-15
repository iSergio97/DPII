
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CurriculumRepository;
import security.LoginService;
import security.UserAccount;
import utilities.RandomTickerGenerator;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Constructors -----------------------------------------------------------

	public CurriculumService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Curriculum create() {
		final Curriculum curriculum = new Curriculum();
		curriculum.setMiscellaneousRecord(new ArrayList<MiscellaneousRecord>());
		final UserAccount login = LoginService.getPrincipal();
		final HandyWorker handyWorker = this.handyWorkerService.findById(login.getId());
		curriculum.setHandyWorker(handyWorker);
		curriculum.setEndorserRecord(new ArrayList<EndorserRecord>());
		curriculum.setPersonalRecord(this.personalRecordService.create());
		curriculum.setProfessionalRecord(new ArrayList<ProfessionalRecord>());
		curriculum.setTicker(RandomTickerGenerator.generateTicker());

		return curriculum;
	}

	public Curriculum save(final Curriculum curriculum) {
		assert curriculum != null;
		return this.curriculumRepository.save(curriculum);
	}

	public Iterable<Curriculum> save(final Iterable<Curriculum> curriculum) {
		assert curriculum != null;
		return this.curriculumRepository.save(curriculum);
	}

	public void delete(final Curriculum actor) {
		assert actor != null;
		this.curriculumRepository.delete(actor);
	}

	public void delete(final Iterable<Curriculum> curricula) {
		assert curricula != null;
		this.curriculumRepository.delete(curricula);
	}

	public Curriculum findById(final int id) {
		return this.curriculumRepository.findOne(id);
	}

	public List<Curriculum> findAll() {
		return this.curriculumRepository.findAll();
	}
}
