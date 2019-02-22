
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	// Fields -----------------------------------------------------------------

	private String					title;
	private Date					establishmentDate;
	private List<String>			pictures;

	// Relationships ----------------------------------------------------------

	private Collection<Procession>	processions;
	private Collection<AcmeFloat>	acmeFloats;
	private Area					area;
	private Collection<Enrolment>	enrolments;


	@OneToMany
	public Collection<Exit> getExit() {
		return this.exit;
	}

	public void setExit(final List<Exit> exit) {
		this.exit = exit;
	}


	private Collection<Exit>	exit;


	// Field access methods ---------------------------------------------------

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	//@URL
	@ElementCollection
	public List<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final List<String> pictures) {
		this.pictures = pictures;
	}

	// Relationship access methods --------------------------------------------

	@OneToMany(mappedBy = "brotherhood")
	public Collection<Procession> getProcessions() {
		return this.processions;
	}

	public void setProcessions(final Collection<Procession> processions) {
		this.processions = processions;
	}

	@OneToMany
	public Collection<AcmeFloat> getAcmeFloats() {
		return this.acmeFloats;
	}

	public void setAcmeFloats(final List<AcmeFloat> acmeFloats) {
		this.acmeFloats = acmeFloats;
	}

	@ManyToOne(optional = false)
	public Area getArea() {
		return this.area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

	@OneToMany(mappedBy = "brotherhood")
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

}
