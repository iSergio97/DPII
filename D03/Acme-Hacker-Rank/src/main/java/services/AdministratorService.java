/*
 * AdministratorService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Administrator;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private AdministratorRepository	administratorRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private UserAccountRepository	userAccountRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator				validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public AdministratorService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Administrator create() {
		final Administrator administrator = new Administrator();

		// create user account
		UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount = this.userAccountRepository.save(userAccount);
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
		administrator.setUserAccount(userAccount);
		return administrator;
	}

	public Administrator save(final Administrator administrator) {
		Assert.isTrue(administrator != null);
		return this.administratorRepository.save(administrator);
	}

	public Iterable<Administrator> save(final Iterable<Administrator> administrators) {
		Assert.isTrue(administrators != null);
		return this.administratorRepository.save(administrators);
	}

	public void delete(final Administrator administrator) {
		Assert.isTrue(administrator != null);
		this.administratorRepository.delete(administrator);
	}

	public void delete(final Iterable<Administrator> administrators) {
		Assert.isTrue(administrators != null);
		this.administratorRepository.delete(administrators);
	}

	public Administrator findOne(final int id) {
		return this.administratorRepository.findOne(id);
	}

	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public AdministratorForm createForm() {
		final AdministratorForm administratorForm = new AdministratorForm();

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

		return administratorForm;
	}

	public Administrator reconstructForm(final AdministratorForm administratorForm, final BindingResult bindingResult) {
		final Administrator result;

		if (administratorForm.getId() == 0)
			result = this.create();
		else
			result = this.administratorRepository.findOne(administratorForm.getId());

		result.setName(administratorForm.getName());
		result.setVat(administratorForm.getVat());
		// result.setSurnames(ConversionUtils.stringToList(administratorForm.getSurnames(), ","));
		result.setSurnames(administratorForm.getSurnames());
		result.setPhoto(administratorForm.getPhoto());
		result.setEmail(administratorForm.getEmail());
		result.setPhoneNumber(administratorForm.getPhoneNumber());
		result.setAddress(administratorForm.getAddress());

		this.validator.validate(result, bindingResult);

		return result;
	}
	public AdministratorForm deconstruct(final Administrator administrator) {
		final AdministratorForm administratorForm = this.createForm();

		administratorForm.setId(administrator.getId());
		administratorForm.setName(administrator.getName());
		// administratorForm.setSurnames(ConversionUtils.listToString(administrator.getSurnames(), ","));
		administratorForm.setSurnames(administratorForm.getSurnames());
		administratorForm.setVat(administrator.getVat());
		administratorForm.setPhoto(administrator.getPhoto());
		administratorForm.setEmail(administrator.getEmail());
		administratorForm.setPhoneNumber(administrator.getPhoneNumber());
		administratorForm.setAddress(administrator.getAddress());
		administratorForm.setUsername(administrator.getUserAccount().getUsername());

		return administratorForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Administrator findByUserAccountId(final int id) {
		return this.administratorRepository.findByUserAccountId(id);
	}

	public Administrator findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
