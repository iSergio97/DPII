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

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Area;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AreaServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AreaService			areaService;

	// Testing data -----------------------------------------------------------

	private final Object[][]	testingData	= {
		{
		"AreaName", Arrays.asList(new String[] {
			"https://www.imgur.com"
		}), null
		}, {
		"AreaName", null, IllegalArgumentException.class
		}
											};


	// Test template ----------------------------------------------------------

	private void template(final String name, final List<String> pictures, final Class<?> expectedThrowableClass) {
		Class<?> throwableClass = null;
		try {
			// Create area
			Area area = this.areaService.create();
			area.setName(name);
			area.setPictures(pictures);
			// Save area
			area = this.areaService.save(area);
			// Validate area
			Assert.isTrue(Validation.buildDefaultValidatorFactory().getValidator().validate(area).size() == 0);
			// Check if area has been saved correctly
			Assert.isTrue(area.getName().equals(name));
			for (int i = 0; i < area.getPictures().size(); ++i)
				Assert.isTrue(area.getPictures().get(i).equals(pictures.get(i)));
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
			this.template((String) this.testingData[i][0], (List<String>) this.testingData[i][1], (Class<?>) this.testingData[i][2]);
	}

}
