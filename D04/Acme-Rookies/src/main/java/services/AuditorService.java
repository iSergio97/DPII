/*
 * AuditorService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import domain.Auditor;
import domain.CreditCard;
import forms.RegisterAuditorForm;
import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AuditorService extends AbstractService<AuditorRepository, Auditor> {

	////////////////////////////////////////////////////////////////////////////////
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

		final CreditCard cc = new CreditCard();
		cc.setHolder(auditorForm.getHolder());
		cc.setBrand(auditorForm.getBrand());
		cc.setNumber(auditorForm.getNumber());
		cc.setExpirationMonth(auditorForm.getExpirationMonth());
		cc.setExpirationYear(auditorForm.getExpirationYear());
		cc.setCVV(Integer.valueOf(auditorForm.getCVV()));

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);
		this.validator.validate(result, bindingResult);
		this.repository.flush();

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
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
