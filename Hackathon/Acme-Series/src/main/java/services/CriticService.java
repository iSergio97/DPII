/*
 * CriticService.java
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

import repositories.CriticRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Critic;
import forms.RegisterCriticForm;

@Service
@Transactional
public class CriticService extends AbstractService<CriticRepository, Critic> {

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Critic create() {
		final Critic critic = super.create();

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
		critic.setUserAccount(userAccount);

		return critic;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterCriticForm createForm() {
		return this.instanceClass(RegisterCriticForm.class);
	}

	public Critic reconstructForm(final RegisterCriticForm criticForm, final BindingResult bindingResult) {
		final Critic result;

		if (criticForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(criticForm.getId());

		result.setName(criticForm.getName());
		result.setSurnames(criticForm.getSurnames());
		result.setPhoto(criticForm.getPhoto());
		result.setEmail(criticForm.getEmail());
		result.setPhoneNumber(criticForm.getPhoneNumber());
		result.setAddress(criticForm.getAddress());

		result.getUserAccount().setUsername(criticForm.getUsername());
		result.getUserAccount().setPassword(criticForm.getPassword());

		this.validator.validate(result, bindingResult);
		this.repository.flush();

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}
	public RegisterCriticForm deconstruct(final Critic critic) {
		final RegisterCriticForm criticForm = this.createForm();

		criticForm.setId(critic.getId());
		criticForm.setName(critic.getName());
		criticForm.setSurnames(critic.getSurnames());
		criticForm.setPhoto(critic.getPhoto());
		criticForm.setEmail(critic.getEmail());
		criticForm.setPhoneNumber(critic.getPhoneNumber());
		criticForm.setAddress(critic.getAddress());

		criticForm.setUsername(critic.getUserAccount().getUsername());
		criticForm.setPassword(critic.getUserAccount().getPassword());

		return criticForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Critic findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Critic findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
