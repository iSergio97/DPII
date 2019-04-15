/*
 * PositionDataService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PositionDataRepository;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService extends AbstractService<PositionDataRepository, PositionData> {

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public PositionData copy(final PositionData positionData) {
		final PositionData copy = this.create();
		copy.setTitle(positionData.getTitle());
		copy.setDescription(positionData.getDescription());
		copy.setStartDate(positionData.getStartDate());
		copy.setEndDate(positionData.getEndDate());
		return this.save(copy);
	}

}
