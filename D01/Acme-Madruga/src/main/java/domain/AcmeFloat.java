
package domain;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class AcmeFloat extends DomainEntity {

	// Fields -----------------------------------------------------------------
	private String			title;
	private String			description;
	private List<String>	pictures;
	private List<Procession>		processions;


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
	@URL
	public List<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final List<String> pictures) {
		this.pictures = pictures;
	}

	// Relationship access methods --------------------------------------------

	@ManyToMany
	public List<Procession> getProcessions() {
		return this.processions;
	}

	public void setProcession(final List<Procession> processions) {
		this.processions = processions;
	}
}
