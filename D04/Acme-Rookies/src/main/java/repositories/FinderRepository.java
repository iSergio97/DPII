/*
 * FinderRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends AbstractRepository<Finder> {

	@Query("select p from Position p where p.ticker like ?1 OR p.title like ?2 OR p.description like ?3 OR p.skills like ?4 OR p.technologies like ?5 OR p.profile like ?6 OR p.deadline <= ?7 OR p.deadline <= ?8 OR p.salary = ?9")
	Collection<Position> findPositions(String kw1, String kw2, String kw3, String kw4, String kw5, String kw6, String deadline, String maximumDeadline, double minimumSalary);

	@Query("select f from Finder f where f.rookie.id = ?1")
	Finder findPrincipal(int rookieId);
}
