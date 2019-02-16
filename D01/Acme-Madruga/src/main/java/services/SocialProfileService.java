package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private SocialProfileRepository		socialProfileRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public SocialProfileService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.isTrue(socialProfile != null);
		return this.socialProfileRepository.save(socialProfile);
	}

	public Iterable<SocialProfile> save(final Iterable<SocialProfile> socialProfiles) {
		Assert.isTrue(socialProfiles != null);
		return this.socialProfileRepository.save(socialProfiles);
	}

	public void delete(final SocialProfile socialProfile) {
		Assert.isTrue(socialProfile != null);
		this.socialProfileRepository.delete(socialProfile);
	}

	public void delete(final Iterable<SocialProfile> socialProfiles) {
		Assert.isTrue(socialProfiles != null);
		this.socialProfileRepository.delete(socialProfiles);
	}

	public SocialProfile findOne(final int id) {
		return this.socialProfileRepository.findOne(id);
	}

	public List<SocialProfile> findAll() {
		return this.socialProfileRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
