
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Note;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NoteRepository	noteRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;

	@Autowired
	private ReportService reportService;

	// Constructors -----------------------------------------------------------

	public NoteService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Note create() {
		Note note = new Note();
		UserAccount login = LoginService.getPrincipal();
		note.setActor(actorService.findById(login.getId()));
		note.setComment("");
		note.setAdditionalComments(new ArrayList<String>());
		note.setMoment(new Date());
		note.setReport(reportService.create());

		return note;
	}

	public Note save(final Note note) {
		Assert.isTrue(note != null);
		return this.noteRepository.save(note);
	}

	public Iterable<Note> save(final Iterable<Note> notes) {
		Assert.isTrue(notes != null);
		return this.noteRepository.save(notes);
	}

	public void delete(final Note note) {
		Assert.isTrue(note != null);
		this.noteRepository.delete(note);
	}

	public void delete(final Iterable<Note> notes) {
		Assert.isTrue(notes != null);
		this.noteRepository.delete(notes);
	}

	public Note findById(final int id) {
		return this.noteRepository.findOne(id);
	}

	public List<Note> findAll() {
		return this.noteRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------
}
