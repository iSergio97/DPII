
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SectionServiceTest extends AbstractTest {

	// Service under test --------------------------------------------

	@Autowired
	private SectionService	sectionService;


	// Test ----------------------------------------------------------

	@Test
	public void testSaveSection() {

		Section section;
		Section saved;
		Collection<Section> sections;

		section = this.sectionService.create();
		//inicializar
		saved = this.sectionService.save(section);
		sections = this.sectionService.findAll();
		Assert.isTrue(sections.contains(saved));

	}

}
