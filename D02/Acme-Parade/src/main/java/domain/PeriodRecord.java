
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class PeriodRecord extends Record {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int				startYear;
	private int				endYear;
	private List<String>	photos;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

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
	public List<String> getPhotos() {
		return this.photos;
	}

	public void setPhotos(final List<String> photos) {
		this.photos = photos;
	}

}
