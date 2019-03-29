/*
 * Finder.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String	keyword;
	private double	minimumSalary;
	private Date	minimumDeadline;
	private Date	maximumDeadline;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotNull
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
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
	public Date getMinimumDeadline() {
		return this.minimumDeadline;
	}

	public void setMinimumDeadline(final Date minimumDeadline) {
		this.minimumDeadline = minimumDeadline;
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
