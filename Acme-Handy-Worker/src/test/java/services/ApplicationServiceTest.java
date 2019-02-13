
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Application;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixUpTaskService	futService;

	@Autowired
	private HandyWorkerService	hwService;


	@Test
	public void createTest() {
		super.authenticate("handyWorker2");
		final Application a = this.applicationService.create();

		Assert.isTrue(a.getStatus().equals(""));
		Assert.isTrue(a.getFixUpTask() != null);
		final HandyWorker h = this.hwService.findById(2483);
		Assert.isTrue(a.getHandyWorker().equals(h));
		Assert.isTrue(a.getMoment() != null);
		Assert.isNull(a.getOfferedPrice());
		Assert.isNull(a.getComments());

		super.unauthenticate();
	}

	@Test
	public void saveTest() {
		Application a, saved;
		Collection<Application> applications;

		super.authenticate("handyWorker2");
		final HandyWorker h = this.hwService.findById(LoginService.getPrincipal().getId());

		a = this.applicationService.create();

		a.setFixUpTask(this.futService.findById(2593));
		a.setOfferedPrice(300);
		a.setStatus("PENDING");
		a.setComments(new ArrayList<String>());

		saved = this.applicationService.save(a);
		applications = this.applicationService.findAll();
		Assert.isTrue(applications.contains(saved));
		//Assert.isTrue(this.applicationService.findByHandyWorker(h).contains(saved));

		super.unauthenticate();
	}

}
