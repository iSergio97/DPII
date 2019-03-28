
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.LegalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LegalRecordServiceTest extends AbstractTest {

	// SUT -------------------------------------------------
	@Autowired
	private LegalRecordService	legalRecordService;


	//Drivers -----------------------------------------------

	@Test
	public void driverGet() {

		final Object testingData[][] = {
			{
				"brotherhood1", "legalRecord1", null
			}, {
				"brotherhood2", "legalRecord1", null
			}, {
				"member1", "legalRecord1", null
			}, {
				"admin1", "legalRecord1", null
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
				"brotherhood1", "legalRecord1", null
			}, {
				null, "legalRecord1", IllegalArgumentException.class
			}, {
				"brotherhood1", "legalRecord2", IllegalArgumentException.class
			}, {
				"member1", "legalRecord1", IllegalArgumentException.class
			}, {
				"admin1", "legalRecord1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				"brotherhood1", "legalRecord1", null
			}, {
				null, "legalRecord1", IllegalArgumentException.class
			}, {
				"member1", "legalRecord1", IllegalArgumentException.class
			}, {
				"administrator1", "legalRecord1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void templateGet(final String username, final int lRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.legalRecordService.findOne(lRecordId);
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
			this.legalRecordService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateSave(final String username, final int lRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final LegalRecord lr = this.legalRecordService.findOne(lRecordId);
			lr.setTitle("SUT title");
			this.legalRecordService.save(lr);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateDelete(final String username, final int lRecordId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final LegalRecord lr = this.legalRecordService.findOne(lRecordId);
			this.legalRecordService.delete(lr);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
