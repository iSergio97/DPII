
package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.AcmeFloat;

public class ParadeForm {

	// Fields -----------------------------------------------------------------

	private String					title;
	private String					description;
	private Date					moment;

	// Relationships ----------------------------------------------------------

	private Collection<AcmeFloat>	acmeFloats;


	// Field access methods ---------------------------------------------------

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	// Relationship access methods --------------------------------------------

	@NotNull
	public Collection<AcmeFloat> getAcmeFloats() {
		return this.acmeFloats;
	}

	public void setAcmeFloats(final Collection<AcmeFloat> acmeFloats) {
		this.acmeFloats = acmeFloats;
	}

}
