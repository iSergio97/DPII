/*
 * Authority.java
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Access(AccessType.PROPERTY)
public class Authority implements GrantedAuthority {

	private static final long	serialVersionUID	= 1L;

	// Values -----------------------------------------------------------------

	public static final String	ADMINISTRATOR		= "ADMINISTRATOR";
	public static final String	COMPANY				= "COMPANY";
	public static final String	ROOKIE				= "ROOKIE";

	// Attributes -------------------------------------------------------------

	private String				authority;


	// Constructors -----------------------------------------------------------

	public Authority() {
		super();
	}

	// Methods ----------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^" + Authority.ADMINISTRATOR + "|" + Authority.COMPANY + "|" + Authority.ROOKIE + "$")
	@Override
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public static Collection<Authority> listAuthorities() {
		Collection<Authority> result;
		Authority authority;

		result = new ArrayList<Authority>();

		authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		result.add(authority);

		return result;
	}

	// Object interface -------------------------------------------------------

	@Override
	public int hashCode() {
		return this.getAuthority().hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		boolean result;

		if (this == other)
			result = true;
		else if (other == null)
			result = false;
		else if (!this.getClass().isInstance(other))
			result = false;
		else
			result = (this.getAuthority().equals(((Authority) other).getAuthority()));

		return result;
	}

	@Override
	public String toString() {
		return this.authority;
	}

}
