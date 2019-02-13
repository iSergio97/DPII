
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select f.customer from FixUpTask f where f.customer.fixUpTasks.size >= (select avg(c.fixUpTasks.size) from Customer c) * 1.1 group by f.customer order by sum(f.applications.size)")
	List<Customer> getTopFixUpTasks();

	@Query("select f.customer from FixUpTask f group by f.customer order by sum(f.complaints.size)")
	List<Customer> getTopComplaints();

	@Query("select min(c.fixUpTasks.size) * 1.0, max(c.fixUpTasks.size) * 1.0, avg(c.fixUpTasks.size), stddev(c.fixUpTasks.size) from Customer c")
	Double[] getFixUpTaskStatistics();

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int id);

}
