/*
 * EducationDataRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationData;

@Repository
public interface EducationDataRepository extends AbstractRepository<EducationData> {

	@Query("select c.hacker.userAccount.id from Curriculum c join c.educationData ed where ed.id = ?1")
	int findOwner(int educationDataId);

	@Query("select c.id from Curriculum c join c.educationData ed where ed.id = ?1")
	int findCurriculum(int educationDataId);

}
