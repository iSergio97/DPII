
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String							ticker;

	// Relationships ----------------------------------------------------------

	private Collection<MiscellaneousRecord>	miscellaneousRecord;
	private Collection<EndorserRecord>		endorserRecord;
	private Collection<ProfessionalRecord>	professionalRecord;
	private Collection<EducationalRecord>	educationalRecord;
	private PersonalRecord					personalRecord;
	private HandyWorker						handyWorker;


	// Field access methods ---------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^([\\d]){6}-([A-Z\\d]){6}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	// Relationship access methods --------------------------------------------

	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	public void setMiscellaneousRecord(final Collection<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecord() {
		return this.endorserRecord;
	}

	public void setEndorserRecord(final Collection<EndorserRecord> endorserRecord) {
		this.endorserRecord = endorserRecord;
	}

	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecord() {
		return this.professionalRecord;
	}

	public void setProfessionalRecord(final Collection<ProfessionalRecord> professionalRecord) {
		this.professionalRecord = professionalRecord;
	}

	@OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
	public Collection<EducationalRecord> getEducationalRecord() {
		return this.educationalRecord;
	}

	public void setEducationalRecord(final Collection<EducationalRecord> educationalRecord) {
		this.educationalRecord = educationalRecord;
	}

	@OneToOne(mappedBy = "curriculum", cascade = CascadeType.ALL)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@Valid
	@OneToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

}
