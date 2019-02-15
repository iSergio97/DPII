
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import utilities.RandomTickerGenerator;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ComplaintRepository	complaintRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ReportService		reportService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructors -----------------------------------------------------------

	public ComplaintService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Complaint create() {
		final Complaint complaint = new Complaint();

		complaint.setTicker(RandomTickerGenerator.generateTicker());
		complaint.setMoment(new Date());
		complaint.setDescription("");
		complaint.setAttachments(new ArrayList<String>());
		complaint.setReports(/* this.reportService.findAll() */new ArrayList<Report>());
		complaint.setFixUpTask(this.fixUpTaskService.create());

		return complaint;

	}
	public Complaint save(final Complaint complaint) {
		Assert.isTrue(complaint != null);
		return this.complaintRepository.save(complaint);
	}

	public Iterable<Complaint> save(final Iterable<Complaint> complaints) {
		Assert.isTrue(complaints != null);
		return this.complaintRepository.save(complaints);
	}

	public void delete(final Complaint complaint) {
		Assert.isTrue(complaint != null);
		this.complaintRepository.delete(complaint);
	}

	public void delete(final Iterable<Complaint> complaints) {
		Assert.isTrue(complaints != null);
		this.complaintRepository.delete(complaints);
	}

	public Complaint findById(final int id) {
		return this.complaintRepository.findOne(id);
	}

	public List<Complaint> findAll() {
		return this.complaintRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------

	public Collection<Complaint> getUnassignedComplaints() {
		return this.complaintRepository.getUnassignedComplaints();
	}

	public Collection<Complaint> getComplaints(final Customer c) {
		return this.complaintRepository.getComplaints(c);
	}

	public Complaint[] getComplaints(final Referee referee) {
		return this.reportService.getComplaintsOfReferee(referee);
	}

	public Collection<Complaint> getComplaints(final Customer c, final FixUpTask fut) {
		return this.complaintRepository.getComplaints(c, fut);
	}

	public List<Customer> getCustomersWhoPublishComplaintsWithWord(final String word) {
		return this.complaintRepository.getCustomersWhoPublishComplaintsWithWord(word);
	}

}
