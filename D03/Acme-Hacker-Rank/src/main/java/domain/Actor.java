/*
 * Actor.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String						name;
	private List<String>				surnames;
	private String						vat;
	private String						photo;
	private String						email;
	private String						phoneNumber;
	private String						address;
	private boolean						isSpammer;
	private boolean						isBanned;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private UserAccount					userAccount;
	private CreditCard					creditCard;
	private Collection<Message>			messagePool;
	private Collection<SocialProfile>	socialProfiles;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@ElementCollection
	public List<String> getSurnames() {
		return this.surnames;
	}

	public void setSurnames(final List<String> surnames) {
		this.surnames = surnames;
	}

	@NotNull
	// Matches 2 letters followed by between 5 and 15 alphanumeric characters
	@Pattern(regexp = "^[\\w]{2}[\\d\\w]{5,15}$")
	public String getVat() {
		return this.vat;
	}

	public void setVat(final String vat) {
		this.vat = vat;
	}

	@NotNull
	// Matches administrator email addresses
	@Pattern(regexp = "^([a-zA-Z0-9 ]+<[a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)?>)|([a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)?)$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	// Optional
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	// Optional
	@NotNull
	// Matches a phone number or an empty string
	@Pattern(regexp = "(^(\\+[1-9]\\d{0,2} (\\([1-9]\\d{0,2}\\) )?)?\\d{4,}$)|(^$)")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// Optional
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public boolean getIsSpammer() {
		return this.isSpammer;
	}

	public void setIsSpammer(final boolean isSpammer) {
		this.isSpammer = isSpammer;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final boolean isBanned) {
		this.isBanned = isBanned;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

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
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "recipient")
	public Collection<Message> getMessagePool() {
		return this.messagePool;
	}

	public void setMessagePool(final Collection<Message> messagePool) {
		this.messagePool = messagePool;
	}

	@Valid
	@NotNull
	@OneToMany
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

}
