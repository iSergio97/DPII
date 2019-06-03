/*
 * SerieRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;
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

	@Query("select s from Serie s order by s.favouritedUsers.size desc")
	List<Serie> getSeriesSortedByNumberOfFavorites();

	@Query("select s from Serie s join s.favouritedUsers u where u.id = ?1")
	Collection<Serie> findFavouriteByUserId(int userId);

	@Query("select s from Serie s join s.pendingUsers u where u.id = ?1")
	Collection<Serie> findPendingByUserId(int userId);

	@Query("select s from Serie s join s.watchingUsers u where u.id = ?1")
	Collection<Serie> findWatchingByUserId(int userId);

	@Query("select s from Serie s join s.watchedUsers u where u.id = ?1")
	Collection<Serie> findWatchedByUserId(int userId);

	@Query("select s from Serie s where s.publisher.id = ?1")
	Collection<Serie> findByPublisherId(int publisherId);

	@Query("select s from Serie s where s.isDraft = false")
	Collection<Serie> findPublicSeries();

	@Query("select s from Serie s join s.seasons se where se.id = ?1")
	Serie findSerieAssociated(int seasonId);

	@Query("select s from Serie s where s.title like ?1 or s.description like ?1")
	Collection<Serie> searchQuery(String text);
}
