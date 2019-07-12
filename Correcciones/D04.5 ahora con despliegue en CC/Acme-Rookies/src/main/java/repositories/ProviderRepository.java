/*
 * ProviderRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends AbstractRepository<Provider> {

	@Query("select p from Provider p where p.userAccount.id = ?1")
	Provider findByUserAccountId(int id);

}
