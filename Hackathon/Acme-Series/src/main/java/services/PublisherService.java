/*
 * PublisherService.java
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

import repositories.PublisherRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Publisher;
import forms.RegisterPublisherForm;

@Service
@Transactional
public class PublisherService extends AbstractService<PublisherRepository, Publisher> {

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

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
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

	public Double[] getSeriesPerPublisherStatistics() {
		return this.repository.getSeriesPerPublisherStatistics();
	}

}
