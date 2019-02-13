
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
import domain.Complaint;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private FixUpTaskService	futService;


	@Test
	public void createTest() {
		Complaint complaint;

		super.authenticate("customer2");
		complaint = this.complaintService.create();

		Assert.isTrue(!complaint.getTicker().equals(""));
		Assert.isTrue(complaint.getDescription().equals(""));
		Assert.isTrue(complaint.getFixUpTask() != null);
		Assert.isTrue(complaint.getMoment() != null);
		Assert.isTrue(complaint.getReports().isEmpty());
		Assert.isTrue(complaint.getAttachments().isEmpty());

		super.unauthenticate();
	}

	@Test
	public void saveTest() {
		Complaint complaint, saved;
		Collection<Complaint> complaints;
		Customer c;

		super.authenticate("customer2");
		complaint = this.complaintService.create();
		c = this.customerService.findPrincipal();

		complaint.setDescription("Descripción de prueba");
		complaint.setFixUpTask(this.futService.findById(2594));

		saved = this.complaintService.save(complaint);
		complaints = this.complaintService.findAll();
		Assert.isTrue(complaints.contains(saved));
		//Assert.isTrue(this.complaintService.getComplaints(c).contains(saved));

		super.unauthenticate();
	}

}
