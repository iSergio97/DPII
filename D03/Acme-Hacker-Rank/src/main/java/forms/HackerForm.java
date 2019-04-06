/*
 * HackerForm.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class HackerForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	name;
	private String	surnames;
	private int		vat;
	private String	email;
	private String	photo;
	private String	phoneNumber;
	private String	address;
	private String	username;
	private String	password;
	private String	confirmPassword;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	// Matches a list of strings with "," as an element separator, or an empty string
	@Pattern(regexp = "(^([^,]+,)*[^,]+$)|(^$)")
	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@NotNull
	public int getVat() {
		return this.vat;
	}

	public void setVat(final int vat) {
		this.vat = vat;
	}

	@NotNull
	// Matches actor email addresses but not administrator email addresses
	@Pattern(regexp = "^([a-zA-Z0-9 ]+<[a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)+>)|([a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)+)$")
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
	@NotNull
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@NotBlank
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
