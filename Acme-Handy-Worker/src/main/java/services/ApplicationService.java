
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.HandyWorker;
import domain.WorkPlan;

@Service
@Transactional
public class ApplicationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;


	// Constructors -----------------------------------------------------------

	public ApplicationService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public Application create() {
		final Application application = new Application();

		final UserAccount login = LoginService.getPrincipal();
		final HandyWorker handyWorker = this.handyWorkerService.findById(login.getId());
		application.setHandyWorker(handyWorker);
		application.setFixUpTask(this.fixUpTaskService.create());
		application.setMoment(new Date());
		application.setStatus("");
		return application;
	}

	public Application save(final Application application) {
		Assert.isTrue(application != null);
		return this.applicationRepository.save(application);
	}

	public Iterable<Application> save(final Iterable<Application> applications) {
		Assert.isTrue(applications != null);
		return this.applicationRepository.save(applications);
	}

	public void delete(final Application application) {
		Assert.isTrue(application != null);
		this.applicationRepository.delete(application);
	}

	public void delete(final Iterable<Application> applications) {
		Assert.isTrue(applications != null);
		this.applicationRepository.delete(applications);
	}

	public Application findById(final int id) {
		Assert.isInstanceOf(Integer.class, id);
		return this.applicationRepository.findOne(id);
	}

	public List<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	// Other methods ----------------------------------------------------------

	public List<Application> findByHandyWorker(final HandyWorker handyWorker) {
		return this.applicationRepository.getApplicationsByHandyWorkerId(handyWorker.getId());
	}

	public List<WorkPlan> getWorkPlansByHandyWorker(final HandyWorker handyWorker) {
		return this.applicationRepository.getWorkPlansByHandyWorkerId(handyWorker.getId());
	}

	public Double getPendingRatio() {

		return this.applicationRepository.getPendingRatio();
	}

	public Double getAcceptedRatio() {

		return this.applicationRepository.getAcceptedRatio();
	}

	public Double getRejectedRatio() {
		return this.applicationRepository.getRejectedRatio();
	}

	public Double getExpiredRatio() {

		return this.applicationRepository.getExpiredRatio();
	}

	public Double[] getOfferedPriceStatistics() {

		return this.applicationRepository.getOfferedPriceStatistics();
	}

}
