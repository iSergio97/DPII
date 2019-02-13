
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousRecord extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String		title;
	private String		attachment;
	private String		comments;

	// Relationships ----------------------------------------------------------

	private Curriculum	curriculum;


	// Field access methods ---------------------------------------------------

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@URL
	public String getAttachment() {
		return this.attachment;
	}

	public String getComments() {
		return this.comments;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setAttachment(final String attachment) {
		this.attachment = attachment;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	// Relationship access methods --------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
