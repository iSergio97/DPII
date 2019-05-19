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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String				keyword;
	private Date				minDate;
	private Date				maxDate;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private User				user;
	private Collection<Serie>	series;
	private Genre				genre;


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
	public Date getMinDate() {
		return this.minDate;
	}

	public void setMinDate(final Date minDate) {
		this.minDate = minDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMaxDate() {
		return this.maxDate;
	}

	public void setMaxDate(final Date maxDate) {
		this.maxDate = maxDate;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@OneToOne
	@Valid
	@NotNull
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@ManyToOne
	@Valid
	@NotNull
	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(final Genre genre) {
		this.genre = genre;
	}

	@ManyToMany
	@Valid
	@NotNull
	public Collection<Serie> getSeries() {
		return this.series;
	}

	public void setSeries(final Collection<Serie> positions) {
		this.series = positions;
	}

}
