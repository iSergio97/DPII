
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskCategoryRepository;
import domain.FixUpTask;
import domain.FixUpTaskCategory;

@Service
@Transactional
public class FixUpTaskCategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FixUpTaskCategoryRepository	fixUpTaskCategoryRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public FixUpTaskCategoryService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public FixUpTaskCategory create() {
		final FixUpTaskCategory futCategory = new FixUpTaskCategory();

		futCategory.setName("");
		futCategory.setFixUpTaskCategoryChildren(new ArrayList<FixUpTaskCategory>());
		futCategory.setFixUpTaskCategoryParent(new FixUpTaskCategory());
		futCategory.setFixUpTasks(new ArrayList<FixUpTask>());
		return futCategory;
	}

	public FixUpTaskCategory save(final FixUpTaskCategory fixUpTaskCategory) {
		Assert.isTrue(fixUpTaskCategory != null);
		return this.fixUpTaskCategoryRepository.save(fixUpTaskCategory);
	}

	public Iterable<FixUpTaskCategory> save(final Iterable<FixUpTaskCategory> fixUpTaskCategories) {
		Assert.isTrue(fixUpTaskCategories != null);
		return this.fixUpTaskCategoryRepository.save(fixUpTaskCategories);
	}

	public void delete(final FixUpTaskCategory fixUpTaskCategory) {
		Assert.isTrue(fixUpTaskCategory != null);
		this.fixUpTaskCategoryRepository.delete(fixUpTaskCategory);
	}

	public void delete(final Iterable<FixUpTaskCategory> fixUpTaskCategories) {
		Assert.isTrue(fixUpTaskCategories != null);
		this.fixUpTaskCategoryRepository.delete(fixUpTaskCategories);
	}

	public FixUpTaskCategory findById(final int id) {
		return this.fixUpTaskCategoryRepository.findOne(id);
	}

	public List<FixUpTaskCategory> findAll() {
		return this.fixUpTaskCategoryRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------

}
