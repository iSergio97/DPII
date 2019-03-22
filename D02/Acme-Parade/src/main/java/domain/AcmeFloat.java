
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class AcmeFloat extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String					title;
	private String					description;
	private List<String>			pictures;

	// Relationships ----------------------------------------------------------

	private Collection<Parade>	parades;
	private Brotherhood				brotherhood;


	// Field access methods ---------------------------------------------------

	@NotNull
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	//Optional
	//@URL
	@NotNull
	@ElementCollection
	public List<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final List<String> pictures) {
		this.pictures = pictures;
	}

	// Relationship access methods --------------------------------------------

	@ManyToMany
	@Valid
	public Collection<Parade> getParades() {
		return this.parades;
	}

	public void setParades(final Collection<Parade> parades) {
		this.parades = new ArrayList<Parade>(parades);
	}
	@ManyToOne
	@Valid
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
