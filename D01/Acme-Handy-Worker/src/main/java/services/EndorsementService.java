
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@Service
@Transactional
public class EndorsementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EndorsementRepository	endorsementRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public EndorsementService() {
		super();
	}

	// CRUD Methods -----------------------------------------------------------

	public Endorsement create() {
		Endorsement endorsement = new Endorsement();
		endorsement.setComment(new ArrayList<String>());
		endorsement.setDate(new Date());
		endorsement.setEndorser(actorService.findPrincipal());

		return endorsement;
	}

	public Endorsement save(final Endorsement endorsement) {
		Assert.isTrue(endorsement != null);
		return this.endorsementRepository.save(endorsement);
	}

	public Iterable<Endorsement> save(final Iterable<Endorsement> endorsements) {
		Assert.isTrue(endorsements != null);
		return this.endorsementRepository.save(endorsements);
	}

	public void delete(final Endorsement endorsement) {
		Assert.isTrue(endorsement != null);
		this.endorsementRepository.delete(endorsement);
	}

	public void delete(final Iterable<Endorsement> endorsements) {
		Assert.isTrue(endorsements != null);
		this.endorsementRepository.delete(endorsements);
	}

	public Endorsement findById(final int id) {
		return this.endorsementRepository.findOne(id);
	}

	public List<Endorsement> findAll() {
		return this.endorsementRepository.findAll();
	}

	// Other Methods ----------------------------------------------------------

	public Endorsement[] getEndorsements(final Customer c) {
		return this.endorsementRepository.getEndorsements(c);
	}

	public Endorsement[] getEndorsements(final HandyWorker hw) {
		return this.endorsementRepository.getEndorsements(hw);
	}

}
