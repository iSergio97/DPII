/*
 * ApplicationForm.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

public class ApplicationForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	description;
	private int		serieId;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Range(min = 0)
	public int getSerieId() {
		return this.serieId;
	}

	public void setSerieId(final int serieId) {
		this.serieId = serieId;
	}

}
