
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;
import domain.PersonalRecord;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {

	@Query("select p.curriculum.handyWorker from PersonalRecord p where p.fullName like concat('%', ?1, '%') or p.photo like concat('%', ?1, '%') or p.email like concat('%', ?1, '%') or p.phoneNumber like concat('%', ?1, '%') or p.linkedIn like concat('%', ?1, '%')")
	List<HandyWorker> getHandyWorkersWithPersonalRecordsWithWord(String word);

}
