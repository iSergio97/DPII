/*
 * HackerService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utilities.ConversionUtils;
import domain.Application;
import domain.Hacker;
import domain.Message;
import domain.SocialProfile;
import forms.HackerForm;

@Service
@Transactional
public class HackerService extends AbstractService<HackerRepository, Hacker> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private UserAccountRepository	userAccountRepository;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Hacker create() {
		final Hacker hacker = new Hacker();

		// create user account
		UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount = this.userAccountRepository.save(userAccount);
		// set fields
		hacker.setName("");
		hacker.setSurnames(new ArrayList<String>());
		hacker.setVat("");
		hacker.setPhoto("");
		hacker.setEmail("");
		hacker.setPhoneNumber("");
		hacker.setAddress("");
		hacker.setIsSpammer(false);
		hacker.setIsBanned(false);
		// set relationships
		hacker.setUserAccount(userAccount);
		hacker.setCreditCard(null);
		hacker.setMessagePool(new ArrayList<Message>());
		hacker.setSocialProfiles(new ArrayList<SocialProfile>());
		hacker.setCurriculum(null);
		hacker.setApplications(new ArrayList<Application>());

		return hacker;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public HackerForm createForm() {
		final HackerForm hackerForm = new HackerForm();

		hackerForm.setName("");
		hackerForm.setSurnames("");
		hackerForm.setVat("");
		hackerForm.setPhoto("");
		hackerForm.setEmail("");
		hackerForm.setPhoneNumber("");
		hackerForm.setAddress("");
		hackerForm.setUsername("");
		hackerForm.setPassword("");
		hackerForm.setConfirmPassword("");

		return hackerForm;
	}

	public Hacker reconstructForm(final HackerForm hackerForm, final BindingResult bindingResult) {
		final Hacker result;

		if (hackerForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(hackerForm.getId());

		result.setName(hackerForm.getName());
		result.setVat(hackerForm.getVat());
		result.setSurnames(ConversionUtils.stringToList(hackerForm.getSurnames(), ","));
		result.setPhoto(hackerForm.getPhoto());
		result.setEmail(hackerForm.getEmail());
		result.setPhoneNumber(hackerForm.getPhoneNumber());
		result.setAddress(hackerForm.getAddress());

		this.validator.validate(result, bindingResult);

		return result;
	}

	public HackerForm deconstruct(final Hacker hacker) {
		final HackerForm hackerForm = this.createForm();

		hackerForm.setId(hacker.getId());
		hackerForm.setName(hacker.getName());
		hackerForm.setSurnames(ConversionUtils.listToString(hacker.getSurnames(), ","));
		hackerForm.setVat(hacker.getVat());
		hackerForm.setPhoto(hacker.getPhoto());
		hackerForm.setEmail(hacker.getEmail());
		hackerForm.setPhoneNumber(hacker.getPhoneNumber());
		hackerForm.setAddress(hacker.getAddress());
		hackerForm.setUsername(hacker.getUserAccount().getUsername());

		return hackerForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Hacker findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Hacker findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
