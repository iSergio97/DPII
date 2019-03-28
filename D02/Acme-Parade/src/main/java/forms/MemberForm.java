
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class MemberForm {

	private String	name;
	private String	middleName;
	private String	surname;
	private String	photo;
	private String	email;
	private String	phoneNumber;
	private String	address;
	private String	username;
	private String	password;
	private String	confirmPassword;
	private int		id;


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
	// Este patr�n es cumplido por las direcciones de email de actor pero no los de administrador
	@Pattern(regexp = "^([a-zA-Z0-9 ]+<[a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)+>)|([a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)+)$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

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

	@NotBlank
	@NotNull
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@NotBlank
	@NotNull
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	@NotNull
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
