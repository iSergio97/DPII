
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SponsorRepository		sponsorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Constructors -----------------------------------------------------------

	public SponsorService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Sponsor create() {
		final Sponsor sponsor = new Sponsor();
		final List<Authority> ls = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		ls.add(authority);
		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(ls);
		sponsor.setMessageBoxes(this.messageBoxService.createSystemBoxes());
		final UserAccount saved = this.userAccountRepository.save(userAccount);
		sponsor.setUserAccount(saved);

		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.isTrue(sponsor != null);
		return this.sponsorRepository.save(sponsor);
	}

	public Iterable<Sponsor> save(final Iterable<Sponsor> sponsors) {
		Assert.isTrue(sponsors != null);
		return this.sponsorRepository.save(sponsors);
	}

	public void delete(final Sponsor sponsor) {
		Assert.isTrue(sponsor != null);
		this.sponsorRepository.delete(sponsor);
	}

	public void delete(final Iterable<Sponsor> sponsors) {
		Assert.isTrue(sponsors != null);
		this.sponsorRepository.delete(sponsors);
	}

	public Sponsor findById(final int id) {
		return this.sponsorRepository.findOne(id);
	}

	public Sponsor findByUserAccountId(final int id) {
		return this.sponsorRepository.findByUserAccountId(id);
	}

	public List<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------
}
