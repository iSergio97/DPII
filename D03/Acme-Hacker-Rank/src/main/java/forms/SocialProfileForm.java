/*
 * CompanyForm.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class SocialProfileForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	nick;
	private String	socialNetworkName;
	private String	profileLink;


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
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	@NotBlank
	public String getSocialNetworkName() {
		return this.socialNetworkName;
	}

	public void setSocialNetworkName(final String socialNetworkName) {
		this.socialNetworkName = socialNetworkName;
	}

	@NotEmpty
	@URL
	public String getProfileLink() {
		return this.profileLink;
	}

	public void setProfileLink(final String profileLink) {
		this.profileLink = profileLink;
	}

}
