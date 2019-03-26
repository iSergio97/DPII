
package forms;

import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import domain.Position;

public class SystemConfigurationForm {

	// Fields -----------------------------------------------------------------

	private int			id;
	private String		defaultCountryCode;
	private String		systemName;
	private String		banner;
	private int			finderDuration;
	private int			maximumFinderResults;
	private String		positiveWords;
	private String		negativeWords;
	private String		spamWords;
	private String		welcomeMessages;
	private String		warningMessages;

	// Relationships ----------------------------------------------------------

	private Position	lowestPosition;


	// Field access methods ---------------------------------------------------

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

	// Matches a list of strings with "," as an element separator
	@Pattern(regexp = "(^([^,]+,)*[^,]+$")
	public String getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final String positiveWords) {
		this.positiveWords = positiveWords;
	}

	// Matches a list of strings with "," as an element separator
	@Pattern(regexp = "(^([^,]+,)*[^,]+$")
	public String getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final String negativeWords) {
		this.negativeWords = negativeWords;
	}

	// Matches a list of strings with "," as an element separator
	@Pattern(regexp = "(^([^,]+,)*[^,]+$")
	public String getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	// Matches a map of strings with ":" as a pair separator and ";" as an entry separator
	@Pattern(regexp = "^([^;:]+:[^;:]+;)*[^;:]+:[^;:]+$")
	public String getWelcomeMessages() {
		return this.welcomeMessages;
	}

	public void setWelcomeMessages(final String welcomeMessages) {
		this.welcomeMessages = welcomeMessages;
	}

	// Matches a map of strings with ":" as a pair separator and ";" as an entry separator
	@Pattern(regexp = "^([^;:]+:[^;:]+;)*[^;:]+:[^;:]+$")
	public String getWarningMessages() {
		return this.warningMessages;
	}

	public void setWarningMessages(final String warningMessages) {
		this.warningMessages = warningMessages;
	}

	////////////////////////////////////////////////////////////////////////////////
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
