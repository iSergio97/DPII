/*
 * ApplicationService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Administrator;
import domain.Application;
import domain.Publisher;
import domain.Serie;
import forms.ApplicationForm;

@Service
@Transactional
public class ApplicationService extends AbstractService<ApplicationRepository, Application> {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PublisherService		publisherService;

	@Autowired
	private SerieService			serieService;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Application create() {
		final Application application = super.create();
		Assert.notNull(application);

		Assert.notNull(this.publisherService.findPrincipal());
		application.setPublisher(this.publisherService.findPrincipal());
		application.setStatus("PENDING");

		return application;
	}

	@Override
	public Application findOne(final int id) {
		final Application application = super.findOne(id);
		final int principalId = LoginService.getPrincipal().getId();
		Assert.isTrue(application.getPublisher().getUserAccount().getId() == principalId || this.administratorService.findByUserAccountId(principalId) != null);
		return application;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public ApplicationForm createForm() {
		return this.instanceClass(ApplicationForm.class);
	}

	public Application reconstructForm(final ApplicationForm applicationForm, final BindingResult binding) {
		final Application application;
		Serie serie;

		serie = this.serieService.findOne(applicationForm.getSerieId());
		if (applicationForm.getId() == 0)
			application = this.create();
		else
			application = this.findOne(applicationForm.getId());

		application.setMoment(new Date());
		application.setDescription(applicationForm.getDescription());
		application.setSerie(serie);

		this.validator.validate(application, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return application;
	}

	public ApplicationForm deconstruct(final Application application) {
		final ApplicationForm applicationForm = this.createForm();

		Assert.isTrue(application.getStatus().equals("PENDING"));

		applicationForm.setId(application.getId());
		applicationForm.setDescription(application.getDescription());

		return applicationForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public List<Application> findAllByUserAccountId(final int userAccountId) {
		return this.repository.findAllByUserAccountId(userAccountId);
	}

	public List<Application> findAllAcceptedBySerieId(final int serieId) {
		return this.repository.findAllAcceptedBySerieId(serieId);
	}

	public List<Application> findAllPendingBySerieId(final int serieId) {
		return this.repository.findAllPendingBySerieId(serieId);
	}

	public List<Application> findAllAppliesByAdminId(final Administrator admin) {
		return this.repository.findAllAppliesByAdminId(admin.getId());
	}

	public List<Application> findAllApplicatoinsByPublisher(final Publisher publisher) {
		return this.repository.findAllApplicatoinsByPublisher(publisher.getId());
	}

	public Collection<Application> findAllBySerieId(final int serieId) {
		return this.repository.findAllBySerieId(serieId);
	}

}
