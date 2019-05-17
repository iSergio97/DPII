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
import domain.Rookie;

@Repository
public interface ApplicationRepository extends AbstractRepository<Application> {

	@Query("select a from Application a where a.rookie.id = ?1")
	Collection<Application> findByRookieId(int id);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPositionId(int id);

	@Query("select min(1.0*(select count(a.rookie) from Application a where a.rookie.id=r.id)) from Rookie r")
	Double min();

	@Query("select max(1.0*(select count(a.rookie) from Application a where a.rookie.id=r.id)) from Rookie r")
	Double max();

	@Query("select avg(1.0*(select count(a.rookie) from Application a where a.rookie.id=r.id)) from Rookie r")
	Double avg();

	@Query("select stdDev(1.0*(select count(a.rookie) from Application a where a.rookie.id=r.id)) from Rookie r")
	Double stdDev();

	@Query("select a from Application a join a.rookie h group by h order by sum(h) desc")
	Collection<Rookie> rookieMax();
}
