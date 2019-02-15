
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsorship;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	// Supporting services ----------------------------------------------------
	@Autowired
	private SponsorService sponsorService;

	// Constructors -----------------------------------------------------------

	public SponsorshipService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Sponsorship create() {
		Sponsorship sponsorship = new Sponsorship();
		sponsorship.setBanner("");
		UserAccount login = LoginService.getPrincipal();
		sponsorship.setSponsor(sponsorService.findById(login.getId()));
		sponsorship.setTargetPage("");

		return sponsorship;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.isTrue(sponsorship != null);
		return this.sponsorshipRepository.save(sponsorship);
	}

	public Iterable<Sponsorship> save(final Iterable<Sponsorship> sponsorships) {
		Assert.isTrue(sponsorships != null);
		return this.sponsorshipRepository.save(sponsorships);
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.isTrue(sponsorship != null);
		this.sponsorshipRepository.delete(sponsorship);
	}

	public void delete(final Iterable<Sponsorship> sponsorships) {
		Assert.isTrue(sponsorships != null);
		this.sponsorshipRepository.delete(sponsorships);
	}

	public Sponsorship findById(final int id) {
		return this.sponsorshipRepository.findOne(id);
	}

	public List<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------
}
