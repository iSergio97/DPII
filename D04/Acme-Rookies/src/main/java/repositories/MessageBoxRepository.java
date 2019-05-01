
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

@Repository
public interface MessageBoxRepository extends AbstractRepository<MessageBox> {

	@Query("select m from MessageBox m join m.actor a where a.id = ?1")
	List<MessageBox> messageFromActor(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'InBox'")
	MessageBox getInBox(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'OutBox'")
	MessageBox getOutBox(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'TrashBox'")
	MessageBox getTrashBox(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'SpamBox'")
	MessageBox getSpamBox(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'NotificationBox'")
	MessageBox getNotifications(int id);
}
