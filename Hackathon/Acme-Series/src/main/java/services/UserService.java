/*
 * UserService.java
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

import domain.User;
import forms.RegisterUserForm;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class UserService extends AbstractService<UserRepository, User> {

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public User create() {
		final User user = super.create();

		// create user account
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.USER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setUsername("");
		userAccount.setPassword("");
		user.setUserAccount(userAccount);

		return user;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterUserForm createForm() {
		return this.instanceClass(RegisterUserForm.class);
	}

	public User reconstructForm(final RegisterUserForm userForm, final BindingResult bindingResult) {
		final User result;

		if (userForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(userForm.getId());

		result.setName(userForm.getName());
		result.setSurnames(userForm.getSurnames());
		result.setPhoto(userForm.getPhoto());
		result.setEmail(userForm.getEmail());
		result.setPhoneNumber(userForm.getPhoneNumber());
		result.setAddress(userForm.getAddress());

		result.getUserAccount().setUsername(userForm.getUsername());
		result.getUserAccount().setPassword(userForm.getPassword());

		this.validator.validate(result, bindingResult);
		this.repository.flush();

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}
	public RegisterUserForm deconstruct(final User user) {
		final RegisterUserForm userForm = this.createForm();

		userForm.setId(user.getId());
		userForm.setName(user.getName());
		userForm.setSurnames(user.getSurnames());
		userForm.setPhoto(user.getPhoto());
		userForm.setEmail(user.getEmail());
		userForm.setPhoneNumber(user.getPhoneNumber());
		userForm.setAddress(user.getAddress());

		userForm.setUsername(user.getUserAccount().getUsername());
		userForm.setPassword(user.getUserAccount().getPassword());

		return userForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public User findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public User findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
