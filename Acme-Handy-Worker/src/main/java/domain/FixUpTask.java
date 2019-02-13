
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class FixUpTask extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String					ticker;
	private Date					moment;
	private String					description;
	private String					address;
	private Integer					maximumPrice;
	private Date					timeLimit;

	// Relationships ----------------------------------------------------------

	private Customer				customer;
	private FixUpTaskCategory		fixUpTaskCategory;
	//	private Customer				publisher;
	private Collection<Application>	applications;
	private Warranty				warranty;
	private WorkPlan				workPlan;
	private Collection<Complaint>	complaints;


	// Field access methods ---------------------------------------------------

	@NotNull
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^([\\d]){6}-([A-Z\\d]){6}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	public Integer getMaximumPrice() {
		return this.maximumPrice;
	}

	public void setMaximumPrice(final Integer maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(final Date timeLimit) {
		this.timeLimit = timeLimit;
	}

	// Relationship access methods --------------------------------------------

	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = true)
	public FixUpTaskCategory getFixUpTaskCategory() {
		return this.fixUpTaskCategory;
	}

	public void setFixUpTaskCategory(final FixUpTaskCategory fixUpTaskCategory) {
		this.fixUpTaskCategory = fixUpTaskCategory;
	}

	//	@NotNull
	//	@Valid
	//	public Customer getPublisher() {
	//		return this.publisher;
	//	}
	//
	//	public void setPublisher(final Customer publisher) {
	//		this.publisher = publisher;
	//	}

	@Valid
	@OneToMany(mappedBy = "fixUpTask")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@NotNull
	@ManyToOne
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	@Valid
	@OneToOne(optional = true)
	public WorkPlan getWorkPlan() {
		return this.workPlan;
	}

	public void setWorkPlan(final WorkPlan workPlan) {
		this.workPlan = workPlan;
	}

	@Valid
	@OneToMany(mappedBy = "fixUpTask")
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

}
