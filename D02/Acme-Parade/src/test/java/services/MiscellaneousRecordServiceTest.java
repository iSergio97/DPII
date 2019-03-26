
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	// SUT -------------------------------------------------
	@Autowired
	private MiscellaneousRecordService	miscRecordService;


	//Drivers -----------------------------------------------

	@Test
	public void driverGet() {

		final Object testingData[][] = {
			{
				"brotherhood1", "linkRecord1", null
			}, {
				"brotherhood2", "linkRecord1", null
			}, {
				"member1", "linkRecord1", null
			}, {
				"admin1", "linkRecord1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateGet((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				"brotherhood1", null
			}, {
				null, null, IllegalArgumentException.class
			}, {
				"member1", IllegalArgumentException.class
			}, {
				"admin1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				"brotherhood1", "miscellaneousRecord1", null
			}, {
				null, "miscellaneousRecord1", NullPointerException.class
			}, {
				"brotherhood1", "miscellaneousRecord2", AssertionError.class
			}, {
				"member1", "miscellaneousRecord1", AssertionError.class
			}, {
				"admin1", "miscellaneousRecord1", NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				"brotherhood1", "miscellaneousRecord1", null
			}, {
				null, "miscellaneousRecord1", IllegalArgumentException.class
			}, {
				"member1", "miscellaneousRecord1", IllegalArgumentException.class
			}, {
				"administrator1", "miscellaneousRecord1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void templateGet(final String username, final int mRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.miscRecordService.findOne(mRecordId);
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
			this.miscRecordService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateSave(final String username, final int mRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final MiscellaneousRecord mr = this.miscRecordService.findOne(mRecordId);
			mr.setTitle("SUT title");
			this.miscRecordService.save(mr);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateDelete(final String username, final int mRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final MiscellaneousRecord mr = this.miscRecordService.findOne(mRecordId);
			this.miscRecordService.delete(mr);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
