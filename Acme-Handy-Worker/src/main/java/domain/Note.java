
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private Date				moment;
	private String				comment;
	private Collection<String>	additionalComments;

	// Relationships ----------------------------------------------------------

	private Report				report;
	private Actor				actor;


	// Field access methods ---------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@NotBlank
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@ElementCollection
	public Collection<String> getAdditionalComments() {
		return this.additionalComments;
	}

	public void setAdditionalComments(final Collection<String> additionalComments) {
		this.additionalComments = additionalComments;
	}

	// Relationship access methods --------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@Valid
	@ManyToOne(optional = false)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

}
