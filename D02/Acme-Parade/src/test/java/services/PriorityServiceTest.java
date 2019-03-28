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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Priority;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PriorityServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PriorityService		priorityService;

	// Testing data -----------------------------------------------------------

	@SuppressWarnings("serial")
	private final Object[][]	testingData	= {
		{
		new HashMap<String, String>() {

			{
				this.put("en", "PriorityNameEnglish");
				this.put("es", "PriorityNameSpanish");
			}
		}, null
		}, {
		null, IllegalArgumentException.class
		}
											};


	// Test template ----------------------------------------------------------

	private void template(final Map<String, String> strings, final Class<?> expectedThrowableClass) {
		Class<?> throwableClass = null;
		try {
			// Create priority
			Priority priority = this.priorityService.create();
			priority.setStrings(strings);
			// Save priority
			priority = this.priorityService.save(priority);
			// Validate priority
			Assert.isTrue(Validation.buildDefaultValidatorFactory().getValidator().validate(priority).size() == 0);
			// Check if priority has been saved correctly
			final Set<String> allKeys = new HashSet<String>();
			allKeys.addAll(priority.getStrings().keySet());
			allKeys.addAll(strings.keySet());
			for (final String key : allKeys)
				Assert.isTrue(priority.getStrings().get(key).equals(strings.get(key)));
		} catch (final Throwable throwable) {
			throwableClass = throwable.getClass();
		}

		super.checkExceptions(expectedThrowableClass, throwableClass);
	}

	// Tests ------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		for (int i = 0; i < this.testingData.length; ++i)
			this.template((Map<String, String>) this.testingData[i][0], (Class<?>) this.testingData[i][1]);
	}

}
