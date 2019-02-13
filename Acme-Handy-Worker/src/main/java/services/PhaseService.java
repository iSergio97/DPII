
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PhaseRepository	phaseRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private WorkPlanService	workPlanService;


	// Constructors -----------------------------------------------------------

	public PhaseService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public Phase create() {
		final Phase phase = new Phase();
		phase.setTitle("");
		phase.setDescription("");
		phase.setStartMoment(new Date());
		phase.setEndMoment(new Date());
		phase.setWorkPlan(this.workPlanService.create());
		return phase;
	}

	public Phase save(final Phase phase) {
		Assert.isTrue(phase != null);
		return this.phaseRepository.save(phase);
	}

	public Iterable<Phase> save(final Iterable<Phase> phases) {
		Assert.isTrue(phases != null);
		return this.phaseRepository.save(phases);
	}

	public void delete(final Phase phase) {
		Assert.isTrue(phase != null);
		this.phaseRepository.delete(phase);
	}

	public void delete(final Iterable<Phase> phases) {
		Assert.isTrue(phases != null);
		this.phaseRepository.delete(phases);
	}

	public Phase findById(final int id) {
		return this.phaseRepository.findOne(id);
	}

	public List<Phase> findAll() {
		return this.phaseRepository.findAll();
	}

	// Other methods ----------------------------------------------------------

	//Este metodo devuelve las phases asociado a un id de un workplan
	public Collection<Phase> findByWorkplanId(final int id) {

		return this.phaseRepository.findByWorkplanId(id);
	}
}
