/*
 * PublisherRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Publisher;

@Repository
public interface PublisherRepository extends AbstractRepository<Publisher> {

	@Query("select p from Publisher p where p.userAccount.id = ?1")
	Publisher findByUserAccountId(int id);

}
