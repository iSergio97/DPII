/*
 * UserRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends AbstractRepository<User> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int id);

}
