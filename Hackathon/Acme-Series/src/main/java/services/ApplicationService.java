/*
 * ApplicationService.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Administrator;
import domain.Application;
import forms.ApplicationForm;
import repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationService extends AbstractService<ApplicationRepository, Application> {

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Application create() {
		final Application application = super.create();

		application.setStatus("PENDING");

		return application;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public ApplicationForm createForm() {
		return this.instanceClass(ApplicationForm.class);
	}

	public Application reconstructForm(final ApplicationForm applicationForm, final BindingResult bindingResult) {
		final Application application;

		if (applicationForm.getId() == 0)
			application = this.create();
		else
			application = this.findOne(applicationForm.getId());

		Assert.isTrue(application.getStatus().equals("PENDING"));

		application.setMoment(new Date());
		application.setDescription(applicationForm.getDescription());

		this.validator.validate(application, bindingResult);

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

	public static Map<String, List<Application>> groupByStatus(final Collection<Application> applications) {
		final Map<String, List<Application>> groupedApplications = new HashMap<String, List<Application>>();
		for (final Application application : applications) {
			List<Application> listedApplications = groupedApplications.get(application.getStatus());
			if (listedApplications == null)
				listedApplications = new ArrayList<Application>();
			listedApplications.add(application);
			groupedApplications.put(application.getStatus(), listedApplications);
		}
		return groupedApplications;
	}

	public List<Application> findAllAppliesByAdminId(final Administrator admin) {
		return this.repository.findAllAppliesByAdminId(admin.getId());
	}

}
