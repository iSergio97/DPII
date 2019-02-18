
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.AcmeFloat;

@Repository
public interface AcmeFloatRepository extends JpaRepository<AcmeFloat, Integer> {

}
