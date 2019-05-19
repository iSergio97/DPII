
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.CompanyService;
import services.PositionService;
import domain.Publisher;
import domain.Serie;

@Controller
@RequestMapping("/company")
public class CompanyController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService	auditService;
	@Autowired
	private CompanyService	companyService;
	@Autowired
	private PositionService	positionService;


	// Constructor ------------------------------------------------------------

	public CompanyController() {
		super();
	}

	// Public list ------------------------------------------------------------

	@RequestMapping(value = "/all/list", method = RequestMethod.GET)
	public ModelAndView publicList() {
		ModelAndView res;
		final Collection<Publisher> companies = this.companyService.findAll();

		res = new ModelAndView("company/all/list");
		res.addObject("companies", companies);
		res.addObject("scoresByCompany", this.auditService.getScoresByCompany());

		return res;

	}

	// Public show ------------------------------------------------------------

	@RequestMapping(value = "/all/show", method = RequestMethod.GET)
	public ModelAndView publicShow(@RequestParam final int companyId) {
		ModelAndView res;
		final Publisher company = this.companyService.findOne(companyId);
		final Collection<Serie> positions = this.positionService.findPositionsForPublic(company);

		res = new ModelAndView("company/all/show");
		res.addObject("positions", positions);
		return res;
	}
}
