
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class ItemForm {

	///////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	name;
	private String	description;
	private String	link;
	private String	pictures;


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

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@URL
	public String getLink() {
		return this.link;
	}
	public void setLink(final String link) {
		this.link = link;
	}

	@URL
	public String getPictures() {
		return this.pictures;
	}
	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

}
