
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	// Fields -----------------------------------------------------------------

	private String	email;


	// Relationships ----------------------------------------------------------

	// Field access methods ---------------------------------------------------

	@Override
	@Pattern(regexp = "^([a-zA-Z0-9 ]+<[a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)?>)|([a-zA-Z0-9]+@([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)?)$")
	public String getEmail() {
		return this.email;
	}

	@Override
	public void setEmail(final String email) {
		this.email = email;
	}

	// Relationship access methods --------------------------------------------

}
