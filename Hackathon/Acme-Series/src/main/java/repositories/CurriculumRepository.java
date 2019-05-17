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

	@Query("select c from Curriculum c join c.rookie r where r.id = ?1")
	Collection<Curriculum> findCurriculaByRookie(int id);

	@Query("select c from Curriculum c join c.personalData p where p.id = ?1")
	Curriculum findCurriculumByPDId(int id);

	@Query("select c.rookie.userAccount.id from Curriculum c where c.id = ?1")
	int findOwner(int miscellaneousDataId);
}
