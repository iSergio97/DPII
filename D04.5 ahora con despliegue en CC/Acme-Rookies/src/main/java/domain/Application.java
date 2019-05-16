/*
 * Application.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private Date		moment;
	private Date		submitMoment;
	private String		explanations;
	private String		codeLink;
	private String		status;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Problem		problem;
	private Position	position;
	private Curriculum	curriculum;
	private Rookie		rookie;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSubmitMoment() {
		return this.submitMoment;
	}

	public void setSubmitMoment(final Date submitMoment) {
		this.submitMoment = submitMoment;
	}

	public String getExplanations() {
		return this.explanations;
	}

	public void setExplanations(final String explanations) {
		this.explanations = explanations;
	}

	@URL
	public String getCodeLink() {
		return this.codeLink;
	}

	public void setCodeLink(final String codeLink) {
		this.codeLink = codeLink;
	}

	@NotBlank
	@Pattern(regexp = "^ACCEPTED|REJECTED|PENDING|SUBMITTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@Valid
	@ManyToOne(optional = false)
	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(final Problem problem) {
		this.problem = problem;
	}

	@Valid
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@Valid
	@ManyToOne(optional = true)
	public Rookie getRookie() {
		return this.rookie;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	@Valid
	@OneToOne
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
