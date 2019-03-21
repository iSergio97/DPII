/*
 * @author Sergio Garrido Domï¿½nguez
 */
/*
 * Comments:
 * Posible cambio de tipo de int a double (o Double) en polarityScore
 */

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
	private boolean						isSuspicious;
	private Integer						polarityScore;
	private boolean						isBanned;

	// Relationships ----------------------------------------------------------

	private UserAccount					userAccount;
	private Collection<Message>			messagesSent;
	private Collection<Message>			messagesReceived;
	private Collection<SocialProfile>	socialProfiles;


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
	// @Pattern(regexp = "^([a-zA-Z0-9 ]+<[a-zA-Z0-9]+@([a-zA-Z0-9]+(\\\\.[a-zA-Z0-9]+)*)?>)|([a-zA-Z0-9]+@([a-zA-Z0-9]+(\\\\.[a-zA-Z0-9]+)*)?)$")
	// TODO: Patrón
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	//Optional
	@Pattern(regexp = "^(\\+[1-9]\\d{0,2} (\\([1-9]\\d{0,2}\\) )?)?\\d{4,}$")
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
	@NotNull
	// TODO: What does NotNull here?
	@OneToMany(mappedBy = "actor")
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

}
