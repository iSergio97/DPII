
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class AreaForm {

	// Fields -----------------------------------------------------------------

	private int		id;
	private String	name;
	private String	pictures;


	// Field access methods ---------------------------------------------------

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	// Matches a bunch of URLs separated by spaces
	@Pattern(regexp = "^(https?:\\/\\/(www\\.)?[a-zA-Z0-9\\-._~:/?#\\[\\]@!$&'\\(\\)\\*\\+,;=]+ )*(https?:\\/\\/(www\\.)?[a-zA-Z0-9\\-._~:/?#\\[\\]@!$&'\\(\\)\\*\\+,;=]+)$")
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

}
