
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
import domain.MiscellaneousRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;
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
	public void getMiscellaneousRecordTest() {
		final MiscellaneousRecord savedMiscellaneousRecord = this.miscellaneousRecordService.findById(2575);
		// Check fields
		Assert.isTrue(savedMiscellaneousRecord.getTitle().equals("Diamante al LoL"));
		Assert.isTrue(savedMiscellaneousRecord.getAttachment().equals(""));
		Assert.isTrue(savedMiscellaneousRecord.getComments().equals("main master yi"));
		Assert.isTrue(savedMiscellaneousRecord.getCurriculum().getId() == 2573);
	}

	/*
	 * Requirement 25.
	 * Handy workers can register their curricula. Every curriculum has a
	 * ticker, a personal record, some education records, some professional
	 * records, some endorser records, and some miscellaneous records.
	 */
	@Test
	public void createMiscellaneousRecordTest() {
		// Create fields
		final String title = "Test title";
		final String attachment = "Test attachment";
		final String comments = "Test comments";
		final Curriculum curriculum = this.curriculumService.findAll().get(0);
		// Create miscellaneous record
		MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setAttachment(attachment);
		miscellaneousRecord.setComments(comments);
		miscellaneousRecord.setCurriculum(curriculum);
		// Save miscellaneous record
		miscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord);
		// Get miscellaneous record
		final MiscellaneousRecord savedMiscellaneousRecord = this.miscellaneousRecordService.findById(miscellaneousRecord.getId());
		// Check fields
		Assert.isTrue(savedMiscellaneousRecord.getTitle().equals(title));
		Assert.isTrue(savedMiscellaneousRecord.getAttachment().equals(attachment));
		Assert.isTrue(savedMiscellaneousRecord.getComments().equals(comments));
		Assert.isTrue(savedMiscellaneousRecord.getCurriculum().equals(curriculum));
	}

}
