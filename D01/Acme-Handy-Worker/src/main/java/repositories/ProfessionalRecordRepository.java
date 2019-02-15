
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;
import domain.ProfessionalRecord;

@Repository
public interface ProfessionalRecordRepository extends JpaRepository<ProfessionalRecord, Integer> {

	@Query("select p.curriculum.handyWorker from ProfessionalRecord p where p.companyName like concat('%', ?1, '%') or p.role like concat('%', ?1, '%') or p.attachment like concat('%', ?1, '%') or p.comments like concat('%', ?1, '%')")
	List<HandyWorker> getHandyWorkersWithProfessionalRecordsWithWord(String word);

}
