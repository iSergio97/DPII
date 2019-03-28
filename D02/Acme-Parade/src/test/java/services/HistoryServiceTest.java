
package services;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.History;
import domain.Record;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HistoryServiceTest extends AbstractTest {

	// SUT -------------------------------------------------
	@Autowired
	private HistoryService	historyService;


	//Supporting Services ----------------------------------

	//Drivers -----------------------------------------------

	@Test
	public void driverRegister() {

		final Object testingData[][] = {
			{
				"brotherhood1", "history1", null
			}, {
				"brotherhood2", "history1", null
			}, {
				"member1", "history1", null
			}, {
				"admin1", "history1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegister((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				"brotherhood4", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"member1", IllegalArgumentException.class
			}, {
				"admin1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				"brotherhood1", "history1", null
			}, {
				null, "history1", IllegalArgumentException.class
			}, {
				"brotherhood1", "history2", IllegalArgumentException.class
			}, {
				"member1", "history1", IllegalArgumentException.class
			}, {
				"admin1", "history1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				"brotherhood1", "history1", null
			}, {
				null, "history1", IllegalArgumentException.class
			}, {
				"member1", "history1", IllegalArgumentException.class
			}, {
				"admin1", "history1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void templateRegister(final String username, final int historyId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.historyService.findOne(historyId);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateCreate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.historyService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateSave(final String username, final int historyId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final History h = this.historyService.findOne(historyId);
			h.setRecords(new ArrayList<Record>());
			this.historyService.save(h);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateDelete(final String username, final int historyId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final History h = this.historyService.findOne(historyId);
			this.historyService.delete(h);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
