/*
 * RookieService.java
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

import domain.CreditCard;
import domain.Rookie;
import forms.RegisterRookieForm;
import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class RookieService extends AbstractService<RookieRepository, Rookie> {

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator validator;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Rookie create() {
		final Rookie rookie = super.create();

		// create user account
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setUsername("");
		userAccount.setPassword("");

		// set fields
		rookie.setName("");
		rookie.setSurnames("");
		rookie.setVat("");
		rookie.setPhoto("");
		rookie.setEmail("");
		rookie.setPhoneNumber("");
		rookie.setAddress("");
		rookie.setIsFlagged(false);
		rookie.setIsBanned(false);
		// set relationships
		rookie.setUserAccount(userAccount);
		rookie.setCreditCard(null);

		return rookie;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterRookieForm createForm() {
		final RegisterRookieForm rookieForm = new RegisterRookieForm();

		rookieForm.setName("");
		rookieForm.setSurnames("");
		rookieForm.setVat("");
		rookieForm.setEmail("");
		rookieForm.setPhoto("");
		rookieForm.setPhoneNumber("");
		rookieForm.setAddress("");
		rookieForm.setUsername("");
		rookieForm.setPassword("");
		rookieForm.setConfirmPassword("");
		rookieForm.setHolder("");
		rookieForm.setBrand("");
		rookieForm.setNumber("");
		final Date date = new Date();
		rookieForm.setExpirationMonth(date.getMonth() + 1);
		rookieForm.setExpirationYear(date.getYear() % 100);
		rookieForm.setCVV(100);
		return rookieForm;
	}

	public Rookie reconstructForm(final RegisterRookieForm rookieForm, final BindingResult bindingResult) {
		final Rookie result;

		if (rookieForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(rookieForm.getId());

		result.setName(rookieForm.getName());
		result.setSurnames(rookieForm.getSurnames());
		result.setVat(rookieForm.getVat());
		result.setEmail(rookieForm.getEmail());
		result.setPhoto(rookieForm.getPhoto());
		result.setPhoneNumber(rookieForm.getPhoneNumber());
		result.setAddress(rookieForm.getAddress());

		result.getUserAccount().setUsername(rookieForm.getUsername());
		result.getUserAccount().setPassword(rookieForm.getPassword());

		final CreditCard cc = new CreditCard();
		cc.setHolder(rookieForm.getHolder());
		cc.setBrand(rookieForm.getBrand());
		cc.setNumber(rookieForm.getNumber());
		cc.setExpirationMonth(rookieForm.getExpirationMonth());
		cc.setExpirationYear(rookieForm.getExpirationYear());
		cc.setCVV(rookieForm.getCVV());

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);
		this.validator.validate(result, bindingResult);
		//		this.rookieRepository.flush();

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}

	public RegisterRookieForm deconstruct(final Rookie rookie) {
		final RegisterRookieForm rookieForm = this.createForm();

		rookieForm.setId(rookie.getId());
		rookieForm.setName(rookie.getName());
		rookieForm.setSurnames(rookie.getSurnames());
		rookieForm.setVat(rookie.getVat());
		rookieForm.setPhoto(rookie.getPhoto());
		rookieForm.setEmail(rookie.getEmail());
		rookieForm.setPhoneNumber(rookie.getPhoneNumber());
		rookieForm.setAddress(rookie.getAddress());
		rookieForm.setUsername(rookie.getUserAccount().getUsername());
		rookieForm.setHolder(rookie.getCreditCard().getHolder());
		rookieForm.setBrand(rookie.getCreditCard().getBrand());
		rookieForm.setNumber(rookie.getCreditCard().getNumber());
		rookieForm.setExpirationMonth(rookie.getCreditCard().getExpirationMonth());
		rookieForm.setExpirationYear(rookie.getCreditCard().getExpirationYear());
		rookieForm.setCVV(rookie.getCreditCard().getCVV());

		return rookieForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Rookie findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Rookie findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
