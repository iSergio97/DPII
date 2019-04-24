/*
 * PositionRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;
import domain.Problem;

@Repository
public interface PositionRepository extends AbstractRepository<Position> {

	@Query("select p from Position p where p.ticker like ?1")
	List<Position> findByTicker(String ticker);

	@Query("select p from Problem p where p.company.id = ?1 and p.isDraft = false")
	List<Problem> findProblemsBycompany(int id);

	@Query("select p from Position p where p.company.id = ?1")
	List<Position> findPositionsByCompany(int id);

	@Query("select p from Position p where p.company.id = ?1 and p.status = 'ACCEPTED'")
	Collection<Position> findPositionForPublicAndCompany(int id);

}
