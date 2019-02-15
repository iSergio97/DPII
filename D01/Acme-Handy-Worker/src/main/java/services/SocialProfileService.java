
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public SocialProfileService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public SocialProfile create() {
		final SocialProfile socialProfile = new SocialProfile();
		socialProfile.setLink("");
		socialProfile.setNickName("");
		socialProfile.setSocialNetworkName("");
		final UserAccount login = LoginService.getPrincipal();
		socialProfile.setActor(this.actorService.findByUserAccountId(login.getId()));

		return socialProfile;
	}

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

	public SocialProfile findById(final int id) {
		return this.socialProfileRepository.findOne(id);
	}

	public List<SocialProfile> findAll() {
		return this.socialProfileRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------

	public List<Actor> getActorsWithSocialProfilesWithWord(final String word) {
		return this.socialProfileRepository.getActorsWithSocialProfilesWithWord(word);
	}

}
