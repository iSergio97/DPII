
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;

@Service
@Transactional
public class SectionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SectionRepository	sectionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private TutorialService		tutorialService;


	// Constructors -----------------------------------------------------------

	public SectionService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Section create() {
		final Section s = new Section();

		s.setTitle("");
		s.setText("");
		s.setPictures(new ArrayList<String>());

		s.setTutorial(this.tutorialService.create());

		return s;
	}
	public Section save(final Section section) {
		Assert.isTrue(section != null);
		return this.sectionRepository.save(section);
	}

	public Iterable<Section> save(final Iterable<Section> sections) {
		Assert.isTrue(sections != null);
		return this.sectionRepository.save(sections);
	}

	public void delete(final Section section) {
		Assert.isTrue(section != null);
		this.sectionRepository.delete(section);
	}

	public void delete(final Iterable<Section> section) {
		Assert.isTrue(section != null);
		this.sectionRepository.delete(section);
	}

	public Section findById(final int id) {
		return this.sectionRepository.findOne(id);
	}

	public List<Section> findAll() {
		return this.sectionRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------
}
