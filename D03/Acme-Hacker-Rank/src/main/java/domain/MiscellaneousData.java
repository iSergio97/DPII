/*
 * MiscellaneousData.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousData extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String				freeText;
	private Collection<String>	attachments;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getData() {
		return this.freeText;
	}

	public void setData(final String data) {
		this.freeText = data;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

}
