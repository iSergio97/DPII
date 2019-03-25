
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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


	// Tests ------------------------------------------------------------------

	/**
	 * Attempts to save an administrator with the given data.
	 */
	private void saveAdministratorWith(final String username, final String password, final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber, final String address) {
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
		administratorUserAccount.setPassword(password);
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
		Assert.isTrue(administrator.getUserAccount().getPassword().equals(password));
	}

	/**
	 * Attempts to save an administrator with legal data.
	 */
	@Test
	public void saveGoodAdministratorTest() {
		// Administrator fields
		final String name = "AdministratorName";
		final String middleName = "AdministratorMiddleName";
		final String surname = "AdministratorSurname";
		final String photo = "https://www.imgur.com";
		final String email = "administrator_email@acme.com";
		final String phoneNumber = "+123 (456) 7890";
		final String address = "AdministratorAddress";
		final String username = "AdministratorUsername";
		final String password = "AdministratorPassword";
		this.saveAdministratorWith(username, password, name, middleName, surname, photo, email, phoneNumber, address);
	}

	/*
	 * Attempts to save an administrator with illegal data.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void saveBadAdministratorTest() {
		// Administrator fields
		final String name = "AdministratorName";
		final String middleName = "AdministratorMiddleName";
		final String surname = "AdministratorSurname";
		final String photo = "IllegalPhoto";
		final String email = "IllegalEmailAddress";
		final String phoneNumber = "IllegalPhoneNumber";
		final String address = "AdministratorAddress";
		final String username = "AdministratorUsername";
		final String password = "AdministratorPassword";
		this.saveAdministratorWith(username, password, name, middleName, surname, photo, email, phoneNumber, address);
	}

}
