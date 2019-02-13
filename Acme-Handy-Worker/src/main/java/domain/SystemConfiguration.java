
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String			name;
	private String			banner;
	private String			message;
	private List<String>	spamWords;
	private double			VAT;
	private String			defaultCountryCode;
	private List<String>	creditCardMakers;
	private List<String>	positiveWords;
	private List<String>	negativeWords;
	private int				maximumFinderResults;


	// Field access methods ---------------------------------------------------

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@ElementCollection
	public List<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final List<String> spamWords) {
		this.spamWords = spamWords;
	}

	public double getVAT() {
		return this.VAT;
	}

	public void setVAT(final double VAT) {
		this.VAT = VAT;
	}

	@Pattern(regexp = "^\\+\\d{1,3}$")
	public String getDefaultCountryCode() {
		return this.defaultCountryCode;
	}

	public void setDefaultCountryCode(final String defaultCountryCode) {
		this.defaultCountryCode = defaultCountryCode;
	}

	@ElementCollection
	public List<String> getCreditCardMakers() {
		return this.creditCardMakers;
	}

	public void setCreditCardMakers(final List<String> creditCardMakers) {
		this.creditCardMakers = creditCardMakers;
	}

	@ElementCollection
	public List<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final List<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	@ElementCollection
	public List<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final List<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

	@Range(min = 0, max = 100)
	public Integer getMaximumFinderResults() {
		return this.maximumFinderResults;
	}

	public void setMaximumFinderResults(final int maximumFinderResults) {
		this.maximumFinderResults = maximumFinderResults;
	}

}
