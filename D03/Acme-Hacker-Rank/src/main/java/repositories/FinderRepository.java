/*
 * FinderRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends AbstractRepository<Finder> {

	@Query("select p from Position p where p.ticker = '?1' OR p.title = '?2' OR p.description = '?3' OR p.skills = '?4' OR p.technologies = '?5' OR p.profile = '?6' OR p.deadline = ?7 OR p.deadline = ?8 OR p.salary = ?9")
	Collection<Position> findPositions(String kw1, String kw2, String kw3, String kw4, String kw5, String kw6, Date deadline, Date maximumDeadline, double minimumSalary);

	@Query("select f from Finder f where f.hacker.id = '?1'")
	Finder findPrincipal(int hackerId);
}
