
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// --------Service under test----------

	@Autowired
	private FinderService	finderService;


	@Test
	public void test() {
		Finder finder;
		final Finder saved;
		Collection<Finder> finders;
		super.authenticate("handyWorker1");
		//Create object
		finder = this.finderService.create();

		//Check properties
		Assert.isTrue(finder.getKeyword().equals(""));
		Assert.isNull(finder.getWarranty());
		Assert.isTrue(finder.getFixUpTasks().isEmpty());
		Assert.isNull(finder.getFixUpTaskCategory());

		//Falta inicializarlo

		saved = this.finderService.save(finder);
		finders = this.finderService.findAll();
		Assert.isTrue(finders.contains(saved));

	}
}
