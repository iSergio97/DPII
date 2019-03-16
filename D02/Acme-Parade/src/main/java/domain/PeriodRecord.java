
package domain;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class PeriodRecord extends DomainEntity {

	//Fields ----------------------------------------

	private String				title;

	private String				description;

	private int					startYear;

	private int					endYear;

	private Collection<String>	photos;

	//Relationships ---------------------------------

	private History				history;


	//Field access methods ----------------------------------

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

	@NotNull
	public int getStartYear() {
		return this.startYear;
	}

	public void setStartYear(final int startYear) {
		this.startYear = startYear;
	}

	@NotNull
	public int getEndYear() {
		return this.endYear;
	}

	public void setEndYear(final int endYear) {
		this.endYear = endYear;
	}

	@ElementCollection
	public Collection<String> getPhotos() {
		return this.photos;
	}

	public void setPhotos(final Collection<String> photos) {
		this.photos = photos;
	}

	//Relationship access methods -----------------------

	@ManyToOne(optional = true)
	@Valid
	public History getHistory() {
		return this.history;
	}

	public void setHistory(final History history) {
		this.history = history;
	}

}
