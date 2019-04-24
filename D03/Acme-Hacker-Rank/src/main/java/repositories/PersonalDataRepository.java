/*
 * PersonalDataRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PersonalData;

@Repository
public interface PersonalDataRepository extends AbstractRepository<PersonalData> {

	@Query("select c.hacker.userAccount.id from Curriculum c join c.personalData md where md.id = ?1")
	Integer findHackerByPDID(int id);
}
