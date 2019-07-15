/*
 * ProblemRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;
import domain.Problem;

@Repository
public interface ProblemRepository extends AbstractRepository<Problem> {

	@Query("select p from Position p join p.problems pr where pr.id = ?1")
	Collection<Position> findPositionAssociated(int problemId);

}
