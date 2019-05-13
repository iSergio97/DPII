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
	Double averageScoreByPositionId(int id);

	@Query("select count(a) from Audit a where a.position.company.id = ?1")
	long countByCompanyId(int id);

	@Query("select avg(a.score) from Audit a where a.position.company.id = ?1")
	Double averageScoreByCompanyId(int id);

	@Query("select min(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	Double getMinimumScorePosition();

	@Query("select max(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	Double getMaximumScorePosition();

	@Query("select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	Double getAverageScorePosition();

	@Query("select stddev(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id)) from Position p")
	Double getStandardDeviationScorePosition();

	@Query("select min(1.0 * (select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id and a.position.company.id = c.id)) from Position p)) from Company c")
	Double getMinimumScoreCompany();

	@Query("select max(1.0 * (select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id and a.position.company.id = c.id)) from Position p)) from Company c")
	Double getMaximumScoreCompany();

	@Query("select avg(1.0 * (select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id and a.position.company.id = c.id)) from Position p)) from Company c")
	Double getAverageScoreCompany();

	@Query("select stddev(1.0 * (select avg(0.1 * (select avg(a.score) from Audit a where a.position.id = p.id and a.position.company.id = c.id)) from Position p)) from Company c")
	Double getStandardDeviationScoreCompany();

}
