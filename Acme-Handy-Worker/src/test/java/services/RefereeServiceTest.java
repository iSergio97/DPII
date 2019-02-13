
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
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------------

	@Autowired
	private RefereeService	refereeService;


	// Tests ----------------------------------------------------------------

	@Test
	public void testSaveReferee() {
		Referee referee;
		final Referee saved;
		final Collection<Referee> referees;

		referee = this.refereeService.create();
		referee.setAddress("Calle de los dolores 25");
		referee.setEmail("referee1@gmail.com");

		saved = this.refereeService.save(referee);
		referees = this.refereeService.findAll();
		Assert.isTrue(referees.contains(saved));

	}
}
