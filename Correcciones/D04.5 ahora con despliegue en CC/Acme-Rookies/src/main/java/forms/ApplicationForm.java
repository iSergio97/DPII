/*
 * ApplicationForm.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class ApplicationForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	explanations;
	private String	codeLink;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getExplanations() {
		return this.explanations;
	}

	public void setExplanations(final String explanations) {
		this.explanations = explanations;
	}

	@URL
	public String getCodeLink() {
		return this.codeLink;
	}

	public void setCodeLink(final String codeLink) {
		this.codeLink = codeLink;
	}

}
