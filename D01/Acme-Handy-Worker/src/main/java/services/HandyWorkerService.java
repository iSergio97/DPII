
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Application;
import domain.Endorsement;
import domain.HandyWorker;
import domain.Message;
import domain.MessageBox;
import domain.Note;
import domain.SocialProfile;
import domain.Tutorial;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	//Constructors
	public HandyWorkerService() {
		super();
	}


	// SuportingServices

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private FinderService			finderService;


	//Methods

	public HandyWorker create() {
		final HandyWorker handyWorker = new HandyWorker();
		final List<Authority> ls = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDY_WORKER);
		ls.add(authority);
		final UserAccount ua = new UserAccount();
		ua.setAuthorities(ls);
		final UserAccount saved = this.userAccountRepository.save(ua);
		handyWorker.setUserAccount(saved);
		final Collection<MessageBox> mbls = this.messageBoxService.createSystemBoxes();
		handyWorker.setMessageBoxes(mbls);
		handyWorker.setApplications(new ArrayList<Application>());
		handyWorker.setFinder(this.finderService.create());
		handyWorker.setCurriculum(null);
		handyWorker.setTutorials(new ArrayList<Tutorial>());
		handyWorker.setNotes(new ArrayList<Note>());
		handyWorker.setMessagesSent(new ArrayList<Message>());
		handyWorker.setMessagesReceived(new ArrayList<Message>());
		handyWorker.setEndorses(new ArrayList<Endorsement>());
		handyWorker.setEndorsedBy(new ArrayList<Endorsement>());
		handyWorker.setSocialProfiles(new ArrayList<SocialProfile>());
		handyWorker.setIsBanned(false);
		handyWorker.setAddress("");
		handyWorker.setPhoneNumber("");
		handyWorker.setEmail("");
		handyWorker.setPhoto("");
		handyWorker.setMiddleName("");
		handyWorker.setName("");
		handyWorker.setSurname("");
		handyWorker.setMake("");

		return handyWorker;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.isTrue(handyWorker != null);
		return this.handyWorkerRepository.save(handyWorker);
	}

	public Iterable<HandyWorker> save(final Iterable<HandyWorker> handyWorkers) {
		Assert.isTrue(handyWorkers != null);
		return this.handyWorkerRepository.save(handyWorkers);
	}

	public void delete(final HandyWorker handyWorker) {
		Assert.isTrue(handyWorker != null);
		this.handyWorkerRepository.delete(handyWorker);
	}

	public void delete(final Iterable<HandyWorker> handyWorkers) {
		Assert.isTrue(handyWorkers != null);
		this.handyWorkerRepository.delete(handyWorkers);
	}

	public HandyWorker findById(final int id) {
		return this.handyWorkerRepository.findOne(id);
	}

	public List<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public List<HandyWorker> getTopApplications() {
		return this.handyWorkerRepository.getTopApplications();
	}

	public List<HandyWorker> getTopComplaints() {
		return this.handyWorkerRepository.getTopComplaints();
	}

	// Custom Methods-------------------------------------------------------------------

	public HandyWorker findByUserAccountId(final int userAccountId) {
		return this.handyWorkerRepository.findByUserAccountId(userAccountId);
	}

}
