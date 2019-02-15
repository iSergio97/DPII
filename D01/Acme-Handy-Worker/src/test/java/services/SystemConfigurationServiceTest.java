/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;

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
		Assert.isTrue(systemConfiguration.getName().equals("Acme Handy Worker"));
		Assert.isTrue(systemConfiguration.getBanner().equals("https://tinyurl.com/acme-handy-worker-logo"));
		Assert.isTrue(systemConfiguration.getMessage().equals("Welcome to Acme Handy Worker! Price, quality, and trust in a single place"));
		Assert.isTrue(systemConfiguration.getSpamWords().size() == 9);
		Assert.isTrue(systemConfiguration.getVAT() == 0.21d);
		Assert.isTrue(systemConfiguration.getDefaultCountryCode().equals("+34"));
		Assert.isTrue(systemConfiguration.getPositiveWords().size() == 14);
		Assert.isTrue(systemConfiguration.getNegativeWords().size() == 10);
		Assert.isTrue(systemConfiguration.getMaximumFinderResults() == 10);
	}

	/* 
	 * Check we can update the system configuration and the changes are persisted in the database correctly.
	 */
	@Test
	public void updateSystemConfigurationTest() {
		final SystemConfiguration systemConfiguration = this.systemConfigurationService.getSystemConfiguration();
		systemConfiguration.setName("Acme Handy Worker Test");
		this.systemConfigurationService.save(systemConfiguration);
		// Check fields
		Assert.isTrue(this.systemConfigurationService.getSystemConfiguration().getName().equals("Acme Handy Worker Test"));
	}

	/* 
	 * There's only one system configuration. Check that we can't create any new system configurations.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createSystemConfigurationTest() {
		final SystemConfiguration systemConfiguration = new SystemConfiguration();
		this.systemConfigurationService.save(systemConfiguration);
	}

}
