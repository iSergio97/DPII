
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Admin;
import domain.Endorsement;
import domain.Message;
import domain.SocialProfile;

@Service
@Transactional
public class AdminService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdminRepository			adminRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Constructors -----------------------------------------------------------

	public AdminService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Admin create() {

		final Admin admin = new Admin();
		UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount = this.userAccountRepository.save(userAccount);

		admin.setUserAccount(userAccount);
		admin.setMessageBoxes(this.messageBoxService.createSystemBoxes());
		admin.setMessagesSent(new ArrayList<Message>());
		admin.setMessagesReceived(new ArrayList<Message>());
		admin.setEndorses(new ArrayList<Endorsement>());
		admin.setEndorsedBy(new ArrayList<Endorsement>());
		admin.setSocialProfiles(new ArrayList<SocialProfile>());
		admin.setIsBanned(false);
		admin.setAddress("");
		admin.setPhoneNumber("");
		admin.setEmail("");
		admin.setPhoto("");
		admin.setMiddleName("");
		admin.setName("");
		admin.setSurname("");
		return admin;
	}
	public Admin save(final Admin admin) {
		Assert.isTrue(admin != null);
		return this.adminRepository.save(admin);
	}

	public Iterable<Admin> save(final Iterable<Admin> admins) {
		Assert.isTrue(admins != null);
		return this.adminRepository.save(admins);
	}

	public void delete(final Admin admin) {
		Assert.isTrue(admin != null);
		this.adminRepository.delete(admin);
	}

	public void delete(final Iterable<Admin> admins) {
		Assert.isTrue(admins != null);
		this.adminRepository.delete(admins);
	}

	public Admin findById(final int id) {
		return this.adminRepository.findOne(id);
	}

	public List<Admin> findAll() {
		return this.adminRepository.findAll();
	}

	public Admin findByUserAccountId(final int id) {
		return this.adminRepository.findByUserAccountId(id);
	}

	// Specific Methods ----------------------------------------------------------------

}
