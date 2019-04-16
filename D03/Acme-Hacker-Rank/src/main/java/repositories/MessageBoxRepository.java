
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

@Repository
public interface MessageBoxRepository extends AbstractRepository<MessageBox> {

	@Query("select m from MessageBox m join m.actor a where a.id = ?1")
	List<MessageBox> messageFromActor(int id);

}
