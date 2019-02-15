
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageBoxServiceTest extends AbstractTest {

	@Autowired
	private MessageBoxService	boxService;

	@Autowired
	private ActorService		actorService;


	@Test
	public void createSystemBoxes() {
		List<MessageBox> sysBoxes;

		super.authenticate("customer2");
		final Actor a = this.actorService.findPrincipal();

		sysBoxes = this.boxService.createSystemBoxes();
		Assert.isTrue(sysBoxes.size() == 4);
		for (final MessageBox b : sysBoxes)
			Assert.isTrue(b.getActor().equals(a));

		super.unauthenticate();

	}

	@Test
	public void createNormalBox() {
		MessageBox b;

		super.authenticate("customer2");
		final Actor a = this.actorService.findPrincipal();
		b = this.boxService.create();

		Assert.isTrue(b.getName().equals(""));
		Assert.isTrue(b.getActor().equals(a));

		super.unauthenticate();
	}

	@Test
	public void saveSysBoxes() {
		List<MessageBox> sysBoxes;
		Iterable<MessageBox> saved;
		Collection<MessageBox> allBoxes;

		super.authenticate("customer2");
		final Actor a = this.actorService.findPrincipal();
		sysBoxes = this.boxService.createSystemBoxes();

		saved = this.boxService.save(sysBoxes);
		allBoxes = this.boxService.findAll();
		for (final MessageBox b : saved)
			Assert.isTrue(allBoxes.contains(b));
		Assert.isTrue(a.getMessageBoxes().size() == 8);

		super.unauthenticate();
	}

	@Test
	public void saveNormalBox() {
		MessageBox box, saved;
		Collection<MessageBox> allBoxes;

		super.authenticate("customer2");
		final Actor a = this.actorService.findPrincipal();
		box = this.boxService.create();

		box.setName("Caja de Prueba");
		saved = this.boxService.save(box);

		allBoxes = this.boxService.findAll();
		Assert.isTrue(allBoxes.contains(saved));
		Assert.isTrue(a.getMessageBoxes().size() == 5);

		super.unauthenticate();
	}

}
