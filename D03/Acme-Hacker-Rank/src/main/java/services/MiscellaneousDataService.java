/*
 * MiscellaneousDataService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MiscellaneousDataRepository;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService extends AbstractService<MiscellaneousDataRepository, MiscellaneousData> {

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public MiscellaneousData copy(final MiscellaneousData miscellaneousData) {
		final MiscellaneousData copy = this.create();
		copy.setFreeText(miscellaneousData.getFreeText());
		copy.setAttachments(miscellaneousData.getAttachments());
		return this.save(copy);
	}

}
