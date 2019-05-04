/*
 * AuditRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends AbstractRepository<Audit> {

	@Query("select a from Audit a where a.auditor.id = ?1")
	Collection<Audit> findByAuditorId(int id);

	@Query("select a from Audit a where a.position.id = ?1")
	Collection<Audit> findByPositionId(int id);

	@Query("select count(a) from Audit a where a.position.id = ?1")
	long countByPositionId(int id);

	@Query("select avg(a.score) from Audit a where a.position.id = ?1")
	double averageScoreByPositionId(int id);

	@Query("select count(a) from Audit a where a.position.company.id = ?1")
	long countByCompanyId(int id);

	@Query("select avg(a.score) from Audit a where a.position.company.id = ?1")
	double averageScoreByCompanyId(int id);

	@Query("select min(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	double getMinimumScorePosition();

	@Query("select max(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	double getMaximumScorePosition();

	@Query("select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	double getAverageScorePosition();

	@Query("select stddev(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	double getStandardDeviationScorePosition();

	@Query("select min(1.0 * (select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id and a.position.company.id = c.id)) from Position p)) from Company c")
	double getMinimumScoreCompany();

	@Query("select max(1.0 * (select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id and a.position.company.id = c.id)) from Position p)) from Company c")
	double getMaximumScoreCompany();

	@Query("select avg(1.0 * (select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id and a.position.company.id = c.id)) from Position p)) from Company c")
	double getAverageScoreCompany();

	@Query("select stddev(1.0 * (select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id and a.position.company.id = c.id)) from Position p)) from Company c")
	double getStandardDeviationScoreCompany();

}
