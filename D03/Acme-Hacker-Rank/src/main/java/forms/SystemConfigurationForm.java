/*
 * SystemConfigurationForm.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class SystemConfigurationForm {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private int		id;
	private String	defaultCountryCode;
	private String	systemName;
	private String	banner;
	private int		finderDuration;
	private int		maximumFinderResults;
	private String	spamWords;
	private String	welcomeMessages;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@Range(min = 0)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Pattern(regexp = "^\\+[1-9]\\d{0,2}$")
	public String getDefaultCountryCode() {
		return this.defaultCountryCode;
	}

	public void setDefaultCountryCode(final String defaultCountryCode) {
		this.defaultCountryCode = defaultCountryCode;
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

	@Min(value = 3600)
	@Max(value = 86400)
	public int getFinderDuration() {
		return this.finderDuration;
	}

	public void setFinderDuration(final int finderDuration) {
		this.finderDuration = finderDuration;
	}

	@Min(value = 1)
	@Max(value = 100)
	public int getMaximumFinderResults() {
		return this.maximumFinderResults;
	}

	public void setMaximumFinderResults(final int maximumFinderResults) {
		this.maximumFinderResults = maximumFinderResults;
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
	public String getWelcomeMessages() {
		return this.welcomeMessages;
	}

	public void setWelcomeMessages(final String welcomeMessages) {
		this.welcomeMessages = welcomeMessages;
	}

}