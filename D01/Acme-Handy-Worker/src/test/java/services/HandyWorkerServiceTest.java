
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Application;
import domain.Curriculum;
import domain.EducationalRecord;
import domain.Endorsement;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.Message;
import domain.MessageBox;
import domain.MiscellaneousRecord;
import domain.Note;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Section;
import domain.SocialProfile;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private SocialProfileService		socialProfileService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private FinderService				finderService;

	@Autowired
	private MessageBoxService			messageBoxService;

	@Autowired
	private MessageService				messageService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private EducationalRecordService	educationalRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private TutorialService				tutorialService;

	@Autowired
	private SectionService				sectionService;


	@Test
	public void createTest() {
		HandyWorker handyWorker;
		super.authenticate("handyWorker1");
		handyWorker = this.handyWorkerService.create();
		handyWorker.setName("Sergio");
		handyWorker.setMiddleName("Prueba de middle name");
		handyWorker.setSurname("Prueba de surname");
		handyWorker.setPhoto("https://gyazo.com/bc9a07f806b4b8222308e38e48c6ff04");
		handyWorker.setEmail("sergiotb15@gmail.com");
		handyWorker.setPhoneNumber("123456789");
		handyWorker.setAddress("Calle de prueba");
		handyWorker.setMessageBoxes(this.messageBoxService.createSystemBoxes());
		final MessageBox cajaNueva = this.messageBoxService.create();
		cajaNueva.setName("Caja de prueba");
		final MessageBox cajaSaved = this.messageBoxService.save(cajaNueva);
		handyWorker.getMessageBoxes().add(cajaSaved);
		SocialProfile sp;
		sp = this.socialProfileService.create();
		sp.setActor(handyWorker);
		sp.setLink("https://www.twitter.com/PruebaDeLink");
		sp.setNickName("Sergio");
		sp.setSocialNetworkName("Social network");
		List<SocialProfile> ls;
		ls = new ArrayList<>();
		ls.add(sp);
		handyWorker.setSocialProfiles(ls);
		handyWorker.setEndorsedBy(new ArrayList<Endorsement>());
		handyWorker.setEndorses(new ArrayList<Endorsement>());
		final List<Message> lsMessage = new ArrayList<>();
		handyWorker.setMessagesSent(lsMessage);
		handyWorker.setNotes(new ArrayList<Note>());
		handyWorker.setApplications(new ArrayList<Application>());
		final Curriculum c = this.curriculumService.create();

		// Creación personal record
		final PersonalRecord pRecord = this.personalRecordService.create();
		pRecord.setEmail("email@gmail.com");
		pRecord.setFullName("FullName");
		pRecord.setLinkedIn("www.linkedIn.com/PruebaDeLinkedIn");
		pRecord.setPhoneNumber("999999999");
		pRecord.setPhoto("https://gyazo.com/bc9a07f806b4b8222308e38e48c6ff04");
		final PersonalRecord pRecordSaved = this.personalRecordService.save(pRecord);
		c.setPersonalRecord(pRecordSaved);

		// Creación MiscellaneousRecord
		final MiscellaneousRecord mRecord = this.miscellaneousRecordService.create();
		mRecord.setAttachment("http://www.google.com");
		mRecord.setComments("Prueba de comments");
		mRecord.setTitle("Prueba de title");
		final MiscellaneousRecord mRecordSaved = this.miscellaneousRecordService.save(mRecord);
		final List<MiscellaneousRecord> lsMR = new ArrayList<MiscellaneousRecord>();
		lsMR.add(mRecordSaved);
		c.setMiscellaneousRecord(lsMR);

		//Creación EndorserRecord
		final EndorserRecord eRecord = this.endorserRecordService.create();
		eRecord.setEndorserLinkedIn("www.google.com");
		eRecord.setEndorserPhoneNumber("123456789");
		eRecord.setEndorserEmail("email@gmail.com");
		eRecord.setEndorserFullName("Prueba de nombre");
		eRecord.setComments("Prueba de comments");
		final EndorserRecord eRecordSaved = this.endorserRecordService.save(eRecord);
		final List<EndorserRecord> lsRecord = new ArrayList<>();
		lsRecord.add(eRecordSaved);
		c.setEndorserRecord(lsRecord);

		//Creación EducationalRecord
		final EducationalRecord edRecord = this.educationalRecordService.create();
		edRecord.setAttachment("https://www.google.com");
		edRecord.setComments("Comments");
		edRecord.setDiplomaTitle("Diploma title");
		edRecord.setEndingTime(new Date());
		final Date date = new Date();
		date.setYear(2012);
		System.out.println("Prueba de fecha: " + date);
		edRecord.setEndingTime(date);
		edRecord.setInstitution("Prueba de institución");
		final EducationalRecord edRecordSaved = this.educationalRecordService.save(edRecord);
		final List<EducationalRecord> lsEdRecord = new ArrayList<>();
		lsEdRecord.add(edRecordSaved);
		c.setEducationalRecord(lsEdRecord);

		//Creación profesional record
		final ProfessionalRecord professionalRecord = this.professionalRecordService.create();
		professionalRecord.setAttachment("http://www.google.com");
		professionalRecord.setComments("Comments");
		professionalRecord.setCompanyName("Prueba de compañía");
		professionalRecord.setEndingTime(new Date());
		professionalRecord.setRole("Manager");
		professionalRecord.setStartingTime(date);
		final List<ProfessionalRecord> lsProfesionalRecord = new ArrayList<>();
		final ProfessionalRecord professionalRecordSaved = this.professionalRecordService.save(professionalRecord);
		lsProfesionalRecord.add(professionalRecordSaved);
		c.setProfessionalRecord(lsProfesionalRecord);

		//Creación tutorial
		final Tutorial tutorial = this.tutorialService.create();
		tutorial.setTitle("Title");
		tutorial.setSummary("Summary");
		tutorial.setLastUpdated(new Date());
		tutorial.setPictures(new ArrayList<String>());
		//Creación de section para el tutorial;
		final Section section = this.sectionService.create();
		section.setPictures(new ArrayList<String>());
		section.setText("Text");
		section.setTitle("Title");
		final List<Section> lsSection = new ArrayList<>();
		final Section sectionSaved = this.sectionService.save(section);
		lsSection.add(sectionSaved);
		tutorial.setSections(lsSection);
		final Tutorial tutorialSaved = this.tutorialService.save(tutorial);
		final List<Tutorial> lsTutorial = new ArrayList<>();
		lsTutorial.add(tutorialSaved);
		handyWorker.setTutorials(lsTutorial);
		handyWorker.setMake("Make");
		handyWorker.setFinder(null);
		HandyWorker handyWorkerSaved;
		handyWorkerSaved = this.handyWorkerService.save(handyWorker);
		c.setHandyWorker(handyWorkerSaved);
		handyWorkerSaved.setCurriculum(c);

		Assert.isTrue(handyWorkerSaved.getCurriculum() != null);
		Assert.isTrue(handyWorkerSaved.getMessagesSent().size() == 1);
		Assert.isTrue(handyWorkerSaved.getTutorials().size() != 0);
		//Assert.isTrue(this.handyWorkerService.findAll().contains(handyWorkerSaved));
		Assert.isNull(handyWorker.getFinder());
		//Assert.isNull(handyWorker.getCurriculum());
		this.handyWorkerService.delete(handyWorkerSaved);
		//System.out.println(this.handyWorkerService.findAll().contains(handyWorkerSaved));

	}

}
