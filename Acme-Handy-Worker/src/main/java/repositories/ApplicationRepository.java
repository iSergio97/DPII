
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.WorkPlan;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select (count(a) / (select count(b) from Application b)) * 1.0 from Application a where a.status='PENDING'")
	Double getPendingRatio();

	@Query("select (count(a) / (select count(b) from Application b)) * 1.0 from Application a where a.status='ACCEPTED'")
	Double getAcceptedRatio();

	@Query("select (count(a) / (select count(b) from Application b)) * 1.0 from Application a where a.status='REJECTED'")
	Double getRejectedRatio();

	@Query("select (count(a) / (select count(b) from Application b)) * 1.0 from Application a where a.fixUpTask.timeLimit < CURRENT_DATE and a.status = 'PENDING'")
	Double getExpiredRatio();

	@Query("select min(a.offeredPrice) * 1.0, max(a.offeredPrice) * 1.0, avg(a.offeredPrice), stddev(a.offeredPrice) from Application a")
	Double[] getOfferedPriceStatistics();

	@Query("select a from Application a where a.handyWorker.id='?1'")
	List<Application> getApplicationsByHandyWorkerId(int handyWorkerId);

	// Query para usar solo las aplicaciones aceptadas
	// @Query("select a.fixUpTask.workPlan from Application a join a.handyWorker h where h.id = ?1 and a.status = 'ACCEPTED'")
	// Query para obtener los workplans de todas las aplicaciones
	@Query("select a.fixUpTask.workPlan from Application a join a.handyWorker h where h.id = ?1")
	List<WorkPlan> getWorkPlansByHandyWorkerId(int handyWorkerId);

}
