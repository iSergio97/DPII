
package services;

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
		systemConfigurationForm.setDefaultCountryCode(systemConfiguration.getDefaultCountryCode());
		systemConfigurationForm.setSystemName(systemConfiguration.getSystemName());
		systemConfigurationForm.setBanner(systemConfiguration.getBanner());
		systemConfigurationForm.setFinderDuration(systemConfiguration.getFinderDuration());
		systemConfigurationForm.setMaximumFinderResults(systemConfiguration.getMaximumFinderResults());
		systemConfigurationForm.setPositiveWords(ConversionUtils.listToString(systemConfiguration.getPositiveWords(), ","));
		systemConfigurationForm.setNegativeWords(ConversionUtils.listToString(systemConfiguration.getNegativeWords(), ","));
		systemConfigurationForm.setSpamWords(ConversionUtils.listToString(systemConfiguration.getSpamWords(), ","));
		systemConfigurationForm.setWarningMessages(ConversionUtils.mapToString(systemConfiguration.getWarningMessages(), ":", ";"));
		systemConfigurationForm.setWelcomeMessages(ConversionUtils.mapToString(systemConfiguration.getWelcomeMessages(), ":", ";"));
		systemConfigurationForm.setLowestPosition(systemConfiguration.getLowestPosition());
		return systemConfigurationForm;
	}

	public SystemConfiguration reconstruct(final SystemConfigurationForm systemConfigurationForm, final BindingResult bindingResult) {
		final SystemConfiguration systemConfiguration = this.getSystemConfiguration();
		systemConfiguration.setDefaultCountryCode(systemConfigurationForm.getDefaultCountryCode());
		systemConfiguration.setSystemName(systemConfigurationForm.getSystemName());
		systemConfiguration.setBanner(systemConfigurationForm.getBanner());
		systemConfiguration.setFinderDuration(systemConfigurationForm.getFinderDuration());
		systemConfiguration.setMaximumFinderResults(systemConfigurationForm.getMaximumFinderResults());
		systemConfiguration.setPositiveWords(ConversionUtils.stringToList(systemConfigurationForm.getPositiveWords(), ","));
		systemConfiguration.setNegativeWords(ConversionUtils.stringToList(systemConfigurationForm.getNegativeWords(), ","));
		systemConfiguration.setSpamWords(ConversionUtils.stringToList(systemConfigurationForm.getSpamWords(), ","));
		systemConfiguration.setWarningMessages(ConversionUtils.stringToMap(systemConfigurationForm.getWarningMessages(), ":", ";"));
		systemConfiguration.setWelcomeMessages(ConversionUtils.stringToMap(systemConfigurationForm.getWelcomeMessages(), ":", ";"));
		systemConfiguration.setLowestPosition(systemConfigurationForm.getLowestPosition());
		this.validator.validate(systemConfiguration, bindingResult);
		return systemConfiguration;
	}

}
