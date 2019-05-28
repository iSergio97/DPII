
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

public class ChapterForm {

	///////////////////////////////////////////////////////////////////
	//Fields

	private int		id;
	private int		number;
	private String	title;
	private String	description;
	private Date	releaseDate;
	private int		duration;
	private int		seasonId;


	@Range(min = 0)
	public int getId() {
		return this.id;
	}
	public void setId(final int id) {
		this.id = id;
	}

	@Range(min = 1)
	public int getNumber() {
		return this.number;
	}
	public void setNumber(final int number) {
		this.number = number;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getReleaseDate() {
		return this.releaseDate;
	}
	public void setReleaseDate(final Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Range(min = 1)
	public int getDuration() {
		return this.duration;
	}
	public void setDuration(final int duration) {
		this.duration = duration;
	}

	public int getSeasonId() {
		return this.seasonId;
	}
	public void setSeasonId(final int seasonId) {
		this.seasonId = seasonId;
	}

}
