/*
 * PositionDataRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PositionData;

@Repository
public interface PositionDataRepository extends AbstractRepository<PositionData> {

	@Query("select c.hacker.userAccount.id from Curriculum c join c.positionData pd where pd.id = ?1")
	int findOwner(int positionDataId);

	@Query("select c.id from Curriculum c join c.positionData pd where pd.id = ?1")
	int findCurriculum(int positionDataId);

}
