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
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	private String				skillsRequired;
	private String				technologiesRequired;
	private double				salaryOffered;
	private Date				deadline;
	private boolean				isDraft;
	private String				status;
	private Date				submitMoment;
	private String				ticker;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Company				company;
	private Collection<Problem>	problems;


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

	@Valid
	@ManyToMany
	public Collection<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(final Collection<Problem> problems) {
		this.problems = problems;
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

	@Basic
	public boolean isDraft() {
		return this.isDraft;
	}

	public void setDraft(final boolean isDraft) {
		this.isDraft = isDraft;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getSubmitMoment() {
		return this.submitMoment;
	}

	public void setSubmitMoment(final Date submitMoment) {
		this.submitMoment = submitMoment;
	}

	@NotNull
	@NotBlank
	@Pattern(regexp = "^([A-Z]{4}-[0-9]{4})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

}
