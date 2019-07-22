/*
 * ApplicationRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends AbstractRepository<Application> {

	@Query("select a from Application a join a.publisher p where p.userAccount.id = ?1")
	List<Application> findAllByUserAccountId(int userAccountId);

	@Query("select a from Application a join a.serie s where s.id = ?1 and a.status = 'ACCEPTED'")
	List<Application> findAllAcceptedBySerieId(int serieId);

	@Query("select a from Application a join a.serie s where s.id = ?1 and a.status = 'PENDING'")
	List<Application> findAllPendingBySerieId(int serieId);

	@Query("select a from Application a join a.administrator admin where admin.id = ?1")
	List<Application> findAllAppliesByAdminId(int id);

	@Query("select a from Application a join a.publisher p where p.id = ?1")
	List<Application> findAllApplicatoinsByPublisher(int id);

	@Query("select a from Application a where a.serie.id = ?1")
	List<Application> findAllBySerie(int serieId);

}
