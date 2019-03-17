
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class LegalRecord extends DomainEntity {

	// Fields

	private String	title;
	private String	description;
	private String	legalName;
	private double	VAT;
	private String	applicableLaws;

	// Relationships

	private History	history;


	// Field access methods

	@NotNull
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@NotBlank
	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	@NotNull
	public double getVAT() {
		return this.VAT;
	}

	public void setVAT(final double VAT) {
		this.VAT = VAT;
	}

	@NotNull
	@NotBlank
	public String getApplicableLaws() {
		return this.applicableLaws;
	}

	public void setApplicableLaws(final String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	// Relationship access methods

	@ManyToOne(optional = true)
	@Valid
	public History getHistory() {
		return this.history;
	}

	public void setHistory(final History history) {
		this.history = history;
	}

}
