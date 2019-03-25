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

import java.util.Map;

import javax.transaction.Transactional;

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
	private PriorityService	priorityService;


	// Tests ------------------------------------------------------------------

	/*
	 * Check we can save a priority in the database.
	 */
	@Test
	public void savePriorityTest() {
		Priority priority = this.priorityService.create();
		final Map<String, String> strings = priority.getStrings();
		strings.put("en", "Test priority");
		priority.setStrings(strings);
		priority = this.priorityService.save(priority);
		Assert.isTrue(priority.getStrings().get("en").equals("Test priority"));
	}

	/*
	 * Attempts to save a priority without a map.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void saveBadPriorityTest() {
		Priority priority = this.priorityService.create();
		priority.setStrings(null);
		priority = this.priorityService.save(priority);
		Assert.notNull(priority.getStrings());
	}

}
