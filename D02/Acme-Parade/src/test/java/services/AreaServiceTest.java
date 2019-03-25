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

import java.util.List;

import javax.transaction.Transactional;

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
	private AreaService	areaService;


	// Tests ------------------------------------------------------------------

	/*
	 * Check we can save a area in the database.
	 */
	@Test
	public void saveAreaTest() {
		Area area = this.areaService.create();
		final List<String> pictures = area.getPictures();
		pictures.add("http://www.imgur.com");
		area.setPictures(pictures);
		area = this.areaService.save(area);
		Assert.isTrue(area.getPictures().get(0).equals("http://www.imgur.com"));
	}

	/*
	 * Attempts to save a area without a map.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void saveBadAreaTest() {
		Area area = this.areaService.create();
		area.setPictures(null);
		area = this.areaService.save(area);
		Assert.notNull(area.getPictures());
	}

}
