
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import domain.Procession;

public class RequestForm {

	private String		status;
	private Integer		hLine;
	private Integer		vLine;
	private String		reason;
	private Procession	procession;
	private int			id;


	@NotNull
	@NotBlank
	@Pattern(regexp = "^APPROVED|REJECTED|PENDING$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Range(min = 1)
	public Integer getHLine() {
		return this.hLine;
	}

	public void setHLine(final Integer hLine) {
		this.hLine = hLine;
	}

	@Range(min = 1)
	public Integer getVLine() {
		return this.vLine;
	}

	public void setVLine(final Integer vLine) {
		this.vLine = vLine;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotNull
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}

}
