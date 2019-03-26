
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public class PriorityForm {

	// Fields -----------------------------------------------------------------

	private int		id;
	private String	strings;


	// Field access methods ---------------------------------------------------

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	// Matches a map of strings with ":" as a pair separator and ";" as an entry separator
	@Pattern(regexp = "^([^;:]+:[^;:]+;)*[^;:]+:[^;:]+$")
	public String getStrings() {
		return this.strings;
	}

	public void setStrings(final String strings) {
		this.strings = strings;
	}

}
