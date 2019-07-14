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
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import domain.CreditCard;
import domain.Rookie;
import forms.RegisterRookieForm;
import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class RookieService extends AbstractService<RookieRepository, Rookie> {

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountRepository	userAccountRepository;


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
		rookieForm.setCVV("");
		return rookieForm;
	}

	public Rookie reconstructForm(final RegisterRookieForm rookieForm, final BindingResult bindingResult) {
		final Rookie result;

		final List<String> usernames = this.userAccountRepository.getUserNames();
		final Date date = new Date();

		if (rookieForm.getExpirationMonth() == null || rookieForm.getExpirationYear() == null) {
			if (rookieForm.getExpirationMonth() == null) {
				final ObjectError error = new ObjectError("expirationMonthNull", "The month of the credit card is null");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationMonth", "error.monthNull");
			}

			if (rookieForm.getExpirationYear() == null) {
				final ObjectError error = new ObjectError("expirationYearNull", "The year of the credit card is nuññ");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationYear", "error.yearNull");
			}
		} else if (rookieForm.getExpirationYear() < (date.getYear() % 100) || rookieForm.getExpirationYear() == (date.getYear() % 100) && rookieForm.getExpirationMonth() < (date.getMonth() + 1)) {
			if (rookieForm.getExpirationYear() < (date.getYear() % 100)) {
				final ObjectError error = new ObjectError("expirationYear", "The year of the credit card is older than the current year");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationYear", "error.oldYear");
			}
			if (rookieForm.getExpirationYear() == (date.getYear() % 100) && rookieForm.getExpirationMonth() < (date.getMonth() + 1)) {
				final ObjectError error = new ObjectError("expirationMonth", "The month of the credit card is older than the current month");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationMonth", "error.oldMonth");
			}
		}
		if (rookieForm.getId() == 0) {
			if (usernames.contains(rookieForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Rookie rookie3 = this.findPrincipal();
			usernames.remove(rookie3.getUserAccount().getUsername());
			if (usernames.contains(rookieForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (rookieForm.getUsername().length() < 5 || rookieForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!rookieForm.getPassword().equals(rookieForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (rookieForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (rookieForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		if (rookieForm.getCVV() == "") {
			final ObjectError error = new ObjectError("CVV", "nullCvv");
			bindingResult.addError(error);
			bindingResult.rejectValue("CVV", "error.nullCvv");
		} else if (Integer.valueOf(rookieForm.getCVV()) < 100) {
			final ObjectError error = new ObjectError("CVV", "shortCvv");
			bindingResult.addError(error);
			bindingResult.rejectValue("CVV", "error.shortCvv");
		}

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

		this.validator.validate(result, bindingResult);

		final CreditCard cc = new CreditCard();
		cc.setHolder(rookieForm.getHolder());
		cc.setBrand(rookieForm.getBrand());
		cc.setNumber(rookieForm.getNumber());
		cc.setExpirationMonth(rookieForm.getExpirationMonth());
		cc.setExpirationYear(rookieForm.getExpirationYear());
		cc.setCVV(Integer.valueOf(rookieForm.getCVV()));

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);

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
		rookieForm.setCVV(String.valueOf(rookie.getCreditCard().getCVV()));

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
