
package forms;

import org.hibernate.validator.constraints.Range;

public class CommentForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	int				id;
	private String	text;
	private double	score;
	private int		serieId;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Range(min = 0, max = 10)
	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}

	@Range(min = 0)
	public int getSerieId() {
		return this.serieId;
	}

	public void setSerieId(final int serieId) {
		this.serieId = serieId;
	}

}
