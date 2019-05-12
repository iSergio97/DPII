
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import domain.Position;

public class SponsorshipForm {

	//////////////////////////////////////////////////////////////////
	//Fields

	private int			id;

	// CreditCard
	private String		holder;
	private String		brand;
	private String		number;
	private Integer		expirationMonth;
	private Integer		expirationYear;
	private Integer		CVV;

	private String		banner;
	private String		targetPage;

	private Position	position;


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

	@NotBlank
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

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@URL
	public String getTargetPage() {
		return this.targetPage;
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

}
