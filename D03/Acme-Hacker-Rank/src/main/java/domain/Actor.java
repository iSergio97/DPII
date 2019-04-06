/*
 * Actor.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Actor extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String		name;
	private String		surnames;
	private int			vat;
	private CreditCard	creditCard;
	private String		photo;
	private String		email;
	private String		phoneNumber;
	private String		address;
	private boolean		isFlagged;
	private boolean		isBanned;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private UserAccount	userAccount;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@Range(min = 0)
	public int getVat() {
		return this.vat;
	}

	public void setVat(final int vat) {
		this.vat = vat;
	}

	@NotBlank
	// Matches administrator email addresses
	@Pattern(regexp = "^([a-zA-Z0-9 ]+<[a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)?>)|([a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)?)$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	// Matches a phone number or an empty string
	@Pattern(regexp = "(^(\\+[1-9]\\d{0,2} (\\([1-9]\\d{0,2}\\) )?)?\\d{4,}$)|(^$)")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public boolean isFlagged() {
		return this.isFlagged;
	}

	public void setFlagged(final boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public boolean isBanned() {
		return this.isBanned;
	}

	public void setBanned(final boolean isBanned) {
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

}
