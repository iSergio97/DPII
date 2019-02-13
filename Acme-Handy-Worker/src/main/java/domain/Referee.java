
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Referee extends Actor {

	// Relationships ----------------------------------------------------------

	Collection<Report>	reports;


	// Relationship access methods --------------------------------------------
	@Valid
	@OneToMany(mappedBy = "referee")
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

}
