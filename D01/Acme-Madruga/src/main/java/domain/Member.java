
package domain;

import javax.persistence.OneToMany;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Member extends Actor {

	// Fields -----------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	// Relationship access methods --------------------------------------------

	private List<Enrolment>	enrolment;
	private List<Request>	requests;
	private Finder			finder;

	@OneToMany
	public List<Enrolment> getEnrolment() {
		return this.enrolment;
	}

	public void setEnrolment(final List<Enrolment> enrolment) {
		this.enrolment = enrolment;
	}

	@OneToMany
	public List<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final List<Request> requests) {
		this.requests = requests;
	}

	@OneToOne
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}
}
