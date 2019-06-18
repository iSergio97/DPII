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

import domain.Company;
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

	@Query("select avg(1.0*(select count(p.company) from Position p where p.company.id=c.id)) from Company c")
	Double media();

	@Query("select min(1.0*(select count(p.company) from Position p where p.company.id=c.id)) from Company c")
	Double min();

	@Query("select max(1.0*(select count(p.company) from Position p where p.company.id=c.id)) from Company c")
	Double max();

	@Query("select stddev(1.0*(select count(p.company) from Position p where p.company.id=c.id)) from Company c")
	Double stdDev();

	@Query("select p.company from Position p group by p.company order by sum(p) desc")
	List<Company> companyMax();

	@Query("select p from Position p where p.title like ?1 or p.description like ?1 or p.profile like ?1 or p.skills like ?1 or p.technologies like ?1 or p.ticker like ?1")
	Collection<Position> searchQuery(String text);

}
