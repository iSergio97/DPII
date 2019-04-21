/*
 * HackerService.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.CreditCard;
import domain.Hacker;
import forms.RegisterHackerForm;
import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class HackerService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private HackerRepository		hackerRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private UserAccountRepository	userAccountRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator				validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public HackerService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

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
		hacker.setSurnames("");
		hacker.setVat("");
		hacker.setPhoto("");
		hacker.setEmail("");
		hacker.setPhoneNumber("");
		hacker.setAddress("");
		hacker.setIsFlagged(false);
		hacker.setIsBanned(false);
		// set relationships
		hacker.setUserAccount(userAccount);
		hacker.setCreditCard(null);

		return hacker;
	}

	public Hacker save(final Hacker hacker) {
		Assert.isTrue(hacker != null);
		return this.hackerRepository.save(hacker);
	}

	public Iterable<Hacker> save(final Iterable<Hacker> hackers) {
		Assert.isTrue(hackers != null);
		return this.hackerRepository.save(hackers);
	}

	public void delete(final Hacker hacker) {
		Assert.isTrue(hacker != null);
		this.hackerRepository.delete(hacker);
	}

	public void delete(final Iterable<Hacker> hackers) {
		Assert.isTrue(hackers != null);
		this.hackerRepository.delete(hackers);
	}

	public Hacker findOne(final int id) {
		return this.hackerRepository.findOne(id);
	}

	public List<Hacker> findAll() {
		return this.hackerRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterHackerForm createForm() {
		final RegisterHackerForm hackerForm = new RegisterHackerForm();

		hackerForm.setName("");
		hackerForm.setSurnames("");
		hackerForm.setVat("");
		hackerForm.setEmail("");
		hackerForm.setPhoto("");
		hackerForm.setPhoneNumber("");
		hackerForm.setAddress("");
		hackerForm.setUsername("");
		hackerForm.setPassword("");
		hackerForm.setConfirmPassword("");
		hackerForm.setHolder("");
		hackerForm.setBrand("");
		hackerForm.setNumber("");
		hackerForm.setExpirationMonth(1);
		hackerForm.setExpirationYear(18);
		hackerForm.setCVV(100);
		return hackerForm;
	}

	public Hacker reconstructForm(final RegisterHackerForm hackerForm, final BindingResult bindingResult) {
		final Hacker result;

		if (hackerForm.getId() == 0)
			result = this.create();
		else
			result = this.hackerRepository.findOne(hackerForm.getId());

		result.setName(hackerForm.getName());
		result.setSurnames(hackerForm.getSurnames());
		result.setVat(hackerForm.getVat());
		result.setEmail(hackerForm.getEmail());
		result.setPhoto(hackerForm.getPhoto());
		result.setPhoneNumber(hackerForm.getPhoneNumber());
		result.setAddress(hackerForm.getAddress());

		result.getUserAccount().setUsername(hackerForm.getUsername());
		result.getUserAccount().setPassword(hackerForm.getPassword());

		final CreditCard cc = new CreditCard();
		cc.setHolder(hackerForm.getHolder());
		cc.setBrand(hackerForm.getBrand());
		cc.setNumber(hackerForm.getNumber());
		cc.setExpirationMonth(hackerForm.getExpirationMonth());
		cc.setExpirationYear(hackerForm.getExpirationYear());
		cc.setCVV(hackerForm.getCVV());

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);
		this.validator.validate(result, bindingResult);
		//		this.hackerRepository.flush();

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}

	public RegisterHackerForm deconstruct(final Hacker hacker) {
		final RegisterHackerForm hackerForm = this.createForm();

		hackerForm.setId(hacker.getId());
		hackerForm.setName(hacker.getName());
		hackerForm.setSurnames(hacker.getSurnames());
		hackerForm.setVat(hacker.getVat());
		hackerForm.setPhoto(hacker.getPhoto());
		hackerForm.setEmail(hacker.getEmail());
		hackerForm.setPhoneNumber(hacker.getPhoneNumber());
		hackerForm.setAddress(hacker.getAddress());
		hackerForm.setUsername(hacker.getUserAccount().getUsername());
		hackerForm.setHolder(hacker.getCreditCard().getHolder());
		hackerForm.setBrand(hacker.getCreditCard().getBrand());
		hackerForm.setNumber(hacker.getCreditCard().getNumber());
		hackerForm.setExpirationMonth(hacker.getCreditCard().getExpirationMonth());
		hackerForm.setExpirationYear(hacker.getCreditCard().getExpirationYear());

		return hackerForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Hacker findByUserAccountId(final int id) {
		return this.hackerRepository.findByUserAccountId(id);
	}

	public Hacker findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
