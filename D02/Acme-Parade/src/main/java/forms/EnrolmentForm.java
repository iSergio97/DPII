
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Brotherhood;

public class EnrolmentForm {

	private Date		moment;
	private Date		exitMoment;
	private int			id;
	private Brotherhood	bro;


	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getExitMoment() {
		return this.exitMoment;
	}

	public void setExitMoment(final Date exitMoment) {
		this.exitMoment = exitMoment;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotNull
	public Brotherhood getBro() {
		return this.bro;
	}

	public void setBro(final Brotherhood bro) {
		this.bro = bro;
	}

}
