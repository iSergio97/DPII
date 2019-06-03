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

	@Query("select a from Application a join a.administrator admin where admin.id = ?1")
	List<Application> findAllAppliesByAdminId(int id);

	@Query("select a from Application a join a.publisher p where p.id = ?1")
	List<Application> findAllApplicatoinsByPublisher(int id);
}
