
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Actor extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String						name;
	private String						middleName;
	private String						surname;
	private String						photo;
	private String						email;
	private String						phoneNumber;
	private String						address;
	private boolean						isBanned;

	// Relationships ----------------------------------------------------------

	private UserAccount					userAccount;
	private Collection<MessageBox>		messageBoxes;
	private Collection<SocialProfile>	socialProfiles;
	private Collection<Endorsement>		endorsedBy;
	private Collection<Endorsement>		endorses;
	private Collection<Message>			messagesSent;
	private Collection<Message>			messagesReceived;
	private Collection<Note>			notes;


	// Field access methods ---------------------------------------------------

	@NotNull
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@NotNull
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotNull
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotNull
	@NotBlank
	@Pattern(regexp = "^((([a-zA-Z0-9])+ )+<([a-zA-Z0-9])+@(([a-zA-Z0-9])+(\\.([a-zA-Z0-9])+)+)?>)|(([a-zA-Z0-9])+@(([a-zA-Z0-9])+(\\.([a-zA-Z0-9])+)+)?)$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotNull
	@Pattern(regexp = "^(\\+\\d{1,3} (\\(\\d{1,3}\\) )?)?\\d{4,}$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotNull
	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final boolean isBanned) {
		this.isBanned = isBanned;
	}

	// Relationship access methods --------------------------------------------

	@NotNull
	@Valid
	@OneToOne
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@NotNull
	@NotEmpty
	@OneToMany(mappedBy = "actor")
	public Collection<MessageBox> getMessageBoxes() {
		return this.messageBoxes;
	}

	public void setMessageBoxes(final Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "actor")
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfile) {
		this.socialProfiles = socialProfile;
	}

	@Valid
	@OneToMany(mappedBy = "endorser")
	public Collection<Endorsement> getEndorsedBy() {
		return this.endorsedBy;
	}

	public void setEndorsedBy(final Collection<Endorsement> endorsedBy) {
		this.endorsedBy = endorsedBy;
	}

	@Valid
	@OneToMany(mappedBy = "endorsed")
	public Collection<Endorsement> getEndorses() {
		return this.endorses;
	}

	public void setEndorses(final Collection<Endorsement> endorses) {
		this.endorses = endorses;
	}

	@Valid
	@OneToMany(mappedBy = "sender")
	public Collection<Message> getMessagesSent() {
		return this.messagesSent;
	}

	public void setMessagesSent(final Collection<Message> messagesSent) {
		this.messagesSent = messagesSent;
	}

	@Valid
	@ManyToMany(mappedBy = "recipients")
	public Collection<Message> getMessagesReceived() {
		return this.messagesReceived;
	}

	public void setMessagesReceived(final Collection<Message> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

	@Valid
	@OneToMany(mappedBy = "actor")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

}
