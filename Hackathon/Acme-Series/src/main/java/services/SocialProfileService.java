/*
 * SocialProfileService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;
import forms.SocialProfileForm;

@Service
@Transactional
public class SocialProfileService extends AbstractService<SocialProfileRepository, SocialProfile> {

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public SocialProfileForm createForm() {
		return this.instanceClass(SocialProfileForm.class);
	}

	public SocialProfile reconstructForm(final SocialProfileForm socialProfileForm, final BindingResult bindingResult) {
		final SocialProfile socialProfile;

		if (socialProfileForm.getId() == 0)
			socialProfile = this.create();
		else
			socialProfile = this.findOne(socialProfileForm.getId());

		socialProfile.setNick(socialProfileForm.getNick());
		socialProfile.setSocialNetworkName(socialProfileForm.getSocialNetworkName());
		socialProfile.setProfileLink(socialProfileForm.getProfileLink());

		this.validator.validate(socialProfile, bindingResult);

		return socialProfile;
	}

	public SocialProfileForm deconstruct(final SocialProfile socialProfile) {
		final SocialProfileForm socialProfileForm = this.createForm();

		socialProfileForm.setId(socialProfile.getId());
		socialProfileForm.setNick(socialProfile.getNick());
		socialProfileForm.setSocialNetworkName(socialProfile.getSocialNetworkName());
		socialProfileForm.setProfileLink(socialProfile.getProfileLink());

		return socialProfileForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Collection<SocialProfile> findByActor(final Actor actor) {
		return this.repository.findByActorId(actor.getId());
	}

}
