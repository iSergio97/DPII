
package domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

public class History extends DomainEntity {

	//Relationships --------------------------------

	private InceptionRecord					inceptionRecord;

	private Collection<PeriodRecord>		periodRecords;

	private Collection<LegalRecord>			legalRecords;

	private Collection<LinkRecord>			linkRecords;

	private Collection<MiscellaneousRecord>	miscRecords;


	//Relationship access methods ----------------------

	@OneToOne(cascade = CascadeType.ALL)
	@Valid
	public InceptionRecord getInceptionRecord() {
		return this.inceptionRecord;
	}

	public void setInceptionRecord(final InceptionRecord inceptionRecord) {
		this.inceptionRecord = inceptionRecord;
	}

	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
	@Valid
	public Collection<PeriodRecord> getPeriodRecords() {
		return this.periodRecords;
	}

	public void setPeriodRecords(final Collection<PeriodRecord> periodRecords) {
		this.periodRecords = periodRecords;
	}

	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
	@Valid
	public Collection<LegalRecord> getLegalRecords() {
		return this.legalRecords;
	}

	public void setLegalRecords(final Collection<LegalRecord> legalRecords) {
		this.legalRecords = legalRecords;
	}

	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
	@Valid
	public Collection<LinkRecord> getLinkRecords() {
		return this.linkRecords;
	}

	public void setLinkRecords(final Collection<LinkRecord> linkRecords) {
		this.linkRecords = linkRecords;
	}

	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
	@Valid
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscRecords;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscRecords) {
		this.miscRecords = miscRecords;
	}

}
