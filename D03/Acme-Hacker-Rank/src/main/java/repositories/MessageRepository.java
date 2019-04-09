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

}
