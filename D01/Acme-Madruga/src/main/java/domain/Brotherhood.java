
package domain;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	// Fields -----------------------------------------------------------------
	private String				title;
	private Date				establishmentDate;
	private List<String>		pictures;

	// Relationships ----------------------------------------------------------
	private List<Procession>	procession;
	private List<AcmeFloat>		acmeFloats;
	private Area				area;
	private List<Enrolment> enrolments;


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

	@URL
	public List<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final List<String> pictures) {
		this.pictures = pictures;
	}

	// Relationship access methods --------------------------------------------

	@ManyToMany
	public List<Procession> getProcession() {
		return this.procession;
	}

	public void setProcession(final List<Procession> procession) {
		this.procession = procession;
	}

@ManyToOne(optional = false)
	public List<AcmeFloat> getAcmeFloats() {
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

	@ManyToOne(optional = true)
	public List<Enrolment> getEnrolments(){
		return this.getEnrolments();
	}

	public void setEnrolment(final List<Enrolment> enrolments){
		this.enrolments = enrolments;
	}
}
