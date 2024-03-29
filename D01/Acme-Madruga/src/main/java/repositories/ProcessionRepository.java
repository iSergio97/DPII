
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.ticker like ?1")
	List<Procession> findByTicker(String ticker);

	@Query("select p from Procession p where p.moment < ?1 and p.isDraft = false")
	List<Procession> findBeforeDate(Date date);

	@Query("select p from Procession p where p.isDraft = false")
	List<Procession> findAllFinal();

	@Query("select p from Procession p where p.isDraft = false and brotherhood.userAccount.id = ?1")
	List<Procession> findAllFinalByBrotherhoodAccountId(int id);

	@Query("select p from Procession p where brotherhood.userAccount.id = ?1")
	List<Procession> findAllByBrotherhoodAccountId(int id);

	@Query("select p from Procession p join p.brotherhood.enrolments e where e.member.id= ?1")
	List<Procession> findPossibleMemberProcessions(int id);

}
