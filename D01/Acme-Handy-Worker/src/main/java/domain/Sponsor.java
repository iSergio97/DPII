
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	// Fields -----------------------------------------------------------------

	List<Sponsorship>	sponsorships;


	// Relationship access methods --------------------------------------------

	@OneToMany(mappedBy = "sponsor")
	public List<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final List<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}
}
