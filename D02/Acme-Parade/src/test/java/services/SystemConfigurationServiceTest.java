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

}
