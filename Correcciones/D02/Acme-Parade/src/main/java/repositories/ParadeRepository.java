
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	@Query("select p from Parade p where p.ticker like ?1")
	List<Parade> findByTicker(String ticker);

	@Query("select p from Parade p where p.moment < ?1 and p.isDraft = false")
	List<Parade> findBeforeDate(Date date);

	@Query("select p from Parade p where p.isDraft = false")
	List<Parade> findAllFinal();

	@Query("select p from Parade p where p.isDraft = false and brotherhood.userAccount.id = ?1")
	List<Parade> findAllFinalByBrotherhoodAccountId(int id);

	@Query("select p from Parade p where brotherhood.userAccount.id = ?1")
	List<Parade> findAllByBrotherhoodAccountId(int id);

	@Query("select p from Parade p join p.brotherhood.enrolments e where e.member.id= ?1")
	List<Parade> findPossibleMemberParades(int id);

	@Query("select p1 from Parade p1 where exists (select f from Parade p2 left join p2.acmeFloats f where p1 = p2 and f.id = ?1)")
	List<Parade> findParadesWithAcmeFloat(int acmeFloat);

}
