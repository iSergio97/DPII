/*
 * SystemConfigurationServiceTest.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import javax.transaction.Transactional;
import javax.validation.Validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SystemConfiguration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SystemConfigurationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Tests ------------------------------------------------------------------

	/*
	 * Check we can get the system configuration and the values are persisted in the database correctly.
	 */
	@Test
	public void getSystemConfigurationTest() {
		final SystemConfiguration systemConfiguration = this.systemConfigurationService.getSystemConfiguration();
		Assert.notNull(systemConfiguration);
	}

	/*
	 * There's only one system configuration. Check that we can't create any new system configurations.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createSystemConfigurationTest() {
		final SystemConfiguration systemConfiguration = new SystemConfiguration();
		this.systemConfigurationService.save(systemConfiguration);
	}

	/*
	 * Check we can't save system configurations with errors.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void saveSystemConfigurationWithErrorsTest() {
		// Create system configuration
		final SystemConfiguration systemConfiguration = this.systemConfigurationService.getSystemConfiguration();
		systemConfiguration.setSystemName("");
		systemConfiguration.setBanner("");
		systemConfiguration.setDefaultCountryCode(9999);
		// Validate system configuration
		Assert.isTrue(Validation.buildDefaultValidatorFactory().getValidator().validate(systemConfiguration).size() == 0);
	}

}
