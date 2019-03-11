
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Integer> {

}
