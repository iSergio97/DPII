/*
 * SystemConfiguration.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String				systemName;
	private String				banner;
	private String				welcomeMessage;
	private String				welcomeMessageES;
	private String				defaultCC;
	private int					finderCacheTime;
	private int					maximumFinderResults;
	private Collection<String>	spamWords;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@NotBlank
	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}

	public void setWelcomeMessageES(final String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	@Pattern(regexp = "^\\+[1-9]\\d{0,2}$")
	public String getDefaultCC() {
		return this.defaultCC;
	}

	public void setDefaultCC(final String defaultCC) {
		this.defaultCC = defaultCC;
	}

	@Min(value = 1)
	@Max(value = 24)
	public int getFinderCacheTime() {
		return this.finderCacheTime;
	}

	public void setFinderCacheTime(final int finderCacheTime) {
		this.finderCacheTime = finderCacheTime;
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
	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

}
