
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.PersonalRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;
	@Autowired
	private CurriculumService		curriculumService;


	// Tests ------------------------------------------------------------------

	/*
	 * Requirement 25.
	 * Handy workers can register their curricula. Every curriculum has a
	 * ticker, a personal record, some education records, some professional
	 * records, some endorser records, and some miscellaneous records.
	 */
	@Test
	public void getPersonalRecordTest() {
		final PersonalRecord savedPersonalRecord = this.personalRecordService.findById(2577);
		// Check fields
		Assert.isTrue(savedPersonalRecord.getFullName().equals("Sara Fernandez Equisde"));
		Assert.isTrue(savedPersonalRecord.getPhoto().equals("https://gyazo.com/673df2f21880d6c7e568ec54cb8dc53b"));
		Assert.isTrue(savedPersonalRecord.getEmail().equals("sarafdez@gmail.com"));
		Assert.isTrue(savedPersonalRecord.getPhoneNumber().equals("+34 630681378"));
		Assert.isTrue(savedPersonalRecord.getLinkedIn().equals("https://www.linkedin.com/company/memes"));
		Assert.isTrue(savedPersonalRecord.getCurriculum().getId() == 2573);
	}

	/*
	 * Requirement 25.
	 * Handy workers can register their curricula. Every curriculum has a
	 * ticker, a personal record, some education records, some professional
	 * records, some endorser records, and some miscellaneous records.
	 */
	@Test
	public void createPersonalRecordTest() {
		// Create fields
		final String fullName = "Test full name";
		final String photo = "Test photo";
		final String email = "Test email";
		final String phoneNumber = "Test phone number";
		final String linkedIn = "Test linked in";
		final Curriculum curriculum = this.curriculumService.findAll().get(0);
		// Create personal record
		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setFullName(fullName);
		personalRecord.setPhoto(photo);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phoneNumber);
		personalRecord.setLinkedIn(linkedIn);
		personalRecord.setCurriculum(curriculum);
		// Save personal record
		personalRecord = this.personalRecordService.save(personalRecord);
		// Get personal record
		final PersonalRecord savedPersonalRecord = this.personalRecordService.findById(personalRecord.getId());
		// Check fields
		Assert.isTrue(savedPersonalRecord.getFullName().equals(fullName));
		Assert.isTrue(savedPersonalRecord.getPhoto().equals(photo));
		Assert.isTrue(savedPersonalRecord.getEmail().equals(email));
		Assert.isTrue(savedPersonalRecord.getPhoneNumber().equals(phoneNumber));
		Assert.isTrue(savedPersonalRecord.getLinkedIn().equals(linkedIn));
		Assert.isTrue(savedPersonalRecord.getCurriculum().equals(curriculum));
	}

}
