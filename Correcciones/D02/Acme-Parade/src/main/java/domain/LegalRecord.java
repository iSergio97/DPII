
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class LegalRecord extends Record {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String	legalName;
	private double	VAT;
	private String	applicableLaws;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

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

}
