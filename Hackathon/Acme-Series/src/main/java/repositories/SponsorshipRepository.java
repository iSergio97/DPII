
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends AbstractRepository<Sponsorship> {

	@Query("select s from Sponsorship s where s.provider.id = ?1")
	Collection<Sponsorship> findByProviderId(int providerId);

	@Query("select s from Sponsorship s where s.position.id = ?1")
	Collection<Sponsorship> findByPositionId(int positionId);

	@Query("select avg(1.0*(select count(s.provider) from Sponsorship s where s.provider.id=p.id)) from Provider p")
	Double mediaPro();

	@Query("select min(1.0*(select count(s.provider) from Sponsorship s where s.provider.id=p.id)) from Provider p")
	Double minPro();

	@Query("select max(1.0*(select count(s.provider) from Sponsorship s where s.provider.id=p.id)) from Provider p")
	Double maxPro();

	@Query("select stddev(1.0*(select count(s.provider) from Sponsorship s where s.provider.id=p.id)) from Provider p")
	Double stdDevPro();

}
