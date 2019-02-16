package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserAccountRepository;
import security.LoginService;
import security.UserAccount;
import domain.UserAccount;

@Service
@Transactional
public class UserAccountService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private UserAccountRepository		userAccountRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public UserAccountService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public UserAccount save(final UserAccount userAccount) {
		Assert.isTrue(userAccount != null);
		return this.userAccountRepository.save(userAccount);
	}

	public Iterable<UserAccount> save(final Iterable<UserAccount> userAccounts) {
		Assert.isTrue(userAccounts != null);
		return this.userAccountRepository.save(userAccounts);
	}

	public void delete(final UserAccount userAccount) {
		Assert.isTrue(userAccount != null);
		this.userAccountRepository.delete(userAccount);
	}

	public void delete(final Iterable<UserAccount> userAccounts) {
		Assert.isTrue(userAccounts != null);
		this.userAccountRepository.delete(userAccounts);
	}

	public UserAccount findOne(final int id) {
		return this.userAccountRepository.findOne(id);
	}

	public List<UserAccount> findAll() {
		return this.userAccountRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
