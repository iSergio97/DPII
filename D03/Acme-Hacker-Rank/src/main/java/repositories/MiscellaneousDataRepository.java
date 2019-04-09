/*
 * MiscellaneousDataRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousData;

@Repository
public interface MiscellaneousDataRepository extends AbstractRepository<MiscellaneousData> {

	@Query("select c.hacker.userAccount.id from Curriculum c join c.miscellaneousData md where md.id = ?1")
	int findOwner(int miscellaneousDataId);

}
