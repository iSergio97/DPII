
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Actor {

	// Fields -----------------------------------------------------------------

	private String					make;

	// Relationships ----------------------------------------------------------

	private Collection<Application>	applications;
	private Finder					finder;
	private Curriculum				curriculum;
	private Collection<Tutorial>	tutorials;


	// Field access methods ---------------------------------------------------

	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	// Relationship access methods --------------------------------------------

	@OneToMany(mappedBy = "handyWorker")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@OneToOne(mappedBy = "handyWorker", optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@Valid
	@OneToOne(mappedBy = "handyWorker")
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	@OneToMany(mappedBy = "handyWorker")
	public Collection<Tutorial> getTutorials() {
		return this.tutorials;
	}

	public void setTutorials(final Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

}
