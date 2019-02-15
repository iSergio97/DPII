
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private Date			moment;
	private String			status;
	private int				offeredPrice;
	private List<String>	comments;

	// Relationships ----------------------------------------------------------

	private HandyWorker		handyWorker;
	private FixUpTask		fixUpTask;


	// Field access methods ---------------------------------------------------

	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Pattern(regexp = "^ACCEPTED|REJECTED|PENDING$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public int getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final int offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	@ElementCollection
	public List<String> getComments() {
		return this.comments;
	}

	public void setComments(final List<String> comments) {
		this.comments = comments;
	}

	// Relationship access methods --------------------------------------------

	@Valid
	@ManyToOne(optional = true)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@Valid
	@ManyToOne(optional = true)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

}
