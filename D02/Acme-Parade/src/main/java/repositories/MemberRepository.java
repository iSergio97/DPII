
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	@Query("select m from Member m where m.userAccount.id = ?1")
	Member findByUserAccountId(int id);

	@Query("select m from domain.Member m join m.enrolments e where e.brotherhood.userAccount.id = ?1 and e.exitMoment is null")
	Collection<Member> findMemebersByBrotherhoodAccountId(int id);

}
