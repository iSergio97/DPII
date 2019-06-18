/*
 * AdministratorService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Administrator;
import domain.CreditCard;
import forms.RegisterAdministratorForm;

@Service
@Transactional
public class AdministratorService extends AbstractService<AdministratorRepository, Administrator> {

	@Autowired
	private UserAccountRepository	userAccountRepository;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Administrator create() {
		final Administrator administrator = super.create();

		// create user account
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setUsername("");
		userAccount.setPassword("");
		administrator.setUserAccount(userAccount);
		// set fields
		administrator.setName("");
		administrator.setSurnames("");
		administrator.setVat("");
		administrator.setEmail("");
		administrator.setCreditCard(null);
		administrator.setPhoto("");
		administrator.setPhoneNumber("");
		administrator.setAddress("");
		administrator.setIsFlagged(false);
		administrator.setIsBanned(false);

		// set relationships
		return administrator;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterAdministratorForm createForm() {
		final RegisterAdministratorForm administratorForm = new RegisterAdministratorForm();

		administratorForm.setName("");
		administratorForm.setSurnames("");
		administratorForm.setVat("");
		administratorForm.setPhoto("");
		administratorForm.setEmail("");
		administratorForm.setPhoneNumber("");
		administratorForm.setAddress("");
		administratorForm.setUsername("");
		administratorForm.setPassword("");
		administratorForm.setConfirmPassword("");
		administratorForm.setCVV("");
		administratorForm.setBrand("");
		administratorForm.setHolder("");

		return administratorForm;
	}

	public Administrator reconstructForm(final RegisterAdministratorForm administratorForm, final BindingResult bindingResult) {
		final Administrator result;

		final List<String> usernames = this.userAccountRepository.getUserNames();

		final Date date = new Date();

		if (administratorForm.getExpirationMonth() == null || administratorForm.getExpirationYear() == null) {
			if (administratorForm.getExpirationMonth() == null) {
				final ObjectError error = new ObjectError("expirationMonthNull", "The month of the credit card is null");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationMonth", "error.monthNull");
			}

			if (administratorForm.getExpirationYear() == null) {
				final ObjectError error = new ObjectError("expirationYearNull", "The year of the credit card is nuññ");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationYear", "error.yearNull");
			}
		} else if (administratorForm.getExpirationYear() < (date.getYear() % 100) || administratorForm.getExpirationYear() == (date.getYear() % 100) && administratorForm.getExpirationMonth() < (date.getMonth() + 1)) {
			if (administratorForm.getExpirationYear() < (date.getYear() % 100)) {
				final ObjectError error = new ObjectError("expirationYear", "The year of the credit card is older than the current year");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationYear", "error.oldYear");
			}
			if (administratorForm.getExpirationYear() == (date.getYear() % 100) && administratorForm.getExpirationMonth() < (date.getMonth() + 1)) {
				final ObjectError error = new ObjectError("expirationMonth", "The month of the credit card is older than the current month");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationMonth", "error.oldMonth");
			}
		}

		if (administratorForm.getId() == 0) {
			if (usernames.contains(administratorForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Administrator administrator3 = this.findPrincipal();
			usernames.remove(administrator3.getUserAccount().getUsername());
			if (usernames.contains(administratorForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (administratorForm.getUsername().length() < 5 || administratorForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!administratorForm.getPassword().equals(administratorForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (administratorForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (administratorForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		if (administratorForm.getCVV() == "") {
			final ObjectError error = new ObjectError("CVV", "nullCvv");
			bindingResult.addError(error);
			bindingResult.rejectValue("CVV", "error.nullCvv");
		} else if (Integer.valueOf(administratorForm.getCVV()) < 100) {
			final ObjectError error = new ObjectError("CVV", "shortCvv");
			bindingResult.addError(error);
			bindingResult.rejectValue("CVV", "error.shortCvv");
		}

		if (administratorForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(administratorForm.getId());

		result.setName(administratorForm.getName());
		result.setSurnames(administratorForm.getSurnames());
		result.setVat(administratorForm.getVat());
		result.setSurnames(administratorForm.getSurnames());
		result.setPhoto(administratorForm.getPhoto());
		result.setEmail(administratorForm.getEmail());
		result.setPhoneNumber(administratorForm.getPhoneNumber());
		result.setAddress(administratorForm.getAddress());

		result.getUserAccount().setUsername(administratorForm.getUsername());
		result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(administratorForm.getPassword(), null));

		this.validator.validate(result, bindingResult);

		final CreditCard cc = new CreditCard();
		cc.setHolder(administratorForm.getHolder());
		cc.setBrand(administratorForm.getBrand());
		cc.setNumber(administratorForm.getNumber());
		cc.setExpirationMonth(administratorForm.getExpirationMonth());
		cc.setExpirationYear(administratorForm.getExpirationYear());
		cc.setCVV(Integer.valueOf(administratorForm.getCVV()));

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}
	public RegisterAdministratorForm deconstruct(final Administrator administrator) {
		final RegisterAdministratorForm administratorForm = this.createForm();

		administratorForm.setId(administrator.getId());
		administratorForm.setName(administrator.getName());
		administratorForm.setSurnames(administrator.getSurnames());
		administratorForm.setVat(administrator.getVat());
		administratorForm.setPhoto(administrator.getPhoto());
		administratorForm.setEmail(administrator.getEmail());
		administratorForm.setPhoneNumber(administrator.getPhoneNumber());
		administratorForm.setAddress(administrator.getAddress());

		administratorForm.setUsername(administrator.getUserAccount().getUsername());
		administratorForm.setPassword(administrator.getUserAccount().getPassword());

		administratorForm.setHolder(administrator.getCreditCard().getHolder());
		administratorForm.setBrand(administrator.getCreditCard().getBrand());
		administratorForm.setNumber(administrator.getCreditCard().getNumber());
		administratorForm.setExpirationMonth(administrator.getCreditCard().getExpirationMonth());
		administratorForm.setExpirationYear(administrator.getCreditCard().getExpirationYear());
		administratorForm.setCVV(String.valueOf(administrator.getCreditCard().getCVV()));

		return administratorForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Administrator findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Administrator findPrincipal() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
			return null;
		else {
			final UserAccount userAccount = LoginService.getPrincipal();
			for (final Authority authority : userAccount.getAuthorities())
				if (authority.getAuthority().equals(Authority.ADMINISTRATOR))
					return this.findByUserAccountId(userAccount.getId());
			return null;
		}
	}

}
