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
import domain.Hacker;

@Repository
public interface ApplicationRepository extends AbstractRepository<Application> {

	@Query("select a from Application a where a.hacker.id = ?1")
	Collection<Application> findByHackerId(int id);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPositionId(int id);

	@Query("select min(1.0*(select count(a.hacker) from Application a where a.hacker.id=h.id)) from Hacker h")
	Double min();

	@Query("select max(1.0*(select count(a.hacker) from Application a where a.hacker.id=h.id)) from Hacker h")
	Double max();

	@Query("select avg(1.0*(select count(a.hacker) from Application a where a.hacker.id=h.id)) from Hacker h")
	Double avg();

	@Query("select stdDev(1.0*(select count(a.hacker) from Application a where a.hacker.id=h.id)) from Hacker h")
	Double stdDev();

	@Query("select a from Application a join a.hacker h group by h order by sum(h) desc")
	Collection<Hacker> hackerMax();
}
