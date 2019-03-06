
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Administrator;
import domain.Message;
import domain.SocialProfile;
import forms.AdministratorForm;

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
	private UserAccountRepository	userAccountRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator				validator;


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

	public AdministratorForm createForm() {

		final AdministratorForm administratorForm = new AdministratorForm();
		administratorForm.setName("");
		administratorForm.setMiddleName("");
		administratorForm.setSurname("");
		administratorForm.setPhoto("");
		administratorForm.setEmail("");
		administratorForm.setPhoneNumber("");
		administratorForm.setAddress("");
		administratorForm.setUsername("");
		administratorForm.setPassword("");
		administratorForm.setConfirmPassword("");

		return administratorForm;
	}

	public Administrator reconstructForm(final AdministratorForm administrator, final BindingResult bindingResult) {

		Administrator result;

		if (administrator.getId() == 0)
			result = this.create();
		else
			result = this.administratorRepository.findOne(administrator.getId());

		result.setName(administrator.getName());
		result.setMiddleName(administrator.getMiddleName());
		result.setSurname(administrator.getSurname());
		result.setPhoto(administrator.getPhoto());
		result.setEmail(administrator.getEmail());
		result.setPhoneNumber(administrator.getPhoneNumber());
		result.setAddress(administrator.getAddress());

		this.validator.validate(result, bindingResult);

		return result;
	}

}
