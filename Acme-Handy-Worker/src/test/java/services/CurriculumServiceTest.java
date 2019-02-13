
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationalRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private EducationalRecordService	educationalRecordService;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	@Test
	public void createTest() {
		Curriculum c;

		super.authenticate("handyWorker1");
		c = this.curriculumService.create();

		Assert.isTrue(c.getTicker() != null);
		Assert.isTrue(c.getHandyWorker() != null);

		Assert.isTrue(c.getMiscellaneousRecord().isEmpty());
		Assert.isTrue(c.getProfessionalRecord().isEmpty());
		Assert.isTrue(c.getEducationalRecord().isEmpty());
		Assert.isTrue(c.getEndorserRecord().isEmpty());
		Assert.isTrue(c.getPersonalRecord().getCurriculum().equals(c));

		super.unauthenticate();
	}

	@Test
	public void saveTest() {
		Curriculum c, saved;
		final HandyWorker h;
		Collection<Curriculum> curriculums;

		super.authenticate("handyWorker1");
		c = this.curriculumService.create();

		final List<EducationalRecord> edR = new ArrayList<EducationalRecord>();
		final List<MiscellaneousRecord> mR = new ArrayList<MiscellaneousRecord>();
		final List<ProfessionalRecord> pfR = new ArrayList<ProfessionalRecord>();
		final List<EndorserRecord> eR = new ArrayList<EndorserRecord>();

		edR.add(this.educationalRecordService.findById(2579));
		mR.add(this.miscellaneousRecordService.findById(2581));
		pfR.add(this.professionalRecordService.findById(2582));
		eR.add(this.endorserRecordService.findById(2580));

		c.setEducationalRecord(edR);
		c.setMiscellaneousRecord(mR);
		c.setEndorserRecord(eR);
		c.setProfessionalRecord(pfR);
		c.setPersonalRecord(this.personalRecordService.findById(2583));

		saved = this.curriculumService.save(c);
		curriculums = this.curriculumService.findAll();

		Assert.isTrue(curriculums.contains(saved));

		super.unauthenticate();
	}

}
