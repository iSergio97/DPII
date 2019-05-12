
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

public class FinderForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	keyword;
	private Date	deadline;
	private double	minimumSalary;
	private Date	maximumDeadline;


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
	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDeadline() {
		return this.deadline;
	}
	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}
	@DecimalMin("0.0")
	public double getMinimumSalary() {
		return this.minimumSalary;
	}
	public void setMinimumSalary(final double minimumSalary) {
		this.minimumSalary = minimumSalary;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMaximumDeadline() {
		return this.maximumDeadline;
	}
	public void setMaximumDeadline(final Date maximumDeadline) {
		this.maximumDeadline = maximumDeadline;
	}

}
