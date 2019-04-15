/*
 * CurriculumRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends AbstractRepository<Curriculum> {

	@Query("select c from Curriculum c where c.hacker.id = ?1")
	Collection<Curriculum> findByHackerId(int id);

}
