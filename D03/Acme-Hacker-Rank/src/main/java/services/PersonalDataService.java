/*
 * PersonalDataService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PersonalDataRepository;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService extends AbstractService<PersonalDataRepository, PersonalData> {

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public PersonalData copy(final PersonalData personalData) {
		final PersonalData copy = this.create();
		copy.setFullName(personalData.getFullName());
		copy.setStatement(personalData.getStatement());
		copy.setPhoneNumber(personalData.getPhoneNumber());
		copy.setGitHubProfile(personalData.getGitHubProfile());
		copy.setLinkedInProfile(personalData.getLinkedInProfile());
		return this.save(copy);
	}

}
