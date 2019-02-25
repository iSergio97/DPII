
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AcmeFloat;

@Repository
public interface AcmeFloatRepository extends JpaRepository<AcmeFloat, Integer> {

	@Query("select f from AcmeFloat f where f.brotherhood.id = ?1")
	Collection<AcmeFloat> findFloats(int principalId);

}
