
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

}
