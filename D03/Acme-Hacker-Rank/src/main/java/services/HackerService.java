/*
 * HackerService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Hacker;
import forms.RegisterHackerForm;

@Service
@Transactional
public class HackerService extends AbstractService<HackerRepository, Hacker> {

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator	validator;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Hacker create() {
		final Hacker hacker = super.create();

		// create user account
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setUsername("");
		userAccount.setPassword("");

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
		final Date date = new Date();
		hackerForm.setExpirationMonth(date.getMonth() + 1);
		hackerForm.setExpirationYear(date.getYear() % 100);
		hackerForm.setCVV(100);
		return hackerForm;
	}

	public Hacker reconstructForm(final RegisterHackerForm hackerForm, final BindingResult bindingResult) {
		final Hacker result;

		if (hackerForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(hackerForm.getId());

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
		hackerForm.setCVV(hacker.getCreditCard().getCVV());

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
