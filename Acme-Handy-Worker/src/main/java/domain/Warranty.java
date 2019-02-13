
package domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	// Fields -----------------------------------------------------------------

	private String				title;
	private String				terms;
	private Collection<String>	applicableLaws;
	private boolean				draft;


	// Field access methods ---------------------------------------------------

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	@ElementCollection
	public Collection<String> getApplicableLaws() {
		return this.applicableLaws;
	}

	public void setApplicableLaws(final List<String> applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	public boolean getDraft() {
		return this.draft;
	}

	public void setDraft(final boolean draft) {
		this.draft = draft;
	}
}
