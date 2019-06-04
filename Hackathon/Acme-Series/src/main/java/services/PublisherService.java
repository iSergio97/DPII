/*
 * PublisherService.java
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

import repositories.PublisherRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Publisher;
import forms.RegisterPublisherForm;

@Service
@Transactional
public class PublisherService extends AbstractService<PublisherRepository, Publisher> {

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Autowired
	private UserAccountRepository	userAccountRepository;


	@Override
	public Publisher create() {
		final Publisher publisher = super.create();

		// create user account
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.PUBLISHER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setUsername("");
		userAccount.setPassword("");
		publisher.setUserAccount(userAccount);

		return publisher;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterPublisherForm createForm() {
		return this.instanceClass(RegisterPublisherForm.class);
	}

	public Publisher reconstructForm(final RegisterPublisherForm publisherForm, final BindingResult bindingResult) {
		final Publisher result;

		final List<String> usernames = this.userAccountRepository.getUserNames();

		if (publisherForm.getId() == 0) {
			if (usernames.contains(publisherForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Publisher publisher3 = this.findPrincipal();
			usernames.remove(publisher3.getUserAccount().getUsername());
			if (usernames.contains(publisherForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (publisherForm.getUsername().length() < 5 || publisherForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!publisherForm.getPassword().equals(publisherForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (publisherForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (publisherForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		if (publisherForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(publisherForm.getId());

		result.setName(publisherForm.getName());
		result.setSurnames(publisherForm.getSurnames());
		result.setPhoto(publisherForm.getPhoto());
		result.setEmail(publisherForm.getEmail());
		result.setPhoneNumber(publisherForm.getPhoneNumber());
		result.setAddress(publisherForm.getAddress());

		result.getUserAccount().setUsername(publisherForm.getUsername());
		result.getUserAccount().setPassword(publisherForm.getPassword());

		this.validator.validate(result, bindingResult);
		this.repository.flush();

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}
	public RegisterPublisherForm deconstruct(final Publisher publisher) {
		final RegisterPublisherForm publisherForm = this.createForm();

		publisherForm.setId(publisher.getId());
		publisherForm.setName(publisher.getName());
		publisherForm.setSurnames(publisher.getSurnames());
		publisherForm.setPhoto(publisher.getPhoto());
		publisherForm.setEmail(publisher.getEmail());
		publisherForm.setPhoneNumber(publisher.getPhoneNumber());
		publisherForm.setAddress(publisher.getAddress());

		publisherForm.setUsername(publisher.getUserAccount().getUsername());
		publisherForm.setPassword(publisher.getUserAccount().getPassword());

		return publisherForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Publisher findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Publisher findPrincipal() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
			return null;
		else {
			final UserAccount userAccount = LoginService.getPrincipal();
			for (final Authority authority : userAccount.getAuthorities())
				if (authority.getAuthority().equals(Authority.PUBLISHER))
					return this.findByUserAccountId(userAccount.getId());
			return null;
		}
	}

	public Double[] getSeriesPerPublisherStatistics() {
		return this.repository.getSeriesPerPublisherStatistics();
	}

}
