/*
 * Company.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String	commercialName;


	////////////////////////////////////////////////////////////////////////////////
	// Relationships

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

}
