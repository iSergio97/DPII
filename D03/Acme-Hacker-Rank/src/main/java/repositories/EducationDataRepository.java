/*
 * EducationDataRepository.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationData;

@Repository
public interface EducationDataRepository extends JpaRepository<EducationData, Integer> {

	@Query("select ed from EducationData ed where ed.curriculum.id = ?1")
	public EducationData findByCurriculum(int curriculumId);
}
