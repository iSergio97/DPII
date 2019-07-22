/*
 * CompanyForm.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import org.hibernate.validator.constraints.NotBlank;

public class RegisterCompanyForm extends RegisterForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String commercialName;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getCommercialName() {
		return this.commercialName;
	}

	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

}
