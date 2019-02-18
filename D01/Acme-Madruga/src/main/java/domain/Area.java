
package domain;

import java.util.List;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Area extends DomainEntity {

	// Fields -----------------------------------------------------------------
	private String			name;
	private List<String>	pictures;
	// Relationships ----------------------------------------------------------
	private Brotherhood		brotherhood;


	// Field access methods ---------------------------------------------------

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	//Optional
	@URL
	public List<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final List<String> pictures) {
		this.pictures = pictures;
	}

	// Relationship access methods --------------------------------------------

	@OneToOne
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
