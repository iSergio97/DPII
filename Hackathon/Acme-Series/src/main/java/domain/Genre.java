/*
 * Genre.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyClass;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Genre extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private Map<String, String>	name;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotNull
	@ElementCollection(targetClass = String.class)
	@MapKeyClass(String.class)
	public Map<String, String> getName() {
		return this.name;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
	}

}
