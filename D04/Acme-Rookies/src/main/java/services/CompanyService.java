/*
 * CompanyService.java
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
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.CreditCard;
import forms.RegisterCompanyForm;

@Service
@Transactional
public class CompanyService extends AbstractService<CompanyRepository, Company> {

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator	validator;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Company create() {
		final Company company = super.create();

		// create user account
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setPassword("");
		userAccount.setUsername("");

		// set fields
		company.setCommercialName("");
		company.setName("");
		company.setSurnames("");
		company.setVat("");
		company.setPhoto("");
		company.setEmail("");
		company.setPhoneNumber("");
		company.setAddress("");
		company.setIsFlagged(false);
		company.setIsBanned(false);
		// set relationships
		company.setUserAccount(userAccount);
		company.setCreditCard(null);

		return company;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterCompanyForm createForm() {
		final RegisterCompanyForm companyForm = new RegisterCompanyForm();

		companyForm.setCommercialName("");
		companyForm.setName("");
		companyForm.setSurnames("");
		companyForm.setVat("");
		companyForm.setPhoto("");
		companyForm.setEmail("");
		companyForm.setPhoneNumber("");
		companyForm.setAddress("");
		companyForm.setUsername("");
		companyForm.setPassword("");
		companyForm.setConfirmPassword("");
		companyForm.setHolder("");
		companyForm.setNumber("");
		companyForm.setCVV("");

		return companyForm;
	}

	public Company reconstructForm(final RegisterCompanyForm companyForm, final BindingResult bindingResult) {
		final Company result;

		if (companyForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(companyForm.getId());

		result.setCommercialName(companyForm.getCommercialName());
		result.setName(companyForm.getName());
		result.setVat(companyForm.getVat());
		result.setCommercialName(companyForm.getCommercialName());
		result.setSurnames(companyForm.getSurnames());
		result.setPhoto(companyForm.getPhoto());
		result.setEmail(companyForm.getEmail());
		result.setPhoneNumber(companyForm.getPhoneNumber());
		result.setAddress(companyForm.getAddress());

		result.getUserAccount().setUsername(companyForm.getUsername());
		result.getUserAccount().setPassword(companyForm.getPassword());

		final CreditCard cc = new CreditCard();
		cc.setHolder(companyForm.getHolder());
		cc.setBrand(companyForm.getBrand());
		cc.setNumber(companyForm.getNumber());
		cc.setExpirationMonth(companyForm.getExpirationMonth());
		cc.setExpirationYear(companyForm.getExpirationYear());
		cc.setCVV(Integer.valueOf(companyForm.getCVV()));

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);
		this.validator.validate(result, bindingResult);
		this.repository.flush();

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}

	public RegisterCompanyForm deconstruct(final Company company) {
		final RegisterCompanyForm companyForm = this.createForm();

		companyForm.setId(company.getId());
		companyForm.setCommercialName(company.getCommercialName());
		companyForm.setName(company.getName());
		companyForm.setCommercialName(company.getCommercialName());
		companyForm.setSurnames(company.getSurnames());
		companyForm.setVat(company.getVat());
		companyForm.setPhoto(company.getPhoto());
		companyForm.setEmail(company.getEmail());
		companyForm.setPhoneNumber(company.getPhoneNumber());
		companyForm.setAddress(company.getAddress());

		companyForm.setUsername(company.getUserAccount().getUsername());
		companyForm.setPassword(company.getUserAccount().getPassword());

		companyForm.setHolder(company.getCreditCard().getHolder());
		companyForm.setBrand(company.getCreditCard().getBrand());
		companyForm.setNumber(company.getCreditCard().getNumber());
		companyForm.setExpirationMonth(company.getCreditCard().getExpirationMonth());
		companyForm.setExpirationYear(company.getCreditCard().getExpirationYear());
		companyForm.setCVV(String.valueOf(company.getCreditCard().getCVV()));

		return companyForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Company findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Company findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
