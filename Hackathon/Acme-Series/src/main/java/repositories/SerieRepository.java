/*
 * SerieRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Serie;

@Repository
public interface SerieRepository extends AbstractRepository<Serie> {

	@Query("select min(s.seasons.size) * 1.0, max(s.seasons.size) * 1.0, avg(s.seasons.size), stddev(s.seasons.size) from Serie s")
	Double[] getSeasonsPerSerieStatistics();

}
