
package domain;

import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Procession extends DomainEntity {

	// Fields -----------------------------------------------------------------
	private String		title;
	private String		description;
	private Date		moment;
	private String		ticker;

	// Relationships ----------------------------------------------------------
	private Brotherhood	brotherhood;
	private List<AcmeFloat> acmeFloats;


	// Field access methods ---------------------------------------------------

	@NotBlank
	@NotNull
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@NotNull
	//Falta a�adir patron YYMMDD-XXXXX donde XXXXX son 5 letras en may�sculas
	public String getTicker() {
		return this.ticker;
	}

	// Relationship access methods --------------------------------------------

	//Falta a�adir anotaci�n XToY
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@ManyToMany
	public List<AcmeFloat> getAcmeFloats(){
		return this.acmeFloats;
	}

	public void setAcmeFloats(final List<AcmeFloat> acmeFloats) {
		this.acmeFloats = acmeFloats;
	}
}
