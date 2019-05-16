/*
 * CompanyRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends AbstractRepository<Company> {

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findByUserAccountId(int id);

}
