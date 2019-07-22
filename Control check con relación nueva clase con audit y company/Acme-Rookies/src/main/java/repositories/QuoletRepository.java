
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends AbstractRepository<Quolet> {

	@Query("select q from Quolet q where q.ticker like ?1")
	List<Quolet> findByTicker(String ticker);

	@Query("select q from Quolet q where q.audit.id = ?1")
	Collection<Quolet> findByAuditId(int auditId);

	@Query("select q from Quolet q where q.company.id = ?1")
	Collection<Quolet> findByCompanyId(int companyId);

	@Query("select q from Quolet q where q.audit.id = ?1 and q.draftMode = false")
	Collection<Quolet> findPublic(int auditId);
}
