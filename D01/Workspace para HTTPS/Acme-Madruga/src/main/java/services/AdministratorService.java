package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private AdministratorRepository		administratorRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public AdministratorService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

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
