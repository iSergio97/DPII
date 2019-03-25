
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	AdministratorService	administratorService;


	// Tests ------------------------------------------------------------------

	@Test
	public void createTest() {
		final Administrator administrator = this.administratorService.create();
		// Check properties
		Assert.isTrue(administrator.getName().equals(""));
		Assert.isTrue(administrator.getMiddleName().equals(""));
		Assert.isTrue(administrator.getSurname().equals(""));
		Assert.isTrue(administrator.getPhoto().equals(""));
		Assert.isTrue(administrator.getEmail().equals(""));
		Assert.isTrue(administrator.getPhoneNumber().equals(""));
		Assert.isTrue(administrator.getAddress().equals(""));
		Assert.isTrue(Boolean.valueOf(administrator.getIsSuspicious()).equals(false));
		Assert.isTrue(administrator.getPolarityScore().equals(0));
		Assert.isTrue(Boolean.valueOf(administrator.getIsBanned()).equals(false));
		// Check relationships properties
		Assert.isTrue(administrator.getUserAccount().isEnabled());
		Assert.isTrue(administrator.getSocialProfiles().isEmpty());
		Assert.isTrue(administrator.getMessagesSent().isEmpty());
		Assert.isTrue(administrator.getMessagesReceived().isEmpty());
	}

}
