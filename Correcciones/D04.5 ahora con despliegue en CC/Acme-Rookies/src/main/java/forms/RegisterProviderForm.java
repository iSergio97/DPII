
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class RegisterProviderForm extends RegisterForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields
	private String	make;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}
}
