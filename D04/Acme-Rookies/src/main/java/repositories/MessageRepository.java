/*
 * MessageRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends AbstractRepository<Message> {

	@Query("select m from Message m join m.messageBoxes mb where mb.id = ?1")
	Collection<Message> findMessages(int id);

}
