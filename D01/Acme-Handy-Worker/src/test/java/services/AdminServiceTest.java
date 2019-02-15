
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Admin;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	AdminService	adminService;


	// Tests ------------------------------------------------------------------

	@Test
	public void createTest() {
		// Create Admin Object
		final Admin a = this.adminService.create();

		// Check properties
		Assert.isTrue(a.getName().equals(""));
		Assert.isTrue(a.getMiddleName().equals(""));
		Assert.isTrue(a.getSurname().equals(""));
		Assert.isTrue(a.getPhoto().equals(""));
		Assert.isTrue(a.getEmail().equals(""));
		Assert.isTrue(a.getPhoneNumber().equals(""));
		Assert.isTrue(a.getAddress().equals(""));

		// Check relationships properties
		Assert.isTrue(a.getUserAccount().isEnabled());
		Assert.isTrue(a.getMessageBoxes().isEmpty());
		Assert.isTrue(a.getSocialProfiles().isEmpty());
		Assert.isTrue(a.getEndorsedBy().isEmpty());
		Assert.isTrue(a.getEndorses().isEmpty());
		Assert.isTrue(a.getMessagesSent().isEmpty());
		Assert.isTrue(a.getMessagesReceived().isEmpty());
		Assert.isTrue(a.getNotes().isEmpty());

		this.adminService.save(a);

	}

}
