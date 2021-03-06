
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Member extends Actor {

	// Fields -----------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private List<Enrolment>	enrolments;
	private List<Request>	requests;
	private Finder			finder;


	// Relationship access methods --------------------------------------------

	@OneToMany(mappedBy = "member")
	@Valid
	public List<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	public void setEnrolments(final List<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	@OneToMany(mappedBy = "member")
	@Valid
	public List<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final List<Request> requests) {
		this.requests = requests;
	}

	@OneToOne(optional = false)
	@Valid
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
