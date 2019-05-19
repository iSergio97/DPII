/*
 * CriticRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Critic;

@Repository
public interface CriticRepository extends AbstractRepository<Critic> {

	@Query("select c from Critic c where c.userAccount.id = ?1")
	Critic findByUserAccountId(int id);

}
