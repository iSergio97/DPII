/*
 * ApplicationForm.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import org.hibernate.validator.constraints.Range;

public class AuditForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private int		positionId;
	private String	text;
	private double	score;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Range(min = 0)
	public int getPositionId() {
		return this.positionId;
	}

	public void setPositionId(final int positionId) {
		this.positionId = positionId;
	}

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

}
