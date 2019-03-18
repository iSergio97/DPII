
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String	status;
	private Integer	hLine;
	private Integer	vLine;
	private String	reason;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Parade	parade;
	private Member	member;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotNull
	@NotBlank
	@Pattern(regexp = "^APPROVED|REJECTED|PENDING$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Range(min = 1)
	public Integer getHLine() {
		return this.hLine;
	}

	public void setHLine(final Integer hLine) {
		this.hLine = hLine;
	}

	@Range(min = 1)
	public Integer getVLine() {
		return this.vLine;
	}

	public void setVLine(final Integer vLine) {
		this.vLine = vLine;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@ManyToOne
	@Valid
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

	@Valid
	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

}
