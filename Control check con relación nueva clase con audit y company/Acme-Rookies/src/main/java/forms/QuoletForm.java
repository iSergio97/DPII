
package forms;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class QuoletForm {

	private String	body;
	private String	picture;
	private int		id;
	private int		auditId;


	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@Min(0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Min(0)
	public int getAuditId() {
		return this.auditId;
	}

	public void setAuditId(final int auditId) {
		this.auditId = auditId;
	}

}
