/*
 * Season.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Season extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int				number;
	private Date			startDate;
	private Date			endDate;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private List<Chapter>	chapters;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@Range(min = 0)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@OneToMany
	@Valid
	@NotNull
	@Cascade(CascadeType.ALL)
	public List<Chapter> getChapters() {
		return this.chapters;
	}

	public void setChapters(final List<Chapter> chapters) {
		this.chapters = chapters;
	}

}
