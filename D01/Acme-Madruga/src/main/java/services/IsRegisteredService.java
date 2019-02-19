package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.IsRegisteredRepository;
import security.LoginService;
import security.UserAccount;
import domain.IsRegistered;

@Service
@Transactional
public class IsRegisteredService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private IsRegisteredRepository		isRegisteredRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public IsRegisteredService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public IsRegistered save(final IsRegistered isRegistered) {
		Assert.isTrue(isRegistered != null);
		return this.isRegisteredRepository.save(isRegistered);
	}

	public Iterable<IsRegistered> save(final Iterable<IsRegistered> isRegistereds) {
		Assert.isTrue(isRegistereds != null);
		return this.isRegisteredRepository.save(isRegistereds);
	}

	public void delete(final IsRegistered isRegistered) {
		Assert.isTrue(isRegistered != null);
		this.isRegisteredRepository.delete(isRegistered);
	}

	public void delete(final Iterable<IsRegistered> isRegistereds) {
		Assert.isTrue(isRegistereds != null);
		this.isRegisteredRepository.delete(isRegistereds);
	}

	public IsRegistered findOne(final int id) {
		return this.isRegisteredRepository.findOne(id);
	}

	public List<IsRegistered> findAll() {
		return this.isRegisteredRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
