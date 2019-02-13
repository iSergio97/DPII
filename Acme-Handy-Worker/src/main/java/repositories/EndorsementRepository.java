
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select e from Endorsement e where e.endorsed = ?1")
	Endorsement[] getEndorsements(Customer c);

	@Query("select e from Endorsement e where e.endorsed = ?1")
	Endorsement[] getEndorsements(HandyWorker h);

}
