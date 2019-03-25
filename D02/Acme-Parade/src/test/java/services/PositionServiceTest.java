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
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PositionService	positionService;


	// Tests ------------------------------------------------------------------

	/*
	 * Check we can save a position in the database.
	 */
	@Test
	public void savePositionTest() {
		Position position = this.positionService.create();
		final Map<String, String> strings = position.getStrings();
		strings.put("en", "Test position");
		position.setStrings(strings);
		position = this.positionService.save(position);
		Assert.isTrue(position.getStrings().get("en").equals("Test position"));
	}

	/*
	 * Attempts to save a position without a map.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void saveBadPositionTest() {
		Position position = this.positionService.create();
		position.setStrings(null);
		position = this.positionService.save(position);
		Assert.notNull(position.getStrings());
	}

}
