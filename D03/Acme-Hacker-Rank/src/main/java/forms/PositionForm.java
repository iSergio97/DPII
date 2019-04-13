
package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Problem;

public class PositionForm {

	private String		title;
	private String		description;
	private String		profile;
	private String		skills;
	private String		technologies;
	private double		salary;
	private Date		deadline;
	private int			id;
	Collection<Problem>	problems;


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

	@Valid
	@NotNull
	public Collection<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(final Collection<Problem> problems) {
		this.problems = problems;
	}

}
