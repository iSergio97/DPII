
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	// Fields

	private String			name;
	private List<String>	pictures;


	// Field access methods

	@NotBlank
	@NotNull
	@Valid
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	//Optional
	@NotNull
	@ElementCollection
	/*
	 * @URL.List(value = {
	 * 
	 * @URL
	 * })
	 */
	public List<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final List<String> pictures) {
		this.pictures = pictures;
	}

}
