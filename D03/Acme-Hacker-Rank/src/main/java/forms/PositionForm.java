
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class PositionForm {

	private String	title;
	private String	description;
	private String	profile;
	private String	skills;
	private String	technologies;
	private double	salary;
	private Date	deadline;
	private int		id;
	private String	status;
	private Date	submitMoment;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getProfile() {
		return this.profile;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}

	@NotNull
	public String getSkills() {
		return this.skills;
	}

	public void setSkills(final String skills) {
		this.skills = skills;
	}

	@NotNull
	public String getTechnologies() {
		return this.technologies;
	}

	public void setTechnologies(final String technologies) {
		this.technologies = technologies;
	}

	@NotBlank
	public double getSalary() {
		return this.salary;
	}

	public void setSalary(final double salary) {
		this.salary = salary;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotNull
	@NotBlank
	@Pattern(regexp = "^APPROVED|REJECTED|PENDING|CANCELLED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getSubmitMode() {
		return this.submitMoment;
	}

	public void setSubmitMode(final Date submitMode) {
		this.submitMoment = submitMode;
	}

}
