
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class LegalRecordForm {

	private String	title;
	private String	description;
	private String	legalName;
	private Double	VAT;
	private String	applicableLaws;
	private Integer	id;


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
	public Double getVAT() {
		return this.VAT;
	}

	public void setVAT(final Double vAT) {
		this.VAT = vAT;
	}

	@NotNull
	@NotBlank
	public String getApplicableLaws() {
		return this.applicableLaws;
	}

	public void setApplicableLaws(final String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	@Range(min = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

}
