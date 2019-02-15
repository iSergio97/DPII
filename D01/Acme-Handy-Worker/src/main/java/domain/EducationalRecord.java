
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EducationalRecord extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String		diplomaTitle;
	private Date		startingTime;
	private Date		endingTime;
	private String		institution;
	private String		attachment;
	private String		comments;

	// Relationships ----------------------------------------------------------

	private Curriculum	curriculum;


	// Field access methods ---------------------------------------------------

	@NotBlank
	public String getDiplomaTitle() {
		return this.diplomaTitle;
	}

	public void setDiplomaTitle(final String diplomaTitle) {
		this.diplomaTitle = diplomaTitle;
	}

	@Past
	@Temporal(TemporalType.DATE)
	public Date getStartingTime() {
		return this.startingTime;
	}

	public void setStartingTime(final Date startingTime) {
		this.startingTime = startingTime;
	}

	@Temporal(TemporalType.DATE)
	public Date getEndingTime() {
		return this.endingTime;
	}

	public void setEndingTime(final Date endingTime) {
		this.endingTime = endingTime;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@URL
	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final String attachment) {
		this.attachment = attachment;
	}

	public String getComments() {
		return this.comments;
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
