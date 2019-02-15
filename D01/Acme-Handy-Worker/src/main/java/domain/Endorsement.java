
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private Date				date;
	private Collection<String>	comment;

	// Relationships ----------------------------------------------------------

	private Actor				endorsed;
	private Actor				endorser;


	// Field access methods ---------------------------------------------------

	@NotNull
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}
	@ElementCollection
	public Collection<String> getComment() {
		return this.comment;
	}

	public void setComment(final Collection<String> comment) {
		this.comment = comment;
	}

	// Relationship access methods --------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getEndorsed() {
		return this.endorsed;
	}

	public void setEndorsed(final Actor endorsed) {
		this.endorsed = endorsed;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getEndorser() {
		return this.endorser;
	}

	public void setEndorser(final Actor endorser) {
		this.endorser = endorser;
	}

}
