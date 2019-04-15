/*
 * Curriculum.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Hacker							hacker;
	private PersonalData					personalData;
	private Collection<PositionData>		positionData;
	private Collection<EducationData>		educationData;
	private Collection<MiscellaneousData>	miscellaneousData;


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

	@OneToOne(cascade = CascadeType.ALL)
	@Valid
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	public Collection<PositionData> getPositionData() {
		return this.positionData;
	}

	public void setPositionData(final Collection<PositionData> positionData) {
		this.positionData = positionData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	public Collection<EducationData> getEducationData() {
		return this.educationData;
	}

	public void setEducationData(final Collection<EducationData> educationData) {
		this.educationData = educationData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	public Collection<MiscellaneousData> getMiscellaneousData() {
		return this.miscellaneousData;
	}

	public void setMiscellaneousData(final Collection<MiscellaneousData> miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}

}
