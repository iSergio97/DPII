
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select (count(r1) / (select count(r2) from Request r2)) * 1.0 from Request r1 where r1.status = 'APPROVED'")
	Double getAcceptedRatio();

	@Query("select (count(r1) / (select count(r2) from Request r2)) * 1.0 from Request r1 where r1.status = 'REJECTED'")
	Double getRejectedRatio();

	@Query("select (count(r1) / (select count(r2) from Request r2)) * 1.0 from Request r1 where r1.status = 'PENDING'")
	Double getPendingRatio();

	@Query("select r.member, count(r) from Request r where r.status = 'APPROVED' group by r.member order by count(r) desc")
	List<Object[]> getMembersOrderedByNumberOfAcceptedRequests();

	@Query("select r from Request r where r.procession.brotherhood.id = ?1 group by r.status")
	List<Request> getOrderedBrotherhoodRequests(int id);

}
