/*
 * SocialProfileService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SocialProfileRepository;
import domain.SocialProfile;
import forms.SocialProfileForm;

@Service
@Transactional
public class SocialProfileService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator				validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public SocialProfileService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public SocialProfile create() {
		final SocialProfile socialProfile = new SocialProfile();

		socialProfile.setNick("");
		socialProfile.setSocialNetworkName("");
		socialProfile.setProfileLink("");

		return socialProfile;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.isTrue(socialProfile != null);
		return this.socialProfileRepository.save(socialProfile);
	}

	public Iterable<SocialProfile> save(final Iterable<SocialProfile> socialProfiles) {
		Assert.isTrue(socialProfiles != null);
		return this.socialProfileRepository.save(socialProfiles);
	}

	public void delete(final SocialProfile socialProfile) {
		Assert.isTrue(socialProfile != null);
		this.socialProfileRepository.delete(socialProfile);
	}

	public void delete(final Iterable<SocialProfile> socialProfiles) {
		Assert.isTrue(socialProfiles != null);
		this.socialProfileRepository.delete(socialProfiles);
	}

	public SocialProfile findOne(final int id) {
		return this.socialProfileRepository.findOne(id);
	}

	public List<SocialProfile> findAll() {
		return this.socialProfileRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public SocialProfileForm createForm() {
		final SocialProfileForm socialProfileForm = new SocialProfileForm();

		socialProfileForm.setNick("");
		socialProfileForm.setSocialNetworkName("");
		socialProfileForm.setProfileLink("");

		return socialProfileForm;
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

}
