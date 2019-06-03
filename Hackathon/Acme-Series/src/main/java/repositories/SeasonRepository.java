/*
 * SeasonRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Season;

@Repository
public interface SeasonRepository extends AbstractRepository<Season> {

	@Query("select min(s.chapters.size) * 1.0, max(s.chapters.size) * 1.0, avg(s.chapters.size), stddev(s.chapters.size) from Season s")
	Double[] getChaptersPerSeasonStatistics();

	@Query("select s from Season s join s.chapters c where c.id = ?1")
	Season findSeasonAssociated(int chapterId);

}
