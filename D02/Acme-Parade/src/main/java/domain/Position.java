
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyClass;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private Map<String, String>	strings;


	// Field access methods ---------------------------------------------------

	@NotNull
	@ElementCollection(targetClass = String.class)
	@MapKeyClass(String.class)
	public Map<String, String> getStrings() {
		return this.strings;
	}

	public void setStrings(final Map<String, String> strings) {
		this.strings = strings;
	}

}
