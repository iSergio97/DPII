
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b where b.userAccount.id = ?1")
	Brotherhood findByUserAccountId(int id);

	@Query("select count(b) from Brotherhood b where b.area.id = ?1")
	int countWithAreaId(int id);

	@Query("select min(b.enrolments.size) * 1.0, max(b.enrolments.size) * 1.0, avg(b.enrolments.size), stddev(b.enrolments.size) from Brotherhood b")
	Double[] getMemberStatistics();

	@Query("select b from Brotherhood b order by b.enrolments.size asc")
	List<Brotherhood> findAllOrderedBySizeAscending();

	@Query("select b from Brotherhood b order by b.enrolments.size desc")
	List<Brotherhood> findAllOrderedBySizeDescending();

}
