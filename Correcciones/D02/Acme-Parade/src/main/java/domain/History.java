
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class History extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Brotherhood			brotherhood;
	private Collection<Record>	records;


	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@OneToOne
	@Valid
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
	@Valid
	public Collection<Record> getRecords() {
		return this.records;
	}

	public void setRecords(final Collection<Record> records) {
		this.records = records;
	}

}
