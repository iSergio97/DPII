/*
 * Finder.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String					keyword;
	private Date					deadline;
	private double					minimumSalary;
	private Date					maximumDeadline;
	private Date					moment;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Hacker					hacker;
	private Collection<Position>	positions;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotNull
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

	@Range(min = 0)
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

	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@OneToOne(optional = true)
	@Valid
	public Hacker getHacker() {
		return this.hacker;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}

	@Valid
	@OneToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

}
