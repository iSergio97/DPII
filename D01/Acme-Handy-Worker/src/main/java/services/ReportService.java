
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Complaint;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository	reportRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private NoteService			noteService;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;


	// Constructors -----------------------------------------------------------

	public ReportService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Report create() {
		final Report report = new Report();

		report.setMoment(new Date());
		report.setDescription("");
		report.setAttachments(new ArrayList<String>());
		report.setNotes(this.noteService.findAll());
		report.setComplaint(this.complaintService.create());
		report.setReferee(this.refereeService.create());

		return report;

	}
	public Report save(final Report report) {
		Assert.isTrue(report != null);
		return this.reportRepository.save(report);
	}

	public Iterable<Report> save(final Iterable<Report> reports) {
		Assert.isTrue(reports != null);
		return this.reportRepository.save(reports);
	}

	public void delete(final Report report) {
		Assert.isTrue(report != null);
		this.reportRepository.delete(report);
	}

	public void delete(final Iterable<Report> reports) {
		Assert.isTrue(reports != null);
		this.reportRepository.delete(reports);
	}

	public Report findById(final int id) {
		return this.reportRepository.findOne(id);
	}

	public List<Report> findAll() {
		return this.reportRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------

	public Double[] getNoteStatistics() {
		return this.reportRepository.getNoteStatistics();
	}

	public Complaint[] getComplaintsOfReferee(final Referee referee) {
		return this.reportRepository.getComplaintsOfReferee(referee);
	}

}
