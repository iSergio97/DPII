/*
 * SystemConfigurationService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SystemConfigurationRepository;
import utilities.ConversionUtils;
import domain.SystemConfiguration;
import forms.SystemConfigurationForm;

@Service
@Transactional
public class SystemConfigurationService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator						validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public SystemConfigurationService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.isTrue(systemConfiguration != null);
		// Don't make new system configurations.
		Assert.isTrue(systemConfiguration.getId() == this.getSystemConfiguration().getId());
		return this.systemConfigurationRepository.save(systemConfiguration);
	}

	public SystemConfiguration getSystemConfiguration() {
		return this.systemConfigurationRepository.findAll().get(0);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public SystemConfigurationForm deconstruct(final SystemConfiguration systemConfiguration) {
		final SystemConfigurationForm systemConfigurationForm = new SystemConfigurationForm();
		systemConfigurationForm.setId(systemConfiguration.getId());
		systemConfigurationForm.setDefaultCC(systemConfiguration.getDefaultCC());
		systemConfigurationForm.setSystemName(systemConfiguration.getSystemName());
		systemConfigurationForm.setBanner(systemConfiguration.getBanner());
		systemConfigurationForm.setFinderCacheTime(systemConfiguration.getFinderCacheTime());
		systemConfigurationForm.setMaximumFinderResults(systemConfiguration.getMaximumFinderResults());
		systemConfigurationForm.setSpamWords(ConversionUtils.listToString((List<String>) systemConfiguration.getSpamWords(), ","));
		// systemConfigurationForm.setWelcomeMessages(ConversionUtils.mapToString(systemConfiguration.getWelcomeMessages(), ":", ";"));
		systemConfigurationForm.setWelcomeMessage(systemConfiguration.getWelcomeMessage());
		systemConfigurationForm.setWelcomeMessageES(systemConfiguration.getWelcomeMessageES());
		systemConfigurationForm.setWarningMessage(systemConfiguration.getWarningMessage());
		systemConfigurationForm.setWarningMessageES(systemConfiguration.getWarningMessageES());
		return systemConfigurationForm;
	}

	public SystemConfiguration reconstruct(final SystemConfigurationForm systemConfigurationForm, final BindingResult bindingResult) {
		final SystemConfiguration systemConfiguration = this.getSystemConfiguration();
		systemConfiguration.setDefaultCC(systemConfigurationForm.getDefaultCC());
		systemConfiguration.setSystemName(systemConfigurationForm.getSystemName());
		systemConfiguration.setBanner(systemConfigurationForm.getBanner());
		systemConfiguration.setFinderCacheTime(systemConfigurationForm.getFinderCacheTime());
		systemConfiguration.setMaximumFinderResults(systemConfigurationForm.getMaximumFinderResults());
		systemConfiguration.setSpamWords(ConversionUtils.stringToList(systemConfigurationForm.getSpamWords(), ","));
		// systemConfiguration.setWelcomeMessages(ConversionUtils.stringToMap(systemConfigurationForm.getWelcomeMessages(), ":", ";"));
		systemConfiguration.setWelcomeMessage(systemConfigurationForm.getWelcomeMessage());
		systemConfiguration.setWelcomeMessageES(systemConfigurationForm.getWelcomeMessageES());
		systemConfiguration.setWarningMessage(systemConfigurationForm.getWarningMessage());
		systemConfiguration.setWarningMessageES(systemConfigurationForm.getWarningMessageES());
		this.validator.validate(systemConfiguration, bindingResult);
		this.systemConfigurationRepository.flush();
		return systemConfiguration;
	}

}
