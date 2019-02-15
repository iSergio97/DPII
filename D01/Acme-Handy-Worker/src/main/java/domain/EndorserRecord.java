
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String		endorserFullName;
	private String		endorserEmail;
	private String		endorserPhoneNumber;
	private String		endorserLinkedIn;
	private String		comments;

	// Relationships ----------------------------------------------------------

	private Curriculum	curriculum;


	// Field access methods ---------------------------------------------------

	@NotBlank
	public String getEndorserFullName() {
		return this.endorserFullName;
	}

	public void setEndorserFullName(final String endorserFullName) {
		this.endorserFullName = endorserFullName;
	}

	@Pattern(regexp = "^((([a-zA-Z0-9])+ )+<([a-zA-Z0-9])+@(([a-zA-Z0-9])+(\\.([a-zA-Z0-9])+)+)?>)|(([a-zA-Z0-9])+@(([a-zA-Z0-9])+(\\.([a-zA-Z0-9])+)+)?)$")
	public String getEndorserEmail() {
		return this.endorserEmail;
	}

	public void setEndorserEmail(final String endorserEmail) {
		this.endorserEmail = endorserEmail;
	}

	@Pattern(regexp = "^(\\+\\d{1,3} (\\(\\d{1,3}\\) )?)?\\d{4,}$")
	public String getEndorserPhoneNumber() {
		return this.endorserPhoneNumber;
	}

	public void setEndorserPhoneNumber(final String endorserPhoneNumber) {
		this.endorserPhoneNumber = endorserPhoneNumber;
	}

	@URL
	public String getEndorserLinkedIn() {
		return this.endorserLinkedIn;
	}

	public void setEndorserLinkedIn(final String endorserLinkedIn) {
		this.endorserLinkedIn = endorserLinkedIn;
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
