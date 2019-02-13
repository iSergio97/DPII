
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

	//query para sacar las phases de un handyworkerId

	@Query("select f from Phase f where f.workPlan.id = ?1")
	Collection<Phase> findByWorkplanId(int workplanId);
}
