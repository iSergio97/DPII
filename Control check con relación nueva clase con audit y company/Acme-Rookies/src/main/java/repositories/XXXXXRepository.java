
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.XXXXX;

@Repository
public interface XXXXXRepository extends AbstractRepository<XXXXX> {

	@Query("select x from XXXXX x where x.ticker like ?1")
	List<XXXXX> findByTicker(String ticker);

	@Query("select x from XXXXX x where x.audit.id = ?1")
	Collection<XXXXX> findByAuditId(int auditId);

	@Query("select x from XXXXX x where x.company.id = ?1")
	Collection<XXXXX> findByCompanyId(int companyId);

}
