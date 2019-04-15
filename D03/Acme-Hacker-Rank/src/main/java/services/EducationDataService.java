/*
 * EducationDataService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EducationDataRepository;
import domain.EducationData;

@Service
@Transactional
public class EducationDataService extends AbstractService<EducationDataRepository, EducationData> {

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public EducationData copy(final EducationData educationData) {
		final EducationData copy = this.create();
		copy.setDegree(educationData.getDegree());
		copy.setInstitution(educationData.getInstitution());
		copy.setMark(educationData.getMark());
		copy.setStartDate(educationData.getStartDate());
		copy.setEndDate(educationData.getEndDate());
		return this.save(copy);
	}

}
