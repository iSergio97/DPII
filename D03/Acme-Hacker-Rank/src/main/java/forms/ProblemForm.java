
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class ProblemForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	title;
	private String	statement;
	private String	hint;
	private String	attachments;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@Range(min = 0)
	public int getId() {
		return this.id;
	}
	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}
	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public String getHint() {
		return this.hint;
	}
	public void setHint(final String hint) {
		this.hint = hint;
	}

	@NotBlank
	public String getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

}
