
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Endorsement;
import domain.Message;
import domain.Referee;
import domain.SocialProfile;

@Service
@Transactional
public class RefereeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RefereeRepository		refereeRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Constructors -----------------------------------------------------------

	public RefereeService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Referee create() {

		final Referee referee = new Referee();
		UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount = this.userAccountRepository.save(userAccount);

		referee.setUserAccount(userAccount);
		referee.setMessageBoxes(this.messageBoxService.createSystemBoxes());
		referee.setMessagesSent(new ArrayList<Message>());
		referee.setMessagesReceived(new ArrayList<Message>());
		referee.setEndorses(new ArrayList<Endorsement>());
		referee.setEndorsedBy(new ArrayList<Endorsement>());
		referee.setSocialProfiles(new ArrayList<SocialProfile>());
		referee.setIsBanned(false);
		referee.setAddress("");
		referee.setPhoneNumber("");
		referee.setEmail("");
		referee.setPhoto("");
		referee.setMiddleName("");
		referee.setName("");
		referee.setSurname("");
		return referee;
	}

	public Referee save(final Referee referee) {
		Assert.isTrue(referee != null);
		return this.refereeRepository.save(referee);
	}

	public Iterable<Referee> save(final Iterable<Referee> referees) {
		Assert.isTrue(referees != null);
		return this.refereeRepository.save(referees);
	}

	public void delete(final Referee referee) {
		Assert.isTrue(referee != null);
		this.refereeRepository.delete(referee);
	}

	public void delete(final Iterable<Referee> referees) {
		Assert.isTrue(referees != null);
		this.refereeRepository.delete(referees);
	}

	public Referee findById(final int id) {
		return this.refereeRepository.findOne(id);
	}

	public Referee findByUserAccountId(final int id) {
		return this.refereeRepository.findByUserAccountId(id);
	}

	public List<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------
}
