
package domain;

import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyClass;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	// Fields

	private String				defaultCountryCode;
	private String				systemName;
	private String				banner;
	private int					finderDuration;
	private int					maximumFinderResults;
	private List<String>		positiveWords;
	private List<String>		negativeWords;
	private List<String>		spamWords;
	private Map<String, String>	welcomeMessages;
	private Map<String, String>	warningMessages;

	// Relationships

	private Position			lowestPosition;


	// Field access methods

	public String getDefaultCountryCode() {
		return this.defaultCountryCode;
	}

	public void setDefaultCountryCode(final String defaultCountryCode) {
		this.defaultCountryCode = defaultCountryCode;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

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
	public Map<String, String> getWarningMessages() {
		return this.warningMessages;
	}

	public void setWarningMessages(final Map<String, String> warningMessages) {
		this.warningMessages = warningMessages;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	@MapKeyClass(String.class)
	public Map<String, String> getWelcomeMessages() {
		return this.welcomeMessages;
	}

	public void setWelcomeMessages(final Map<String, String> welcomeMessages) {
		this.welcomeMessages = welcomeMessages;
	}

	// Relationship access methods

	@NotNull
	@OneToOne
	public Position getLowestPosition() {
		return this.lowestPosition;
	}

	public void setLowestPosition(final Position lowestPosition) {
		this.lowestPosition = lowestPosition;
	}

}
