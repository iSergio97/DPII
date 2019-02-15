
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

@Repository
public interface MessageBoxRepository extends JpaRepository<MessageBox, Integer> {

	@Query("select m from MessageBox m join m.actor a where a.id = ?1")
	MessageBox[] getMessageBoxes(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = ?2")
	MessageBox findByPrincipalAndName(int id, String name);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.id = ?2")
	MessageBox[] findByPrincipalAndId(int id, int messageBoxId);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'InBox'")
	MessageBox getInBox(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'OutBox'")
	MessageBox getOutBox(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'TrashBox'")
	MessageBox getTrashBox(int id);

	@Query("select m from MessageBox m join m.actor a where a.id = ?1 and m.name = 'SpamBox'")
	MessageBox getSpamBox(int id);

}
