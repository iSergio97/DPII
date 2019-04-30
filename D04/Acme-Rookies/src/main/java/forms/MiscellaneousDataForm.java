
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class MiscellaneousDataForm {

	private String	freeText;
	private String	attachments;
	private int		id;
	private int		curriculumId;


	@NotBlank
	public String getFreeText() {
		return this.freeText;
	}

	public void setFreeText(final String freeText) {
		this.freeText = freeText;
	}

	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String string) {
		this.attachments = string;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getCurriculumId() {
		return this.curriculumId;
	}

	public void setCurriculumId(final int curriculumId) {
		this.curriculumId = curriculumId;
	}

}
