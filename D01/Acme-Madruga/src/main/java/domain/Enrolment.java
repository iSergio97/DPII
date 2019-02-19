
package domain;

import javax.persistence.ManyToOne;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Enrolment extends DomainEntity {

	// Fields -----------------------------------------------------------------
	private Date		moment;
	private String		position;

	// Relationships ----------------------------------------------------------
	private Member		member;
	private Brotherhood	brotherhood;


	// Field access methods ---------------------------------------------------

	@NotBlank
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@NotNull
	public String getPosition() {
		return this.position;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	// Relationship access methods --------------------------------------------
	@ManyToOne
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
	@ManyToOne
	public Member getMember() {
		return this.member;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

}
