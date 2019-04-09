/*
 * SocialProfileRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SocialProfile;

@Repository
public interface SocialProfileRepository extends AbstractRepository<SocialProfile> {

	@Query("select s from SocialProfile s where s.actor.id = ?1")
	Collection<SocialProfile> findByActorId(int id);

}
