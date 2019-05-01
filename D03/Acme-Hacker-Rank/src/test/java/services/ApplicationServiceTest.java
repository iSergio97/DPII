
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.Validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private PositionService		positionService;
	@Autowired
	private CurriculumService	curriculumService;

	// Testing data -----------------------------------------------------------

	private final Object[][]	testingData	= {
		{
		"explanations", "https://www.imgur.com", "PENDING", null
		}, {
		"explanations", "a", "PENDING", IllegalArgumentException.class
		}
											};


	// Test template ----------------------------------------------------------

	private void template(final String explanations, final String codeLink, final String status, final Class<?> expectedThrowableClass) {
		Class<?> throwableClass = null;
		try {
			Application application = this.applicationService.create();
			application.setMoment(new Date());
			application.setSubmitMoment(new Date());
			application.setExplanations(explanations);
			application.setCodeLink(codeLink);
			application.setStatus(status);
			application.setPosition(this.positionService.findAll().get(0));
			application.setProblem(application.getPosition().getProblems().iterator().next());
			application.setCurriculum(this.curriculumService.findAll().get(0));
			application.setHacker(application.getCurriculum().getHacker());
			application = this.applicationService.save(application);
			Assert.isTrue(Validation.buildDefaultValidatorFactory().getValidator().validate(application).size() == 0);
		} catch (final Throwable throwable) {
			throwableClass = throwable.getClass();
		}

		super.checkExceptions(expectedThrowableClass, throwableClass);
	}

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		for (int i = 0; i < this.testingData.length; ++i)
			this.template((String) this.testingData[i][0], (String) this.testingData[i][1], (String) this.testingData[i][2], (Class<?>) this.testingData[i][3]);
	}

}
