
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.LoginService;
import security.UserAccount;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TutorialRepository	tutorialRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	// Constructors -----------------------------------------------------------

	public TutorialService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Tutorial create() {
		final Tutorial tutorial = new Tutorial();
		final UserAccount login = LoginService.getPrincipal();
		tutorial.setHandyWorker(this.handyWorkerService.findByUserAccountId(login.getId()));
		tutorial.setLastUpdated(new Date());
		final List<String> pictures = new ArrayList<String>();
		pictures.add("");
		pictures.add("");
		pictures.add("");
		pictures.add("");
		tutorial.setPictures(pictures);
		tutorial.setSections(new ArrayList<Section>());
		tutorial.setSummary("");
		tutorial.setSponsorships(new ArrayList<Sponsorship>());
		tutorial.setTitle("");

		return tutorial;
	}

	public Tutorial save(final Tutorial tutorial) {
		Assert.isTrue(tutorial != null);
		return this.tutorialRepository.save(tutorial);
	}

	public Iterable<Tutorial> save(final Iterable<Tutorial> tutorials) {
		Assert.isTrue(tutorials != null);
		return this.tutorialRepository.save(tutorials);
	}

	public void delete(final Tutorial tutorial) {
		Assert.isTrue(tutorial != null);
		this.tutorialRepository.delete(tutorial);
	}

	public void delete(final Iterable<Tutorial> tutorials) {
		Assert.isTrue(tutorials != null);
		this.tutorialRepository.delete(tutorials);
	}

	public Tutorial findById(final int id) {
		return this.tutorialRepository.findOne(id);
	}

	public List<Tutorial> findAll() {
		return this.tutorialRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------
}
