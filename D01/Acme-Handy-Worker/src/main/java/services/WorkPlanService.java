
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkPlanRepository;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;
import domain.WorkPlan;

@Service
@Transactional
public class WorkPlanService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private WorkPlanRepository	workPlanRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private PhaseService		phaseService;


	// Constructors -----------------------------------------------------------

	public WorkPlanService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public WorkPlan create() {
		final WorkPlan workPlan = new WorkPlan();

		final FixUpTask fixUpTask = new FixUpTask();
		final Collection<Phase> phases = new ArrayList<Phase>();

		workPlan.setFixUpTask(fixUpTask);
		workPlan.setPhases(phases);

		return workPlan;

	}

	public WorkPlan save(final WorkPlan workPlan) {
		Assert.isTrue(workPlan != null);
		return this.workPlanRepository.save(workPlan);
	}

	public Iterable<WorkPlan> save(final Iterable<WorkPlan> workPlans) {
		Assert.isTrue(workPlans != null);
		return this.workPlanRepository.save(workPlans);
	}

	public void delete(final WorkPlan workPlan) {
		Assert.isTrue(workPlan != null);
		this.workPlanRepository.delete(workPlan);
	}

	public void delete(final Iterable<WorkPlan> workPlans) {
		Assert.isTrue(workPlans != null);
		this.workPlanRepository.delete(workPlans);
	}

	public WorkPlan findById(final int id) {
		return this.workPlanRepository.findOne(id);
	}

	public List<WorkPlan> findAll() {
		return this.workPlanRepository.findAll();
	}

	// Other methods ----------------------------------------------------------

	public List<WorkPlan> findByHandyWorker(final HandyWorker handyWorker) {
		return this.applicationService.getWorkPlansByHandyWorker(handyWorker);
	}

}
