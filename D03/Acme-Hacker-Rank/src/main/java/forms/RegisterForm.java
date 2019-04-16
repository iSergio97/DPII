
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class RegisterForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	// UserAccount
	private int		id;
	// Actor
	private String	name;
	private String	surnames;
	private String	vat;
	private String	email;
	private String	photo;
	private String	phoneNumber;
	private String	address;
	private String	username;
	private String	password;
	// Register itself
	private String	confirmPassword;
	// CreditCard
	private String	holder;
	private String	brand;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private Integer	CVV;


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

	@NotBlank
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	@NotBlank
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(final String brand) {
		this.brand = brand;
	}

	@Pattern(regexp = "^([\\d]){16}$")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public Integer getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@Range(min = 100, max = 999)
	public Integer getCVV() {
		return this.CVV;
	}

	public void setCVV(final Integer cVV) {
		this.CVV = cVV;
	}

}
