/*
 * CurriculumRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select c from Curriculum c join c.hacker h where h.id = ?1")
	Curriculum findCurriculumByHacker(int id);

}
