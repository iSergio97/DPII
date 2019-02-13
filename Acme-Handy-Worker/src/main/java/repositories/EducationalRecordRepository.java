
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationalRecord;
import domain.HandyWorker;

@Repository
public interface EducationalRecordRepository extends JpaRepository<EducationalRecord, Integer> {

	@Query("select e.curriculum.handyWorker from EducationalRecord e where e.diplomaTitle like concat('%', ?1, '%') or e.institution like concat('%', ?1, '%') or e.attachment like concat('%', ?1, '%') or e.comments like concat('%', ?1, '%')")
	List<HandyWorker> getHandyWorkersWithEducationalRecordsWithWord(String word);

}
