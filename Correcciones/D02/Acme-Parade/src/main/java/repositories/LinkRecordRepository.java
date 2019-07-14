
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LinkRecord;

@Repository
public interface LinkRecordRepository extends JpaRepository<LinkRecord, Integer> {

	@Query("select r from LinkRecord r where r.history.id = ?1")
	public Collection<LinkRecord> getLinkRecordsByHistory(int historyId);

}
