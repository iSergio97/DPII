/*
 * Hacker.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Hacker extends Actor {

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Curriculum				curriculum;
	private Collection<Application>	applications;


	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "hacker")
	@Valid
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	@OneToMany(mappedBy = "hacker")
	@Valid
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final List<Application> applications) {
		this.applications = applications;
	}

}
