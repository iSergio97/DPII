
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Season;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SeasonServiceTest extends AbstractTest {

	// SUT -------------------------------------------------
	@Autowired
	private SeasonService	seasonService;


	//Drivers -----------------------------------------------

	@Test
	public void driverGet() {

		final Object testingData[][] = {
			{
				"user1", "serie1season1", null
			}, {
				"publisher1", "serie1season1", null
			}, {
				"critic1", "serie1season1", null
			}, {
				"admin1", "serie1season1", null
			}, {
				null, "serie1season1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateGet((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				"publisher1", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"user1", IllegalArgumentException.class
			}, {
				"critic1", IllegalArgumentException.class
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
				"publisher1", "serie1season1", null
			}, {
				null, "serie1season1", IllegalArgumentException.class
			}, {
				"admin1", "serie1season1", null
			}, {
				"user1", "serie1season1", IllegalArgumentException.class
			}, {
				"critic1", "serie1season1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				"publisher1", "serie1season1", null
			}, {
				null, "serie1season1", IllegalArgumentException.class
			}, {
				"user1", "serie1season1", IllegalArgumentException.class
			}, {
				"critic1", "serie1season1", IllegalArgumentException.class
			}, {
				"admin1", "serie1season1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void templateGet(final String username, final int seasonId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.seasonService.findOne(seasonId);
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
			this.seasonService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateSave(final String username, final int seasonId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Season s = this.seasonService.findOne(seasonId);
			s.setNumber(999);
			this.seasonService.save(s);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateDelete(final String username, final int seasonId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Season s = this.seasonService.findOne(seasonId);
			this.seasonService.delete(s);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
