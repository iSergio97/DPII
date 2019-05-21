/*
 * SerieRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Serie;

@Repository
public interface SerieRepository extends AbstractRepository<Serie> {

	@Query("select min(s.seasons.size) * 1.0, max(s.seasons.size) * 1.0, avg(s.seasons.size), stddev(s.seasons.size) from Serie s")
	Double[] getSeasonsPerSerieStatistics();

	@Query("select min(c.score) * 1.0, max(c.score) * 1.0, avg(c.score), stddev(c.score) from Critique c where c.serie.id = ?1")
	Double[] getSerieCritiqueScoreStatistics(int serieId);

	@Query("select distinct c.serie, count(c) from Comment c group by c.serie order by count(c) desc")
	List<Object[]> getSeriesSortedByNumberOfCommentsDescending();

	@Query("select distinct c.serie, avg(c.score) from Critique c group by c.serie order by avg(c.score) desc")
	List<Object[]> getSeriesSortedByCritiqueScoreDescending();

}
