
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends AbstractRepository<Item> {

	@Query("select i from Item i where i.provider.id = ?1")
	Collection<Item> findByProviderId(int providerId);

	@Query("select avg(1.0*(select count(i.provider) from Item i where i.provider.id=p.id)) from Provider p")
	Double media();

	@Query("select min(1.0*(select count(i.provider) from Item i where i.provider.id=p.id)) from Provider p")
	Double min();

	@Query("select max(1.0*(select count(i.provider) from Item i where i.provider.id=p.id)) from Provider p")
	Double max();

	@Query("select stddev(1.0*(select count(i.provider) from Item i where i.provider.id=p.id)) from Provider p")
	Double stdDev();

}
