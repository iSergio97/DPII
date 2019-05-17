
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	/////////////////////////////////////////////////////////////////
	//Fields

	private CreditCard	creditCard;
	private String		banner;
	private String		targetPage;

	/////////////////////////////////////////////////////////////////
	//Relationships

	private Provider	provider;
	private Position	position;


	/////////////////////////////////////////////////////////////////
	//Field access methods

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
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

	//////////////////////////////////////////////////////////////////
	//Relationship access methods

	@Valid
	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}
	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	@Valid
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}
	public void setPosition(final Position position) {
		this.position = position;
	}

}
