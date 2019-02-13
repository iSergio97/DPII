
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
import domain.ProfessionalRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;
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
	public void getProfessionalRecordTest() {
		final ProfessionalRecord savedProfessionalRecord = this.professionalRecordService.findById(2576);
		// Check fields
		Assert.isTrue(savedProfessionalRecord.getCompanyName().equals("Genera"));
		Assert.isTrue(savedProfessionalRecord.getStartingTime().getYear() == 115);
		Assert.isTrue(savedProfessionalRecord.getStartingTime().getMonth() == 6);
		Assert.isTrue(savedProfessionalRecord.getStartingTime().getDate() == 19);
		Assert.isTrue(savedProfessionalRecord.getEndingTime().getYear() == 119);
		Assert.isTrue(savedProfessionalRecord.getEndingTime().getMonth() == 6);
		Assert.isTrue(savedProfessionalRecord.getEndingTime().getDate() == 19);
		Assert.isTrue(savedProfessionalRecord.getRole().equals("Director de proyecto"));
		Assert.isTrue(savedProfessionalRecord.getAttachment().equals(""));
		Assert.isTrue(savedProfessionalRecord.getComments().equals(""));
		Assert.isTrue(savedProfessionalRecord.getCurriculum().getId() == 2573);
	}

	/*
	 * Requirement 25.
	 * Handy workers can register their curricula. Every curriculum has a
	 * ticker, a personal record, some education records, some professional
	 * records, some endorser records, and some miscellaneous records.
	 */
	@Test
	public void createProfessionalRecordTest() {
		// Create fields
		final String companyName = "Test diploma title";
		final Date startingTime = new Date();
		startingTime.setYear(startingTime.getYear() - 1);
		final Date endingTime = new Date();
		endingTime.setYear(endingTime.getYear() + 1);
		final String role = "Test role";
		final String attachment = "Test attachment";
		final String comments = "comments";
		final Curriculum curriculum = this.curriculumService.findAll().get(0);
		// Create professional record
		ProfessionalRecord professionalRecord = new ProfessionalRecord();
		professionalRecord.setCompanyName(companyName);
		professionalRecord.setStartingTime(startingTime);
		professionalRecord.setEndingTime(endingTime);
		professionalRecord.setRole(role);
		professionalRecord.setAttachment(attachment);
		professionalRecord.setComments(comments);
		professionalRecord.setCurriculum(curriculum);
		// Save professional record
		professionalRecord = this.professionalRecordService.save(professionalRecord);
		// Get professional record
		final ProfessionalRecord savedProfessionalRecord = this.professionalRecordService.findById(professionalRecord.getId());
		// Check fields
		Assert.isTrue(savedProfessionalRecord.getCompanyName().equals(companyName));
		Assert.isTrue(savedProfessionalRecord.getStartingTime().equals(startingTime));
		Assert.isTrue(savedProfessionalRecord.getEndingTime().equals(endingTime));
		Assert.isTrue(savedProfessionalRecord.getRole().equals(role));
		Assert.isTrue(savedProfessionalRecord.getAttachment().equals(attachment));
		Assert.isTrue(savedProfessionalRecord.getComments().equals(comments));
		Assert.isTrue(savedProfessionalRecord.getCurriculum().equals(curriculum));
	}

}
