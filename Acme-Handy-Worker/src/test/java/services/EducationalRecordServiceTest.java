
package services;

import java.util.Date;

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

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EducationalRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private EducationalRecordService	educationalRecordService;

	@Autowired
	private CurriculumService			curriculumService;


	// Tests ------------------------------------------------------------------

	/*
	 * Requirement 25.
	 * Handy workers can register their curricula. Every curriculum has a
	 * ticker, a personal record, some education records, some professional
	 * records, some endorser records, and some miscellaneous records.
	 */
	@Test
	public void getEducationalRecordTest() {
		final EducationalRecord savedEducationalRecord = this.educationalRecordService.findById(2574);
		// Check fields
		Assert.isTrue(savedEducationalRecord.getDiplomaTitle().equals("Ingenieria Informatica"));
		Assert.isTrue(savedEducationalRecord.getStartingTime().getYear() == 115);
		Assert.isTrue(savedEducationalRecord.getStartingTime().getMonth() == 8);
		Assert.isTrue(savedEducationalRecord.getStartingTime().getDate() == 15);
		Assert.isTrue(savedEducationalRecord.getEndingTime().getYear() == 150);
		Assert.isTrue(savedEducationalRecord.getEndingTime().getMonth() == 6);
		Assert.isTrue(savedEducationalRecord.getEndingTime().getDate() == 1);
		Assert.isTrue(savedEducationalRecord.getInstitution().equals("Universidad de Sevilla"));
		Assert.isTrue(savedEducationalRecord.getAttachment().equals(""));
		Assert.isTrue(savedEducationalRecord.getComments().equals("Este tío controla."));
		Assert.isTrue(savedEducationalRecord.getCurriculum().getId() == 2573);
	}

	/*
	 * Requirement 25.
	 * Handy workers can register their curricula. Every curriculum has a
	 * ticker, a personal record, some education records, some professional
	 * records, some endorser records, and some miscellaneous records.
	 */
	@Test
	public void createEducationalRecordTest() {
		// Create fields
		final String diplomaTitle = "Test diploma title";
		final Date startingTime = new Date();
		startingTime.setYear(startingTime.getYear() - 1);
		final Date endingTime = new Date();
		endingTime.setYear(endingTime.getYear() + 1);
		final String institution = "Test institution";
		final String attachment = "Test attachment";
		final String comments = "comments";
		final Curriculum curriculum = this.curriculumService.findAll().get(0);
		// Create educational record
		EducationalRecord educationalRecord = new EducationalRecord();
		educationalRecord.setDiplomaTitle(diplomaTitle);
		educationalRecord.setStartingTime(startingTime);
		educationalRecord.setEndingTime(endingTime);
		educationalRecord.setInstitution(institution);
		educationalRecord.setAttachment(attachment);
		educationalRecord.setComments(comments);
		educationalRecord.setCurriculum(curriculum);
		// Save educational record
		educationalRecord = this.educationalRecordService.save(educationalRecord);
		// Get educational record
		final EducationalRecord savedEducationalRecord = this.educationalRecordService.findById(educationalRecord.getId());
		// Check fields
		Assert.isTrue(savedEducationalRecord.getDiplomaTitle().equals(diplomaTitle));
		Assert.isTrue(savedEducationalRecord.getStartingTime().equals(startingTime));
		Assert.isTrue(savedEducationalRecord.getEndingTime().equals(endingTime));
		Assert.isTrue(savedEducationalRecord.getInstitution().equals(institution));
		Assert.isTrue(savedEducationalRecord.getAttachment().equals(attachment));
		Assert.isTrue(savedEducationalRecord.getComments().equals(comments));
		Assert.isTrue(savedEducationalRecord.getCurriculum().equals(curriculum));
	}

}
