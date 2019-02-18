
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private Date					date;
	private String					subject;
	private String					body;
	private String					priority;
	private Collection<String>		tags;

	// Relationships ----------------------------------------------------------

	private Actor					sender;
	private Collection<Actor>		recipients;
	private Collection<MessageBox>	messageBoxes;


	// Field access methods ---------------------------------------------------

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	@Pattern(regexp = "^HIGH|NEUTRAL|LOW$")
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	//	@NotBlank
	@ElementCollection
	public Collection<String> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<String> tags) {
		this.tags = tags;
	}

	// Relationship access methods --------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Actor> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<Actor> recipient) {
		this.recipients = recipient;
	}

	@NotNull
	@Valid
	@NotEmpty
	@ManyToMany
	public Collection<MessageBox> getMessageBoxes() {
		return this.messageBoxes;
	}

	public void setMessageBoxes(final Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

}
