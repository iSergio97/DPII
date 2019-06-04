/*
 * ChapterRepository.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;

@Repository
public interface ChapterRepository extends AbstractRepository<Chapter> {

	@Query("select s.chapters from Season s where s.id = ?1")
	Collection<Chapter> findChaptersBySeasonId(int id);
}
