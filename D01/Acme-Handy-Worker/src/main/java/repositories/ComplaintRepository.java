
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select c from Complaint c where c.reports.size = 0")
	Collection<Complaint> getUnassignedComplaints();

	@Query("select c from Complaint c join c.fixUpTask fut where fut.customer = ?1")
	Collection<Complaint> getComplaints(Customer c);

	@Query("select c from Complaint c join c.fixUpTask fut where fut.customer = ?1 and fut = ?2")
	Collection<Complaint> getComplaints(Customer c, FixUpTask fut);

	@Query("select c.fixUpTask.customer from Complaint c where c.description like concat('%', ?1, '%')")
	List<Customer> getCustomersWhoPublishComplaintsWithWord(String word);

}
