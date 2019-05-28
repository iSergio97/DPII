/*
 * CommentRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends AbstractRepository<Comment> {

	@Query("select min(1.0 * (select count(c.serie) from Comment c where c.serie.id = s.id)), max(1.0 * (select count(c.serie) from Comment c where c.serie.id = s.id)), avg(1.0 * (select count(c.serie) from Comment c where c.serie.id = s.id)), stddev(1.0 * (select count(c.serie) from Comment c where c.serie.id = s.id)) from Serie s")
	Double[] getCommentsPerSerieStatistics();

}
