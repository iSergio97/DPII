/*
 * @author Sergio Garrido Dom�nguez
 */
/*
 * Comments:
 * Posible cambio de tipo de int a double (o Double) en polarityScore
 */

package domain;

import java.util.List;

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

	private String				name;
	private String				middleName;
	private String				surname;
	private String				photo;
	private String				email;
	private String				phoneNumber;
	private String				address;
	private boolean				isSuspicious;
	private Integer				polarityScore;
	private boolean				isBanned;

	// Relationships ----------------------------------------------------------

	private UserAccount			userAccount;
	private List<MessageBox>	messageBoxes;
	private List<Message>		messagesSent;
	private List<Message>		messagesReceived;
	private List<SocialProfile>	socialProfiles;


	// Field access methods ---------------------------------------------------

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	//Optional
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@NotBlank
	@NotNull
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	//Optional
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	@NotNull
	@Pattern(regexp = "^([a-zA-Z0-9 ]+<[a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)?>)|([a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)?)$")
	// TODO: Check
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	//Optional
	// @Pattern(regexp = "^(\\+\\d{1,3} (\\(\\d{1,3}\\) )?)?\\d{4,}$")
	// TODO
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	//Optional
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public void setIsSuspicious(final boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

	public Integer getPolarityScore() {
		return this.polarityScore;
	}

	public void setPolarityScore(final Integer polarityScore) {
		this.polarityScore = polarityScore;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final boolean isBanned) {
		this.isBanned = isBanned;
	}

	// Relationship access methods --------------------------------------------

	@Valid
	@NotNull
	@OneToOne(optional = false)
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
	public List<MessageBox> getMessageBoxes() {
		return this.messageBoxes;
	}

	public void setMessageBoxes(final List<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public List<Message> getMessagesSent() {
		return this.messagesSent;
	}

	public void setMessagesSent(final List<Message> messagesSent) {
		this.messagesSent = messagesSent;
	}

	@Valid
	@ManyToMany(mappedBy = "recipients")
	public List<Message> getMessagesReceived() {
		return this.messagesReceived;
	}

	public void setMessagesReceived(final List<Message> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

	@Valid
	@NotNull
	// TODO: What does NotNull here?
	@OneToMany(mappedBy = "actor")
	public List<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final List<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

}
