
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Phase;
import domain.WorkPlan;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PhaseServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PhaseService	phaseService;
	@Autowired
	private WorkPlanService	workPlanService;


	// Tests ------------------------------------------------------------------

	/*
	 * Requirement 7.
	 * Once an application is accepted, the corresponding handy worker may
	 * associate a work plan with the corresponding fix-up task. A work plan is
	 * composed of an arbitrary number of phases; for each phase, the system
	 * must store a title, a description, a start moment, and an end moment.
	 * Multiple phases may overlap, but none of them can be scheduled before or
	 * after the period of time during which the corresponding fix-up task must
	 * be carried out.
	 */
	@Test
	public void getPhaseTest() {
		final Phase savedPhase = this.phaseService.findById(2511);
		// Check fields
		Assert.isTrue(savedPhase.getTitle().equals("FixUpTask 1.1: Comenzando"));
		Assert.isTrue(savedPhase.getDescription().equals("Fase de prueba para comprobar que todo va bien"));
		Assert.isTrue(savedPhase.getStartMoment().getYear() == 118);
		Assert.isTrue(savedPhase.getStartMoment().getMonth() == 5);
		Assert.isTrue(savedPhase.getStartMoment().getDate() == 12);
		Assert.isTrue(savedPhase.getEndMoment().getYear() == 118);
		Assert.isTrue(savedPhase.getEndMoment().getMonth() == 5);
		Assert.isTrue(savedPhase.getEndMoment().getDate() == 13);
		Assert.isTrue(savedPhase.getWorkPlan().getId() == 2510);
	}

	/*
	 * Requirement 11.4.
	 * When a customer accepts an application, then the corresponding handy
	 * worker can create a work plan for the corresponding fix-up task. They
	 * can fully manage the work plan, which includes showing them, creating,
	 * updating, and deleting phases.
	 */
	@Test
	public void createPhaseTest() {
		// Create fields
		final WorkPlan workPlan = this.workPlanService.findAll().get(0);
		final String title = "Test title";
		final String description = "Test description";
		final Date startMoment = workPlan.getFixUpTask().getTimeLimit();
		final Date endMoment = workPlan.getFixUpTask().getTimeLimit();
		// Create phase
		Phase phase = new Phase();
		phase.setTitle(title);
		phase.setDescription(description);
		phase.setStartMoment(startMoment);
		phase.setEndMoment(endMoment);
		phase.setWorkPlan(workPlan);
		// Save phase
		phase = this.phaseService.save(phase);
		// Get personal record
		final Phase savedPhase = this.phaseService.findById(phase.getId());
		// Check fields
		Assert.isTrue(savedPhase.getTitle().equals(title));
		Assert.isTrue(savedPhase.getDescription().equals(description));
		Assert.isTrue(savedPhase.getStartMoment().equals(startMoment));
		Assert.isTrue(savedPhase.getEndMoment().equals(endMoment));
		Assert.isTrue(savedPhase.getWorkPlan().equals(workPlan));
	}

}
