/*
 * Position.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String				title;
	private String				description;
	private String				profileRequired;
	private Collection<String>	skillsRequired;
	private Collection<String>	technologiesRequired;
	private double				salaryOffered;
	private Date				deadline;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Company				company;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

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
	@ElementCollection
	public Collection<String> getSkillsRequired() {
		return this.skillsRequired;
	}

	public void setSkillsRequired(final Collection<String> skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getTechnologiesRequired() {
		return this.technologiesRequired;
	}

	public void setTechnologiesRequired(final Collection<String> technologiesRequired) {
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

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@ManyToOne
	@Valid
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

}
