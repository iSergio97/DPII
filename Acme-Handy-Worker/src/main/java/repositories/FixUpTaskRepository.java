
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f where (f.ticker like concat('%', ?1, '%') or f.description like concat('%', ?1, '%') or f.address like concat('%', ?1, '%')) and f.fixUpTaskCategory.id = ?2 and f.warranty.id = ?3 and f.maximumPrice > ?4 and f.maximumPrice < ?5 and f.timeLimit > ?6 and f.timeLimit < ?7")
	List<FixUpTask> getFiltered(String keyword, int fixUpTaskCategoryId, int warrantyId, int minimumPrice, int maximumPrice, Date minimumDate, Date maximumDate);

	@Query("select (count(f1) / (select count(f2) from FixUpTask f2)) * 1.0 from FixUpTask f1 where f1.complaints.size > 0")
	Double getComplaintRatio();

	@Query("select min(f.applications.size) * 1.0, max(f.applications.size) * 1.0, avg(f.applications.size), stddev(f.applications.size) from FixUpTask f")
	Double[] getApplicationStatistics();

	@Query("select min(f.complaints.size) * 1.0, max(f.complaints.size) * 1.0, avg(f.complaints.size), stddev(f.complaints.size) from FixUpTask f")
	Double[] getComplaintStatistics();

	@Query("select min(f.maximumPrice) * 1.0, max(f.maximumPrice) * 1.0, avg(f.maximumPrice), stddev(f.maximumPrice) from FixUpTask f")
	Double[] getMaximumPriceStatistics();

	@Query("select f.customer from FixUpTask f where f.description like concat('%', ?1, '%') or f.address like concat('%', ?1, '%')")
	List<Customer> getCustomersWhoPublishFixUpTasksWithWord(String word);

}
