
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int id);

	@Query("select a from Actor a where a.name like concat('%', ?1, '%') or a.middleName like concat('%', ?1, '%') or a.surname like concat('%', ?1, '%') or a.photo like concat('%', ?1, '%') or a.photo like concat('%', ?1, '%') or a.email like concat('%', ?1, '%') or a.phoneNumber like concat('%', ?1, '%') or a.address like concat('%', ?1, '%') or a.userAccount.username like concat('%', ?1, '%')")
	List<Actor> getActorsWithWord(String word);

}
