/*
 * @author Sergio Garrido Dom�nguez
 */
/*
 * Comments:
 * Posible cambio de tipo de int a double (o Double) en polarityScore
 */

package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;
import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Actor extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String		name;
	private String		middleName;
	private String		surname;
	private String		photo;
	private String		email;
	private String		phoneNumber;
	private String		address;
	private int			flag;
	private int			polarityScore;
	private boolean		isBanned;

	// Relationships ----------------------------------------------------------

	private UserAccount	userAccount;


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

	//Falta a�adir patr�n de email
	@NotBlank
	@NotNull
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	/*
	Optional
	Falta a�adir patr�n tel�fono con prefijo
	 */
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

	@NotNull
	@NotBlank
	public int getFlag() {
		return this.flag;
	}

	public void setFlag(final int flag) {
		this.flag = flag;
	}

	@NotNull
	@NotBlank
	public int getPolarityScore() {
		return this.polarityScore;
	}

	public void setPolarityScore(final int polarityScore) {
		this.polarityScore = polarityScore;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final boolean isBanned) {
		this.isBanned = isBanned;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
}