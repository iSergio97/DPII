/*
 * SystemConfigurationForm.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class SystemConfigurationForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	systemName;
	private String	banner;
	private int		defaultCountryCode;
	private int		maximumFinderResults;
	private String	spamWords;
	private String	welcomeMessage;
	private String	positiveWords;
	private String	negativeWords;


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
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@Range(min = 1, max = 999)
	public int getDefaultCountryCode() {
		return this.defaultCountryCode;
	}

	public void setDefaultCountryCode(final int defaultCountryCode) {
		this.defaultCountryCode = defaultCountryCode;
	}

	@Range(min = 1, max = 100)
	public int getMaximumFinderResults() {
		return this.maximumFinderResults;
	}

	public void setMaximumFinderResults(final int maximumFinderResults) {
		this.maximumFinderResults = maximumFinderResults;
	}

	@NotNull
	// Matches a list of strings with "," as an element separator, or an empty string
	@Pattern(regexp = "(^([^,]+,)*[^,]+$)|(^$)")
	public String getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final String positiveWords) {
		this.positiveWords = positiveWords;
	}

	@NotNull
	// Matches a list of strings with "," as an element separator, or an empty string
	@Pattern(regexp = "(^([^,]+,)*[^,]+$)|(^$)")
	public String getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final String negativeWords) {
		this.negativeWords = negativeWords;
	}

	@NotNull
	// Matches a list of strings with "," as an element separator, or an empty string
	@Pattern(regexp = "(^([^,]+,)*[^,]+$)|(^$)")
	public String getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	@NotNull
	// Matches a map of strings with ":" as a pair separator and ";" as an entry separator, or an empty string
	@Pattern(regexp = "(^([^;:]+:[^;:]+;)*[^;:]+:[^;:]+$)|(^$)")
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

}
