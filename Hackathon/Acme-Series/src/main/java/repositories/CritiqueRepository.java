/*
 * CritiqueRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Critique;

@Repository
public interface CritiqueRepository extends AbstractRepository<Critique> {

	@Query("select cr from Critique cr join cr.critic c where c.userAccount.id = ?1")
	List<Critique> findAllByUserAccount(int userAccountId);

	@Query("select cr from Critique cr join cr.serie s where s.id = ?1")
	List<Critique> findAllBySerie(int serieId);

}
