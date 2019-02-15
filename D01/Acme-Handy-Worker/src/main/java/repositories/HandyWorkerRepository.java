
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	// This query is correct but doesn't work anyway because for some FUCKING reason JPQL doesn't let you do arithmetic and group in the same query.
	// @Query("select a.handyWorker from Application a where a.status = 'ACCEPTED' and count(a) >= ((select count(a2) from Application a2 where a2.status = 'ACCEPTED') * 1.1 / (select count(h) from HandyWorker h)) group by a.handyWorker order by count(a)")
	@Query("select h1 from HandyWorker h1 where h1.applications.size >= (select avg(h2.applications.size) from HandyWorker h2) * 1.1 order by h1.applications.size")
	List<HandyWorker> getTopApplications();

	@Query("select a.handyWorker from Application a group by a.handyWorker order by sum(a.fixUpTask.complaints.size)")
	List<HandyWorker> getTopComplaints();

	@Query("select h from HandyWorker h where h.userAccount.id=?1")
	HandyWorker findByUserAccountId(int userAccountId);

}
