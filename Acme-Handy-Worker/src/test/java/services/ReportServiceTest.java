
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
import domain.Report;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportServiceTest extends AbstractTest {

	// Service under test -------------------------------------------

	@Autowired
	private ReportService	reportService;


	@Test
	public void testSaveReport() {
		Report report, saved;
		Collection<Report> reports;

		report = this.reportService.create();
		//inicializarlo falta
		saved = this.reportService.save(report);
		reports = this.reportService.findAll();
		Assert.isTrue(reports.contains(saved));
	}

}
