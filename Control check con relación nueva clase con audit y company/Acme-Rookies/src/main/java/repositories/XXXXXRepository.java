
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.XXXXX;

@Repository
public interface XXXXXRepository extends AbstractRepository<XXXXX> {

	@Query("select x from XXXXX x where x.ticker like ?1")
	List<XXXXX> findByTicker(String ticker);
}
