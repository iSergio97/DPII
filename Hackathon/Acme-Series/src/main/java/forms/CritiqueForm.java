/*
 * CritiqueForm.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

public class CritiqueForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	text;
	private double	score;
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
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Range(min = 0, max = 10)
	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}

	@Range(min = 0)
	public int getSerieId() {
		return this.serieId;
	}

	public void setSerieId(final int serieId) {
		this.serieId = serieId;
	}

}
