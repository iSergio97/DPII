/*
 * UserService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.User;
import forms.RegisterUserForm;

@Service
@Transactional
public class UserService extends AbstractService<UserRepository, User> {

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Autowired
	private UserAccountRepository	userAccountRepository;


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

		final List<String> usernames = this.userAccountRepository.getUserNames();

		if (userForm.getId() == 0) {
			if (usernames.contains(userForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final User user3 = this.findPrincipal();
			usernames.remove(user3.getUserAccount().getUsername());
			if (usernames.contains(userForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (userForm.getUsername().length() < 5 || userForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (userForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (userForm.getPhoneNumber().length() < 11) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

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
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
			return null;
		else {
			final UserAccount userAccount = LoginService.getPrincipal();
			for (final Authority authority : userAccount.getAuthorities())
				if (authority.getAuthority().equals(Authority.USER))
					return this.findByUserAccountId(userAccount.getId());
			return null;
		}
	}

}
