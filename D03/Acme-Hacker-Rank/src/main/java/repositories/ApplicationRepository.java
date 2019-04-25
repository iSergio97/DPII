/*
 * ApplicationRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends AbstractRepository<Application> {

	@Query("select a from Application a where a.hacker.id = ?1")
	Collection<Application> findByHackerId(int id);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPositionId(int id);

}
