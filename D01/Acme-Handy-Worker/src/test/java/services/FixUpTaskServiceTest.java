
package services;

import java.sql.Date;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	@Autowired
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private WarrantyService				warrantyService;

	@Autowired
	private FixUpTaskCategoryService	futCService;


	@Test
	public void test() {
		FixUpTask fut, saved;
		Collection<FixUpTask> futs;

		super.authenticate("customer3");

		fut = this.fixUpTaskService.create();
		Assert.isTrue(!fut.getTicker().equals(""));
		Assert.isTrue(fut.getMoment() != null);
		Assert.isTrue(fut.getDescription().equals(""));
		Assert.isTrue(fut.getAddress().equals(""));
		Assert.isNull(fut.getMaximumPrice());
		Assert.isNull(fut.getTimeLimit());
		Assert.isTrue(fut.getCustomer().getUserAccount().getUsername().equals("customer3"));
		Assert.isTrue(fut.getApplications().isEmpty());
		Assert.isTrue(fut.getWarranty() != null);
		Assert.isTrue(fut.getWorkPlan() == null);
		Assert.isTrue(fut.getFixUpTaskCategory() != null);
		Assert.isTrue(fut.getComplaints().isEmpty());

		//Falta inicializarlo
		fut.setDescription("Descripción de prueba");
		fut.setAddress("Dirección de prueba");
		fut.setMaximumPrice(500);
		fut.setTimeLimit(Date.valueOf("2020-06-06"));
		fut.setFixUpTaskCategory(this.futCService.findById(2493));
		fut.setWarranty(this.warrantyService.findById(2508));
		saved = this.fixUpTaskService.save(fut);
		futs = this.fixUpTaskService.findAll();
		Assert.isTrue(futs.contains(saved));

		super.authenticate(null);
	}

}
