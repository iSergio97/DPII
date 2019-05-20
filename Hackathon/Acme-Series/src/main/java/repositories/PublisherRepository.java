/*
 * PublisherRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Publisher;

@Repository
public interface PublisherRepository extends AbstractRepository<Publisher> {

	@Query("select p from Publisher p where p.userAccount.id = ?1")
	Publisher findByUserAccountId(int id);

	@Query("select min(1.0 * (select count(s.publisher) from Serie s where s.publisher.id = p.id)), max(1.0 * (select count(s.publisher) from Serie s where s.publisher.id = p.id)), avg(1.0 * (select count(s.publisher) from Serie s where s.publisher.id = p.id)), stddev(1.0 * (select count(s.publisher) from Serie s where s.publisher.id = p.id)) from Publisher p")
	Double[] getSeriesPerPublisherStatistics();

}
