
package domain;

import java.util.List;

import javax.persistence.OneToOne;

public class Member extends Actor {

	// Fields -----------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	// Relationship access methods --------------------------------------------

	private List<Enrolment>	enrolment;
	private List<Request>	requests;
	private Finder			finder;


	public List<Enrolment> getEnrolment() {
		return this.enrolment;
	}

	public void setEnrolment(final List<Enrolment> enrolment) {
		this.enrolment = enrolment;
	}

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
