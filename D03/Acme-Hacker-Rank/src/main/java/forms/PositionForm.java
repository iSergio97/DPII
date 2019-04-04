
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
	private String	profileRequired;
	private String	skillsRequired;
	private String	technologiesRequired;
	private double	salaryOffered;
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
	public String getProfileRequired() {
		return this.profileRequired;
	}

	public void setProfileRequired(final String profileRequired) {
		this.profileRequired = profileRequired;
	}

	@NotNull
	public String getSkillsRequired() {
		return this.skillsRequired;
	}

	public void setSkillsRequired(final String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	@NotNull
	public String getTechnologiesRequired() {
		return this.technologiesRequired;
	}

	public void setTechnologiesRequired(final String technologiesRequired) {
		this.technologiesRequired = technologiesRequired;
	}

	@NotBlank
	public double getSalaryOffered() {
		return this.salaryOffered;
	}

	public void setSalaryOffered(final double salaryOffered) {
		this.salaryOffered = salaryOffered;
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
