
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalRecord;

@Repository
public interface LegalRecordRepository extends JpaRepository<LegalRecord, Integer> {

	@Query("select r from LegalRecord r where r.history.id = ?1")
	public Collection<LegalRecord> getLegalRecordsByHistory(int historyId);

}
