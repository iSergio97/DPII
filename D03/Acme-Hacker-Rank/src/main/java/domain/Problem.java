/*
 * Problem.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Problem extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String				title;
	private String				statement;
	private String				hint;
	private String				attachments;
	private boolean				isDraft;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Company	company;


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
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public String getHint() {
		return this.hint;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	@NotEmpty
	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public boolean isDraft() {
		return this.isDraft;
	}

	public void setDraft(final boolean isDraft) {
		this.isDraft = isDraft;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@ManyToOne
	@Valid
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

}
