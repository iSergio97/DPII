/*
 * FinderRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Serie;

@Repository
public interface FinderRepository extends AbstractRepository<Finder> {

	@Query("select s from Serie s where s.title like ?1 or s.description like ?1 or (s.startDate >= ?2 and s.endDate <= ?3)")
	Collection<Serie> findSeries(String keyword, Date minDate, Date maxDate);

	@Query("select f from Finder f where f.user.id = ?1")
	Finder findByUser(int userId);

}
