
package forms;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import domain.Parade;

public class AcmeFloatForm {

	// Fields -----------------------------------------------------------------

	private int				id;
	private String			title;
	private String			description;
	private String			pictures;

	// Relationships ----------------------------------------------------------

	private List<Parade>	parades;


	// Field access methods ---------------------------------------------------

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	// Matches any string that is not delimited by whitespace or empty
	// @Pattern(regexp = "^(\\S+\\s+)*\\S+$")
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	// Matches any string that is not delimited by whitespace or empty
	// @Pattern(regexp = "^(\\S+\\s+)*\\S+$")
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	// Matches a bunch of URLs separated by spaces, or an empty string
	@Pattern(regexp = "(^(https?:\\/\\/[a-zA-Z0-9\\-_~:/?#\\[\\]@!$&'\\(\\)\\*\\+,;=.]+ )*(https?:\\/\\/[a-zA-Z0-9\\-_~:/?#\\[\\]@!$&'\\(\\)\\*\\+,;=.]+)$)|(^$)")
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	// Relationship access methods --------------------------------------------

	public List<Parade> getParades() {
		return this.parades;
	}

	public void setParades(final List<Parade> parades) {
		this.parades = parades;
	}

}
