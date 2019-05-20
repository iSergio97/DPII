/*
 * SeasonService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SeasonRepository;
import domain.Season;

@Service
@Transactional
public class SeasonService extends AbstractService<SeasonRepository, Season> {

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Double[] getChaptersPerSeasonStatistics() {
		return this.repository.getChaptersPerSeasonStatistics();
	}

}
