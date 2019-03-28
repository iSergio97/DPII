
package forms;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class PeriodRecordForm {

	private String			title;
	private String			description;
	private Integer			startYear;
	private Integer			endYear;
	private List<String>	photos;
	private Integer			id;


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
	public Integer getStartYear() {
		return this.startYear;
	}

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
	}

	@NotNull
	public Integer getEndYear() {
		return this.endYear;
	}

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	@ElementCollection
	public List<String> getPhotos() {
		return this.photos;
	}

	public void setPhotos(final List<String> photos) {
		this.photos = photos;
	}

	@Range(min = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

}
