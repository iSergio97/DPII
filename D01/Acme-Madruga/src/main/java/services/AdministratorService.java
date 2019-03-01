
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utilities.HashPassword;
import domain.Administrator;
import domain.Message;
import domain.SocialProfile;

@Service
@Transactional
public class AdministratorService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private AdministratorRepository	administratorRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private UserAccountRepository	userAccountRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public AdministratorService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Administrator create() {
		final Administrator administrator = new Administrator();

		// create user account
		UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount = this.userAccountRepository.save(userAccount);

		// set fields
		administrator.setName("");
		administrator.setMiddleName("");
		administrator.setSurname("");
		administrator.setPhoto("");
		administrator.setEmail("");
		administrator.setPhoneNumber("");
		administrator.setAddress("");
		administrator.setIsSuspicious(false);
		administrator.setPolarityScore(null);
		administrator.setIsBanned(false);
		// set relationships
		administrator.setUserAccount(userAccount);
		administrator.setMessageBoxes(this.messageBoxService.createSystemBoxes());
		administrator.setMessagesSent(new ArrayList<Message>());
		administrator.setMessagesReceived(new ArrayList<Message>());
		administrator.setSocialProfiles(new ArrayList<SocialProfile>());

		return administrator;
	}

	public Administrator save(final Administrator administrator) {
		Assert.isTrue(administrator != null);
		return this.administratorRepository.save(administrator);
	}

	public Iterable<Administrator> save(final Iterable<Administrator> administrators) {
		Assert.isTrue(administrators != null);
		return this.administratorRepository.save(administrators);
	}

	public void delete(final Administrator administrator) {
		Assert.isTrue(administrator != null);
		this.administratorRepository.delete(administrator);
	}

	public void delete(final Iterable<Administrator> administrators) {
		Assert.isTrue(administrators != null);
		this.administratorRepository.delete(administrators);
	}

	public Administrator findOne(final int id) {
		return this.administratorRepository.findOne(id);
	}

	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Administrator findByUserAccountId(final int id) {
		return this.administratorRepository.findByUserAccountId(id);
	}

	public Administrator findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
