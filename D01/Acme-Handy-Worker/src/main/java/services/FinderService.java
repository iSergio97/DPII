
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FinderRepository			finderRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private WarrantyService				warrantyService;

	@Autowired
	private FixUpTaskCategoryService	fixUpTaskCategoryService;


	// Constructors -----------------------------------------------------------

	public FinderService() {

		super();
	}

	// Methods ----------------------------------------------------------------

	public Finder create() {
		final Finder finder = new Finder();

		finder.setKeyword("");

		final Date dateMin = new Date();
		dateMin.setTime(dateMin.getMonth());
		finder.setMinimumDate(dateMin);

		Date dateMax;
		dateMax = new Date();
		dateMax.setYear(dateMax.getYear() + 3);
		finder.setMaximumDate(dateMax);

		finder.setFixUpTasks(this.fixUpTaskService.findAll());
		finder.setWarranty(this.warrantyService.create());
		finder.setMinimumPrice(0);
		finder.setMaximumPrice(100000);

		return finder;
	}

	public Finder save(final Finder finder) {
		Assert.isTrue(finder != null);
		return this.finderRepository.save(finder);
	}

	public Iterable<Finder> save(final Iterable<Finder> finders) {
		Assert.isTrue(finders != null);
		return this.finderRepository.save(finders);
	}

	public void delete(final Finder finder) {
		Assert.isTrue(finder != null);
		this.finderRepository.delete(finder);
	}

	public void delete(final Iterable<Finder> finders) {
		Assert.isTrue(finders != null);
		this.finderRepository.delete(finders);
	}

	public Finder findById(final int id) {
		return this.finderRepository.findOne(id);
	}

	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------

	//Este metodo devuelve el finder asociado a un id de un handy worker
	public Finder findByHandyWorkerId(final int id) {

		return this.finderRepository.findByHandyWorkerId(id);
	}

}
