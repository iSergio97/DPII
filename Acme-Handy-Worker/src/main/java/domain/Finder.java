
package domain;

import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String					keyword;
	private int						minimumPrice;
	private int						maximumPrice;
	private Date					minimumDate;
	private Date					maximumDate;

	// Relationships ----------------------------------------------------------

	private HandyWorker				handyWorker;
	private Collection<FixUpTask>	fixUpTasks;
	private Warranty				warranty;
	private FixUpTaskCategory		fixUpTaskCategory;


	// Field access methods ---------------------------------------------------

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public int getMinimumPrice() {
		return this.minimumPrice;
	}

	public void setMinimumPrice(final int minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public int getMaximumPrice() {
		return this.maximumPrice;
	}

	public void setMaximumPrice(final int maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMinimumDate() {
		return this.minimumDate;
	}

	public void setMinimumDate(final Date minimumDate) {
		this.minimumDate = minimumDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMaximumDate() {
		return this.maximumDate;
	}

	public void setMaximumDate(final Date maximumDate) {
		this.maximumDate = maximumDate;
	}

	// Relationship access methods --------------------------------------------

	@Valid
	@OneToOne(optional = true)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@Valid
	@ManyToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTask) {
		this.fixUpTasks = fixUpTask;
	}

	@Valid
	@ManyToOne(optional = true)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	@Valid
	@OneToOne(optional = true)
	public FixUpTaskCategory getFixUpTaskCategory() {
		return this.fixUpTaskCategory;
	}

	public void setFixUpTaskCategory(final FixUpTaskCategory fixUpTaskCategory) {
		this.fixUpTaskCategory = fixUpTaskCategory;
	}

}
