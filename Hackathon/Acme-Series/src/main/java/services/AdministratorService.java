/*
 * AdministratorService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import forms.RegisterAdministratorForm;

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

		return administrator;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterAdministratorForm createForm() {
		return this.instanceClass(RegisterAdministratorForm.class);
	}

	public Administrator reconstructForm(final RegisterAdministratorForm form, final BindingResult bindingResult) {
		final Administrator result;

		if (form.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(form.getId());

		result.setName(form.getName());
		result.setSurnames(form.getSurnames());
		result.setPhoto(form.getPhoto());
		result.setEmail(form.getEmail());
		result.setPhoneNumber(form.getPhoneNumber());
		result.setAddress(form.getAddress());

		result.getUserAccount().setUsername(form.getUsername());
		result.getUserAccount().setPassword(form.getPassword());

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
		administratorForm.setPhoto(administrator.getPhoto());
		administratorForm.setEmail(administrator.getEmail());
		administratorForm.setPhoneNumber(administrator.getPhoneNumber());
		administratorForm.setAddress(administrator.getAddress());

		administratorForm.setUsername(administrator.getUserAccount().getUsername());
		administratorForm.setPassword(administrator.getUserAccount().getPassword());

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
