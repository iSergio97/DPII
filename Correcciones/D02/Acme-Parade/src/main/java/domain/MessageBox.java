
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
// Esto hace que el par {actor, name} sea único, permitiendo, por ejemplo guardar varias 'InBox' en la tabla 'message_box' pero todas de diferentes usuarios
// Lo he comentado temporalmente que no me funcionaba con ésto puesto
/*
 * @Table(uniqueConstraints = {
 * 
 * @UniqueConstraint(columnNames = {
 * "actor", "name"
 * })
 * })
 */
public class MessageBox extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String				name;
	private Boolean				isSystem;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Actor				actor;
	private Collection<Message>	messages;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public Boolean getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(final Boolean isSystem) {
		this.isSystem = isSystem;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationship access methods

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "messageBoxes")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

}
