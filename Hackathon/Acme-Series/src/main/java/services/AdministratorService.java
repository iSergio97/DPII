/*
 * AdministratorService.java
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import domain.Administrator;
import forms.RegisterAdministratorForm;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class AdministratorService extends AbstractService<AdministratorRepository, Administrator> {

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods
	@Autowired
	private UserAccountRepository userAccountRepository;


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

		final List<String> usernames = this.userAccountRepository.getUserNames();

		if (form.getId() == 0) {
			if (usernames.contains(form.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Administrator administrator3 = this.findPrincipal();
			usernames.remove(administrator3.getUserAccount().getUsername());
			if (usernames.contains(form.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (form.getUsername().length() < 5 || form.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!form.getPassword().equals(form.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (form.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (form.getPhoneNumber().length() < 11) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

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
