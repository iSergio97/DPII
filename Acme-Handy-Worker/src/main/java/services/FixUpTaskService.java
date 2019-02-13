
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import utilities.RandomTickerGenerator;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FixUpTaskRepository			fixUpTaskRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private WarrantyService				warrantyService;

	@Autowired
	private WorkPlanService				workPlanService;

	@Autowired
	private FixUpTaskCategoryService	fixUpTaskCategoryService;


	// Constructors -----------------------------------------------------------

	public FixUpTaskService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public FixUpTask create() {
		final FixUpTask fixUpTask = new FixUpTask();
		fixUpTask.setTicker(RandomTickerGenerator.generateTicker());
		fixUpTask.setMoment(new Date());
		fixUpTask.setDescription("");
		fixUpTask.setAddress("");
		fixUpTask.setMaximumPrice(null);
		fixUpTask.setTimeLimit(null);
		fixUpTask.setCustomer(this.customerService.findPrincipal());
		fixUpTask.setApplications(new ArrayList<Application>());
		// Dentro de () llamar al WarrantyServices y usar el método create
		fixUpTask.setWarranty(this.warrantyService.create());
		// Lo mismo pero con Workplan
		fixUpTask.setWorkPlan(this.workPlanService.create());
		fixUpTask.setComplaints(new ArrayList<Complaint>());
		fixUpTask.setFixUpTaskCategory(this.fixUpTaskCategoryService.create());
		// Guardar en el repositorio.
		return fixUpTask;
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.isTrue(fixUpTask != null);
		return this.fixUpTaskRepository.save(fixUpTask);
	}

	public Iterable<FixUpTask> save(final Iterable<FixUpTask> fixUpTasks) {
		Assert.isTrue(fixUpTasks != null);
		return this.fixUpTaskRepository.save(fixUpTasks);
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.isTrue(fixUpTask != null);
		this.fixUpTaskRepository.delete(fixUpTask);
	}

	public void delete(final Iterable<FixUpTask> fixUpTasks) {
		Assert.isTrue(fixUpTasks != null);
		this.fixUpTaskRepository.delete(fixUpTasks);
	}

	public FixUpTask findById(final int id) {
		return this.fixUpTaskRepository.findOne(id);
	}

	public List<FixUpTask> findAll() {
		return this.fixUpTaskRepository.findAll();
	}

	// Other methods ----------------------------------------------------------

	public List<FixUpTask> findWithFinder(final Finder finder) {
		return this.fixUpTaskRepository.getFiltered(finder.getKeyword(), finder.getFixUpTaskCategory().getId(), finder.getWarranty().getId(), finder.getMinimumPrice(), finder.getMaximumPrice(), finder.getMinimumDate(), finder.getMaximumDate());
	}

	public Double getComplaintRatio() {
		return this.fixUpTaskRepository.getComplaintRatio();
	}

	public Double[] getApplicationStatistics() {
		return this.fixUpTaskRepository.getApplicationStatistics();
	}

	public Double[] getComplaintStatistics() {
		return this.fixUpTaskRepository.getComplaintStatistics();
	}

	public Double[] getMaximumPriceStatistics() {
		return this.fixUpTaskRepository.getMaximumPriceStatistics();
	}

	public List<Customer> getCustomersWhoPublishFixUpTasksWithWord(final String word) {
		return this.fixUpTaskRepository.getCustomersWhoPublishFixUpTasksWithWord(word);
	}

	public boolean anyApplicationAccepted(final FixUpTask f) {
		boolean result = false;
		final Collection<Application> applications = f.getApplications();
		for (final Application a : applications)
			if (a.getStatus().equals("ACCEPTED")) {
				result = true;
				break;
			}
		return result;
	}
}
