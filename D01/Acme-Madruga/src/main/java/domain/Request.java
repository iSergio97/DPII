
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String		status;
	private int			row;
	private int			column;
	private String		reason;
	// Relationships ----------------------------------------------------------

	private Procession	procession;


	// Field access methods ---------------------------------------------------

	@NotNull
	@NotBlank
	@Pattern(regexp = "^APPROVED|REJECTED|PENDING$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@NotBlank
	@NotNull
	@Range(min = 1)
	public int getRow() {
		return this.row;
	}

	@NotBlank
	@NotNull
	@Range(min = 1)
	public void setRow(final int row) {
		this.row = row;
	}

	public int getColumn() {
		return this.column;
	}

	public void setColumn(final int column) {
		this.column = column;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	// Relationship access methods --------------------------------------------

	//Falta anotación XToY
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}
}
