
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountRepository;
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
	AdministratorService			administratorService;

	// Supporting systems -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;

	// Testing data -----------------------------------------------------------

	private final Object[][]		testingData	= {
		{
		"AdministratorName", "AdministratorMiddleName", "AdministratorSurname", "https://www.imgur.com", "administrator_email@acme.com", "+123 (456) 7890", "AdministratorAddress", "AdministratorUsername", "AdministratorPassword", null
		}, {
		"AdministratorName", "AdministratorMiddleName", "AdministratorSurname", "IllegalPhoto", "IllegalEmail", "IllegalPhoneNumber", "AdministratorAddress", "AdministratorUsername", "AdministratorPassword", IllegalArgumentException.class
		}
												};


	// Tests ------------------------------------------------------------------

	/**
	 * Attempts to save an administrator with the given data.
	 */
	private void template(final String username, final String password, final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber, final String address,
		final Class<?> expectedThrowableClass) {
		Class<?> throwableClass = null;
		final String encodedPassword = new Md5PasswordEncoder().encodePassword(password, null);
		try {
			// Create administrator
			Administrator administrator = this.administratorService.create();
			UserAccount administratorUserAccount = administrator.getUserAccount();
			administrator.setName(name);
			administrator.setMiddleName(middleName);
			administrator.setSurname(surname);
			administrator.setPhoto(photo);
			administrator.setEmail(email);
			administrator.setPhoneNumber(phoneNumber);
			administrator.setAddress(address);
			administratorUserAccount.setUsername(username);
			administratorUserAccount.setPassword(encodedPassword);
			// Save administrator
			administratorUserAccount = this.userAccountRepository.save(administratorUserAccount);
			administrator.setUserAccount(administratorUserAccount);
			administrator = this.administratorService.save(administrator);
			// Check if administrator has been saved correctly
			Assert.isTrue(administrator.getName().equals(name));
			Assert.isTrue(administrator.getMiddleName().equals(middleName));
			Assert.isTrue(administrator.getSurname().equals(surname));
			Assert.isTrue(administrator.getPhoto().equals(photo));
			Assert.isTrue(administrator.getEmail().equals(email));
			Assert.isTrue(administrator.getPhoneNumber().equals(phoneNumber));
			Assert.isTrue(administrator.getAddress().equals(address));
			Assert.isTrue(administrator.getUserAccount().isEnabled());
			Assert.isTrue(administrator.getUserAccount().getUsername().equals(username));
			Assert.isTrue(administrator.getUserAccount().getPassword().equals(encodedPassword));
		} catch (final Throwable throwable) {
			throwableClass = throwable.getClass();
		}

		super.checkExceptions(expectedThrowableClass, throwableClass);
	}

	@Test
	public void driver() {
		for (int i = 0; i < this.testingData.length; ++i)
			this.template((String) this.testingData[i][0], (String) this.testingData[i][1], (String) this.testingData[i][2], (String) this.testingData[i][3], (String) this.testingData[i][4], (String) this.testingData[i][5],
				(String) this.testingData[i][6], (String) this.testingData[i][7], (String) this.testingData[i][8], (Class<?>) this.testingData[i][9]);
	}

}
