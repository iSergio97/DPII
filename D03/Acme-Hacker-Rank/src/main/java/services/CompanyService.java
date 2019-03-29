/*
 * CompanyService.java
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

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utilities.ConversionUtils;
import domain.Company;
import domain.Message;
import domain.Position;
import domain.Problem;
import domain.SocialProfile;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private CompanyRepository		companyRepository;

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

	public CompanyService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Company create() {
		final Company company = new Company();

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
		company.setCommercialName("");
		company.setName("");
		company.setSurnames(new ArrayList<String>());
		company.setVat("");
		company.setPhoto("");
		company.setEmail("");
		company.setPhoneNumber("");
		company.setAddress("");
		company.setIsSpammer(false);
		company.setIsBanned(false);
		// set relationships
		company.setUserAccount(userAccount);
		company.setCreditCard(null);
		company.setMessagePool(new ArrayList<Message>());
		company.setSocialProfiles(new ArrayList<SocialProfile>());
		company.setPositions(new ArrayList<Position>());
		company.setProblems(new ArrayList<Problem>());

		return company;
	}

	public Company save(final Company company) {
		Assert.isTrue(company != null);
		return this.companyRepository.save(company);
	}

	public Iterable<Company> save(final Iterable<Company> companys) {
		Assert.isTrue(companys != null);
		return this.companyRepository.save(companys);
	}

	public void delete(final Company company) {
		Assert.isTrue(company != null);
		this.companyRepository.delete(company);
	}

	public void delete(final Iterable<Company> companys) {
		Assert.isTrue(companys != null);
		this.companyRepository.delete(companys);
	}

	public Company findOne(final int id) {
		return this.companyRepository.findOne(id);
	}

	public List<Company> findAll() {
		return this.companyRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public CompanyForm createForm() {
		final CompanyForm companyForm = new CompanyForm();

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

		return companyForm;
	}

	public Company reconstructForm(final CompanyForm companyForm, final BindingResult bindingResult) {
		final Company result;

		if (companyForm.getId() == 0)
			result = this.create();
		else
			result = this.companyRepository.findOne(companyForm.getId());

		result.setCommercialName(companyForm.getCommercialName());
		result.setName(companyForm.getName());
		result.setVat(companyForm.getVat());
		result.setSurnames(ConversionUtils.stringToList(companyForm.getSurnames(), ","));
		result.setPhoto(companyForm.getPhoto());
		result.setEmail(companyForm.getEmail());
		result.setPhoneNumber(companyForm.getPhoneNumber());
		result.setAddress(companyForm.getAddress());

		this.validator.validate(result, bindingResult);

		return result;
	}

	public CompanyForm deconstruct(final Company company) {
		final CompanyForm companyForm = this.createForm();

		companyForm.setId(company.getId());
		companyForm.setCommercialName(company.getCommercialName());
		companyForm.setName(company.getName());
		companyForm.setSurnames(ConversionUtils.listToString(company.getSurnames(), ","));
		companyForm.setVat(company.getVat());
		companyForm.setPhoto(company.getPhoto());
		companyForm.setEmail(company.getEmail());
		companyForm.setPhoneNumber(company.getPhoneNumber());
		companyForm.setAddress(company.getAddress());
		companyForm.setUsername(company.getUserAccount().getUsername());

		return companyForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Company findByUserAccountId(final int id) {
		return this.companyRepository.findByUserAccountId(id);
	}

	public Company findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
