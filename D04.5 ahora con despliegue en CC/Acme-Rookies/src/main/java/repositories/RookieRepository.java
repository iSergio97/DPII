/*
 * RookieRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface RookieRepository extends AbstractRepository<Rookie> {

	@Query("select r from Rookie r where r.userAccount.id = ?1")
	Rookie findByUserAccountId(int id);

}
