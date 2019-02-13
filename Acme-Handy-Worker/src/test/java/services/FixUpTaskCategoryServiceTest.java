
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
import domain.FixUpTaskCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskCategoryServiceTest extends AbstractTest {

	//-----------Service under test------------
	@Autowired
	private FixUpTaskCategoryService	futCategoryService;


	@Test
	public void test() {
		FixUpTaskCategory futCat, saved;
		Collection<FixUpTaskCategory> futCats;
		super.authenticate("admin1");

		//----------Create object--------------
		futCat = this.futCategoryService.create();

		//----------Check properties--------------
		Assert.isTrue(futCat.getName().equals(""));
		Assert.isTrue(futCat.getFixUpTasks().isEmpty());
		Assert.isTrue(futCat.getFixUpTaskCategoryChildren().isEmpty());
		Assert.isTrue(futCat.getFixUpTaskCategoryParent() != null);

		//---------Initialise object-------------
		futCat.setName("Roofing");
		futCat.setFixUpTaskCategoryParent(this.futCategoryService.findById(2487));

		//--------Save-------------------
		saved = this.futCategoryService.save(futCat);
		futCats = this.futCategoryService.findAll();
		Assert.isTrue(futCats.contains(saved));

	}

}
