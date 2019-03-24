
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousRecord;

@Repository
public interface MiscellaneousRecordRepository extends JpaRepository<MiscellaneousRecord, Integer> {

	@Query("select r from MiscellaneousRecord r where r.history.id = ?1")
	public Collection<MiscellaneousRecord> getMiscellaneousRecordsByHistory(int historyId);

}
