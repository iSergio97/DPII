
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
import domain.Actor;
import domain.Endorsement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private ActorService		actorService;


	@Test
	public void createTest() {
		Endorsement e;

		super.authenticate("customer2");

		e = this.endorsementService.create();
		Assert.isTrue(e.getEndorser().equals(this.actorService.findPrincipal()));
		Assert.isTrue(e.getDate() != null);
		Assert.isTrue(e.getComment().isEmpty());
		Assert.isNull(e.getEndorsed());

		super.unauthenticate();
	}

	@Test
	public void saveTest() {
		Endorsement e, saved;
		Collection<Endorsement> endorsements;

		final Actor endorser;
		Actor endorsed;
		super.authenticate("customer2");
		e = this.endorsementService.create();
		endorsed = this.actorService.findById(2483);
		e.setEndorsed(endorsed);

		saved = this.endorsementService.save(e);
		endorsements = this.endorsementService.findAll();
		Assert.isTrue(endorsements.contains(saved));

		super.unauthenticate();

	}
}
