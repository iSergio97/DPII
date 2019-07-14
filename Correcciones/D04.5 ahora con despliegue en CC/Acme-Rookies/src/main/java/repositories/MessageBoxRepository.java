
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

@Repository
public interface MessageBoxRepository extends AbstractRepository<MessageBox> {

	@Query("select mb from MessageBox mb join mb.actor a where a.id = ?1")
	List<MessageBox> messageFromActor(int id);

	@Query("select mb from MessageBox mb join mb.actor a where a.id = ?1 and mb.isSystem = 1 and mb.name = 'InBox'")
	MessageBox findInBox(int id);

	@Query("select mb from MessageBox mb join mb.actor a where a.id = ?1 and mb.isSystem = 1 and mb.name = 'OutBox'")
	MessageBox findOutBox(int id);

	@Query("select mb from MessageBox mb join mb.actor a where a.id = ?1 and mb.isSystem = 1 and mb.name = 'TrashBox'")
	MessageBox findTrashBox(int id);

	@Query("select mb from MessageBox mb join mb.actor a where a.id = ?1 and mb.isSystem = 1 and mb.name = 'SpamBox'")
	MessageBox findSpamBox(int id);

	@Query("select mb from MessageBox mb join mb.actor a where a.id = ?1 and mb.isSystem = 1 and mb.name = 'NotificationBox'")
	MessageBox findNotificationBox(int id);

	@Query("select mb from MessageBox mb join mb.actor a where a.userAccount.id = ?1 and mb.isSystem = 1 and mb.name = ?2")
	MessageBox findByPrincipalAndName(int id, String name);

	@Query("select mb from MessageBox mb join mb.actor a where a.userAccount.id = ?1 and mb.isSystem = 1")
	Collection<MessageBox> findSystemBoxes(int id);

	@Query("select mb from MessageBox mb join mb.actor a where a.userAccount.id = ?1 and mb.isSystem = 0")
	Collection<MessageBox> findMessageBoxes(int id);

}
