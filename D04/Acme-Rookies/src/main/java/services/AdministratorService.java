/*
 * AdministratorService.java
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

import domain.Administrator;
import domain.CreditCard;
import forms.RegisterAdministratorForm;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService extends AbstractService<AdministratorRepository, Administrator> {

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
		administratorForm.setExpirationMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
		administratorForm.setExpirationYear(Calendar.getInstance().get(Calendar.YEAR) % 100);

		return administratorForm;
	}

	public Administrator reconstructForm(final RegisterAdministratorForm administratorForm, final BindingResult bindingResult) {
		final Administrator result;

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
		result.getUserAccount().setPassword(administratorForm.getPassword());

		final CreditCard cc = new CreditCard();
		cc.setHolder(administratorForm.getHolder());
		cc.setBrand(administratorForm.getBrand());
		cc.setNumber(administratorForm.getNumber());
		cc.setExpirationMonth(administratorForm.getExpirationMonth());
		cc.setExpirationYear(administratorForm.getExpirationYear());
		cc.setCVV(Integer.valueOf(administratorForm.getCVV()));

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);
		this.validator.validate(result, bindingResult);
		this.repository.flush();

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
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
