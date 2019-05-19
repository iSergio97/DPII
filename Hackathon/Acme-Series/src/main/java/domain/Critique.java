/*
 * Critique.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Critique extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private Date	moment;
	private String	text;
	private double	score;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Critic	critic;
	private Serie	serie;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
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

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@Valid
	@ManyToOne(optional = false)
	public Critic getCritic() {
		return this.critic;
	}

	public void setCritic(final Critic critic) {
		this.critic = critic;
	}

	@Valid
	@ManyToOne(optional = false)
	public Serie getSerie() {
		return this.serie;
	}

	public void setSerie(final Serie serie) {
		this.serie = serie;
	}

}
