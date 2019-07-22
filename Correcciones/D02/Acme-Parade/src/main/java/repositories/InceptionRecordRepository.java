
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.InceptionRecord;

@Repository
public interface InceptionRecordRepository extends JpaRepository<InceptionRecord, Integer> {

	@Query("select r from InceptionRecord r where r.history.id = ?1")
	public InceptionRecord getInceptionRecordByHistory(int historyId);
}
