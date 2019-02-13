
package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository				actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ComplaintService			complaintService;

	@Autowired
	private EducationalRecordService	educationalRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private MessageService				messageService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private SocialProfileService		socialProfileService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Actor save(final Actor actor) {
		Assert.isTrue(actor != null);
		return this.actorRepository.save(actor);
	}

	public Iterable<Actor> save(final Iterable<Actor> actors) {
		Assert.isTrue(actors != null);
		return this.actorRepository.save(actors);
	}

	public void delete(final Actor actor) {
		Assert.isTrue(actor != null);
		this.actorRepository.delete(actor);
	}

	public void delete(final Iterable<Actor> actors) {
		Assert.isTrue(actors != null);
		this.actorRepository.delete(actors);
	}

	public Actor findById(final int id) {
		return this.actorRepository.findOne(id);
	}

	public List<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------

	public Actor findByUserAccountId(final int id) {
		return this.actorRepository.findByUserAccountId(id);
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

	public List<Actor> getActorsWithWord(final String word) {

		return this.actorRepository.getActorsWithWord(word);
	}

	public List<Actor> findSuspicious() {
		final HashSet<Actor> uniqueSuspiciousActors = new HashSet<>();
		for (final String spamWord : this.systemConfigurationService.getSystemConfiguration().getSpamWords()) {
			uniqueSuspiciousActors.addAll(this.getActorsWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.socialProfileService.getActorsWithSocialProfilesWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.messageService.getActorsWhoSendMessagesWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.fixUpTaskService.getCustomersWhoPublishFixUpTasksWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.complaintService.getCustomersWhoPublishComplaintsWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.educationalRecordService.getHandyWorkersWithEducationalRecordsWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.endorserRecordService.getHandyWorkersWithEndorserRecordsWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.miscellaneousRecordService.getHandyWorkersWithMiscellaneousRecordsWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.personalRecordService.getHandyWorkersWithPersonalRecordsWithWord(spamWord));
			uniqueSuspiciousActors.addAll(this.professionalRecordService.getHandyWorkersWithProfessionalRecordsWithWord(spamWord));
		}
		return new ArrayList<Actor>(uniqueSuspiciousActors);
	}

}
