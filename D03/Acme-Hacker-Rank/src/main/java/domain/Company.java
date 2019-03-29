/*
 * Company.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String					commercialName;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Collection<Position>	positions;
	private Collection<Problem>		problems;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getCommercialName() {
		return this.commercialName;
	}

	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@OneToMany(mappedBy = "company")
	@Valid
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

	@OneToMany(mappedBy = "company")
	@Valid
	public Collection<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(final Collection<Problem> problems) {
		this.problems = problems;
	}

}
