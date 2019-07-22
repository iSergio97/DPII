/*
 * MessageRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends AbstractRepository<Message> {

<<<<<<< HEAD
=======
	@Query("select m from Message m join m.messageBoxes mb where mb.id = ?1")
	Collection<Message> findMessages(int id);

	@Query("select count(m) from Message m where m.isSpam = true and m.sender.id = ?1")
	int countSpam(int id);

	@Query("select count(m) from Message m where m.sender.id = ?1")
	int countMails(int id);
>>>>>>> develop
}
