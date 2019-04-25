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

import domain.Application;
import domain.Hacker;
import domain.Position;
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
		final ApplicationForm applicationForm = new ApplicationForm();

		applicationForm.setExplanations("");
		applicationForm.setCodeLink("");

		return applicationForm;
	}

	public Application reconstructForm(final ApplicationForm applicationForm, final BindingResult bindingResult) {
		final Application application;

		if (applicationForm.getId() == 0)
			application = this.create();
		else
			application = this.findOne(applicationForm.getId());

		Assert.isTrue(application.getStatus().equals("PENDING"));

		application.setSubmitMoment(new Date());
		application.setExplanations(applicationForm.getExplanations());
		application.setCodeLink(applicationForm.getCodeLink());

		this.validator.validate(application, bindingResult);

		return application;
	}

	public ApplicationForm deconstruct(final Application application) {
		final ApplicationForm applicationForm = this.createForm();

		Assert.isTrue(application.getStatus().equals("PENDING"));

		applicationForm.setId(application.getId());
		applicationForm.setExplanations(application.getExplanations());
		applicationForm.setCodeLink(application.getCodeLink());

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

	public Collection<Application> getApplicationsOfHacker(final Hacker hacker) {
		return this.repository.findByHackerId(hacker.getId());
	}

	public Collection<Application> getApplicationsOfPosition(final Position position) {
		return this.repository.findByPositionId(position.getId());
	}

	public Double min() {
		return this.repository.min();
	}

	public Double max() {
		return this.repository.max();
	}

	public Double media() {
		return this.repository.avg();
	}

	public Double stdDev() {
		return this.repository.stdDev();
	}

	public Hacker hackerMax() {
		return this.repository.hackerMax();
	}

}
