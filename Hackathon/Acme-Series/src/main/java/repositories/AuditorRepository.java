/*
 * AuditorRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;

@Repository
public interface AuditorRepository extends AbstractRepository<Auditor> {

	@Query("select a from Auditor a where a.userAccount.id = ?1")
	Auditor findByUserAccountId(int id);

}
