
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MessageBox extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String	name;
	private boolean	isSystem;

	////////////////////////////////////////////////////////////////////////////////
	// Relationships

	private Actor	actor;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isSystem() {
		return this.isSystem;
	}

	public void setSystem(final boolean isSystem) {
		this.isSystem = isSystem;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Relationships access methods

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
