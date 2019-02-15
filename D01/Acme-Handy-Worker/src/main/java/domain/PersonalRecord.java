
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalRecord extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String		fullName;
	private String		photo;
	private String		email;
	private String		phoneNumber;
	private String		linkedIn;

	// Relationships ----------------------------------------------------------

	private Curriculum	curriculum;


	// Field access methods ---------------------------------------------------

	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@Pattern(regexp = "^((([a-zA-Z0-9])+ )+<([a-zA-Z0-9])+@(([a-zA-Z0-9])+(\\.([a-zA-Z0-9])+)+)?>)|(([a-zA-Z0-9])+@(([a-zA-Z0-9])+(\\.([a-zA-Z0-9])+)+)?)$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Pattern(regexp = "^(\\+\\d{1,3} (\\(\\d{1,3}\\) )?)?\\d{4,}$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@URL
	// @NotBlank
	public String getLinkedIn() {
		return this.linkedIn;
	}

	public void setLinkedIn(final String linkedIn) {
		this.linkedIn = linkedIn;
	}

	// Relationship access methods --------------------------------------------

	@Valid
	@OneToOne(optional = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
