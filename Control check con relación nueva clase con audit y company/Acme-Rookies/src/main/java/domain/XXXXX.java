
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class XXXXX extends DomainEntity {

	private String	ticker;
	private Date	publicationMoment;
	private String	body;
	private String	picture;
	private Audit	audit;
	private Boolean	draftMode;
	private Company	company;


	//query para sacar la company del audit a través de una position
	//select a.position.company from Audit a where a.id =
	//Adaptada a XXXXX
	//select x.audit.position.company from XXXXX x where x.id =
	//Añadir patrón
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	//Optional
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@ManyToOne
	@Valid
	public Audit getAudit() {
		return this.audit;
	}

	public void setAudit(final Audit audit) {
		this.audit = audit;
	}

	public Boolean getDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final Boolean draftMode) {
		this.draftMode = draftMode;
	}

	@Valid
	@ManyToOne
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

}
