/*
 * AuditorService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Auditor;
import domain.CreditCard;
import forms.RegisterAuditorForm;

@Service
@Transactional
public class AuditorService extends AbstractService<AuditorRepository, Auditor> {

	////////////////////////////////////////////////////////////////////////////////

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// CRUD methods

	@Override
	public Auditor create() {
		final Auditor auditor = super.create();

		// create user account
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setUsername("");
		userAccount.setPassword("");
		auditor.setUserAccount(userAccount);
		// set fields
		auditor.setName("");
		auditor.setSurnames("");
		auditor.setVat("");
		auditor.setEmail("");
		auditor.setCreditCard(null);
		auditor.setPhoto("");
		auditor.setPhoneNumber("");
		auditor.setAddress("");
		auditor.setIsFlagged(false);
		auditor.setIsBanned(false);

		// set relationships
		return auditor;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterAuditorForm createForm() {
		final RegisterAuditorForm auditorForm = new RegisterAuditorForm();

		auditorForm.setName("");
		auditorForm.setSurnames("");
		auditorForm.setVat("");
		auditorForm.setPhoto("");
		auditorForm.setEmail("");
		auditorForm.setPhoneNumber("");
		auditorForm.setAddress("");
		auditorForm.setUsername("");
		auditorForm.setPassword("");
		auditorForm.setConfirmPassword("");
		auditorForm.setCVV("");
		auditorForm.setBrand("");
		auditorForm.setHolder("");
		auditorForm.setExpirationMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
		auditorForm.setExpirationYear(Calendar.getInstance().get(Calendar.YEAR) % 100);

		return auditorForm;
	}

	public Auditor reconstructForm(final RegisterAuditorForm auditorForm, final BindingResult bindingResult) {
		final Auditor result;

		final List<String> usernames = this.userAccountRepository.getUserNames();

		final Date date = new Date();

		if (auditorForm.getExpirationMonth() == null || auditorForm.getExpirationYear() == null) {
			if (auditorForm.getExpirationMonth() == null) {
				final ObjectError error = new ObjectError("expirationMonthNull", "The month of the credit card is null");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationMonth", "error.monthNull");
			}

			if (auditorForm.getExpirationYear() == null) {
				final ObjectError error = new ObjectError("expirationYearNull", "The year of the credit card is nuññ");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationYear", "error.yearNull");
			}
		} else if (auditorForm.getExpirationYear() < (date.getYear() % 100) || auditorForm.getExpirationYear() == (date.getYear() % 100) && auditorForm.getExpirationMonth() < (date.getMonth() + 1)) {
			if (auditorForm.getExpirationYear() < (date.getYear() % 100)) {
				final ObjectError error = new ObjectError("expirationYear", "The year of the credit card is older than the current year");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationYear", "error.oldYear");
			}
			if (auditorForm.getExpirationYear() == (date.getYear() % 100) && auditorForm.getExpirationMonth() < (date.getMonth() + 1)) {
				final ObjectError error = new ObjectError("expirationMonth", "The month of the credit card is older than the current month");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationMonth", "error.oldMonth");
			}
		}

		if (auditorForm.getId() == 0) {
			if (usernames.contains(auditorForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Auditor auditor3 = this.findPrincipal();
			usernames.remove(auditor3.getUserAccount().getUsername());
			if (usernames.contains(auditorForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (auditorForm.getUsername().length() < 5 || auditorForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!auditorForm.getPassword().equals(auditorForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (auditorForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (auditorForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		if (auditorForm.getCVV() == "") {
			final ObjectError error = new ObjectError("CVV", "nullCvv");
			bindingResult.addError(error);
			bindingResult.rejectValue("CVV", "error.nullCvv");
		} else if (Integer.valueOf(auditorForm.getCVV()) < 100) {
			final ObjectError error = new ObjectError("CVV", "shortCvv");
			bindingResult.addError(error);
			bindingResult.rejectValue("CVV", "error.shortCvv");
		}

		if (auditorForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(auditorForm.getId());

		result.setName(auditorForm.getName());
		result.setSurnames(auditorForm.getSurnames());
		result.setVat(auditorForm.getVat());
		result.setSurnames(auditorForm.getSurnames());
		result.setPhoto(auditorForm.getPhoto());
		result.setEmail(auditorForm.getEmail());
		result.setPhoneNumber(auditorForm.getPhoneNumber());
		result.setAddress(auditorForm.getAddress());

		result.getUserAccount().setUsername(auditorForm.getUsername());
		result.getUserAccount().setPassword(auditorForm.getPassword());

		this.validator.validate(result, bindingResult);

		final CreditCard cc = new CreditCard();
		cc.setHolder(auditorForm.getHolder());
		cc.setBrand(auditorForm.getBrand());
		cc.setNumber(auditorForm.getNumber());
		cc.setExpirationMonth(auditorForm.getExpirationMonth());
		cc.setExpirationYear(auditorForm.getExpirationYear());
		cc.setCVV(Integer.valueOf(auditorForm.getCVV()));

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}
	public RegisterAuditorForm deconstruct(final Auditor auditor) {
		final RegisterAuditorForm auditorForm = this.createForm();

		auditorForm.setId(auditor.getId());
		auditorForm.setName(auditor.getName());
		auditorForm.setSurnames(auditor.getSurnames());
		auditorForm.setVat(auditor.getVat());
		auditorForm.setPhoto(auditor.getPhoto());
		auditorForm.setEmail(auditor.getEmail());
		auditorForm.setPhoneNumber(auditor.getPhoneNumber());
		auditorForm.setAddress(auditor.getAddress());

		auditorForm.setUsername(auditor.getUserAccount().getUsername());
		auditorForm.setPassword(auditor.getUserAccount().getPassword());

		auditorForm.setHolder(auditor.getCreditCard().getHolder());
		auditorForm.setBrand(auditor.getCreditCard().getBrand());
		auditorForm.setNumber(auditor.getCreditCard().getNumber());
		auditorForm.setExpirationMonth(auditor.getCreditCard().getExpirationMonth());
		auditorForm.setExpirationYear(auditor.getCreditCard().getExpirationYear());
		auditorForm.setCVV(String.valueOf(auditor.getCreditCard().getCVV()));

		return auditorForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Auditor findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Auditor findPrincipal() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
			return null;
		else {
			final UserAccount userAccount = LoginService.getPrincipal();
			for (final Authority authority : userAccount.getAuthorities())
				if (authority.getAuthority().equals(Authority.AUDITOR))
					return this.findByUserAccountId(userAccount.getId());
			return null;
		}
	}

}
