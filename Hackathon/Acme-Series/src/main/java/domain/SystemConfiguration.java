/*
 * SystemConfiguration.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package domain;

import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyClass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String				systemName;
	private String				banner;
	private String				defaultCountryCode;
	private int					finderCacheTime;
	private int					maximumFinderResults;
	private List<String>		positiveWords;
	private List<String>		negativeWords;
	private List<String>		spamWords;
	private Map<String, String>	welcomeMessage;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

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

	@Pattern(regexp = "^\\+[1-9]\\d{0,2}$")
	public String getDefaultCountryCode() {
		return this.defaultCountryCode;
	}

	public void setDefaultCountryCode(final String defaultCountryCode) {
		this.defaultCountryCode = defaultCountryCode;
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
	public List<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final List<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	@NotNull
	@ElementCollection
	public List<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final List<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

	@NotNull
	@ElementCollection
	public List<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final List<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	@MapKeyClass(String.class)
	public Map<String, String> getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final Map<String, String> welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

}
