package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherhoodService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private BrotherhoodRepository		brotherhoodRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public BrotherhoodService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Brotherhood save(final Brotherhood brotherhood) {
		Assert.isTrue(brotherhood != null);
		return this.brotherhoodRepository.save(brotherhood);
	}

	public Iterable<Brotherhood> save(final Iterable<Brotherhood> brotherhoods) {
		Assert.isTrue(brotherhoods != null);
		return this.brotherhoodRepository.save(brotherhoods);
	}

	public void delete(final Brotherhood brotherhood) {
		Assert.isTrue(brotherhood != null);
		this.brotherhoodRepository.delete(brotherhood);
	}

	public void delete(final Iterable<Brotherhood> brotherhoods) {
		Assert.isTrue(brotherhoods != null);
		this.brotherhoodRepository.delete(brotherhoods);
	}

	public Brotherhood findOne(final int id) {
		return this.brotherhoodRepository.findOne(id);
	}

	public List<Brotherhood> findAll() {
		return this.brotherhoodRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods


	public Brotherhood findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findOne(userAccount.getId());
	}

}
