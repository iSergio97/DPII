/*
 * AdministratorRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends AbstractRepository<Administrator> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);

}
