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

	@Query("select c from Curriculum c join c.hacker h where h.id = ?1")
	Collection<Curriculum> findCurriculumsByHacker(int id);

	@Query("select c from Curriculum c join c.personalData p where p.id = ?1")
	Curriculum findCurriculumByPDId(int id);
}
