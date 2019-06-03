/*
 * SocialProfileServiceTest.java
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
import domain.SocialProfile;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SocialProfileService	socialProfileService;

	// Testing data -----------------------------------------------------------

	private final Object[][]		testingData	= {
		{
		"a", "a", "http://www.google.com/", null
		}, {
		"", "a", "http://www.google.com/", IllegalArgumentException.class
		}, {
		"a", "", "http://www.google.com/", IllegalArgumentException.class
		}, {
		"a", "a", "esto no es una URL", IllegalArgumentException.class
		}
												};


	// Test template ----------------------------------------------------------

	private void template(final String nick, final String socialNetworkName, final String profileLink, final Class<?> expectedThrowableClass) {
		Class<?> throwableClass = null;
		try {
			// Create social profile
			SocialProfile socialProfile = this.socialProfileService.create();
			socialProfile.setNick(nick);
			socialProfile.setSocialNetworkName(socialNetworkName);
			socialProfile.setProfileLink(profileLink);
			// Save social profile
			socialProfile = this.socialProfileService.save(socialProfile);
			// Validate position
			Assert.isTrue(Validation.buildDefaultValidatorFactory().getValidator().validate(socialProfile).size() == 0);
			// Check if position has been saved correctly
			Assert.isTrue(socialProfile.getNick().equals(nick));
			Assert.isTrue(socialProfile.getSocialNetworkName().equals(socialNetworkName));
			Assert.isTrue(socialProfile.getProfileLink().equals(profileLink));
		} catch (final Throwable throwable) {
			throwableClass = throwable.getClass();
		}

		super.checkExceptions(expectedThrowableClass, throwableClass);
	}

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		for (int i = 0; i < this.testingData.length; ++i)
			this.template((String) this.testingData[i][0], (String) this.testingData[i][1], (String) this.testingData[i][2], (Class<?>) this.testingData[i][3]);
	}

}
