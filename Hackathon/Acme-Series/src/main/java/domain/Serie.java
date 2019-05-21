/*
 * Serie.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Serie extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String			title;
	private String			description;
	private String			banner;
	private Date			startDate;
	private Date			endDate;
	private String			status;
	private String			director;
	private String			cast;
	private boolean			draft;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Publisher		publisher;
	private List<Season>	seasons;


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

	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
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

	@Pattern(regexp = "^ON EMISSION|FINALIZED")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(final String director) {
		this.director = director;
	}

	public String getCast() {
		return this.cast;
	}

	public void setCast(final String cast) {
		this.cast = cast;
	}

	public boolean getDraft() {
		return this.draft;
	}

	public void setDraft(final boolean draft) {
		this.draft = draft;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@ManyToOne
	@Valid
	@NotNull
	public Publisher getPublisher() {
		return this.publisher;
	}

	public void setPublisher(final Publisher publisher) {
		this.publisher = publisher;
	}

	@OneToMany
	@Valid
	@NotNull
	@Cascade(CascadeType.ALL)
	public List<Season> getSeasons() {
		return this.seasons;
	}

	public void setSeasons(final List<Season> seasons) {
		this.seasons = seasons;
	}

}
