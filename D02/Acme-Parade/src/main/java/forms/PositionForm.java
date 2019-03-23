
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class PositionForm {

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

	@NotBlank
	public String getStrings() {
		return this.strings;
	}

	public void setStrings(final String strings) {
		this.strings = strings;
	}

}
