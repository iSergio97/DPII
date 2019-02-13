
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.Referee;
import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select c from Report r join r.complaint c where r.referee = ?1")
	Complaint[] getComplaintsOfReferee(Referee r);

	@Query("select min(r.notes.size) * 1.0, max(r.notes.size) * 1.0, avg(r.notes.size), stddev(r.notes.size) from Report r")
	Double[] getNoteStatistics();

}
