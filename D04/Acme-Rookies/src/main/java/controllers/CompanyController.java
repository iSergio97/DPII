
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Company;
import domain.Position;
import services.CompanyService;
import services.PositionService;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;


	public CompanyController() {
		super();
	}

	// Public list ------------------------------------------------------------
	@RequestMapping(value = "/all/list", method = RequestMethod.GET)
	public ModelAndView publicList() {
		ModelAndView res;
		final Collection<Company> companies = this.companyService.findAll();

		res = new ModelAndView("company/all/list");
		res.addObject("companies", companies);

		return res;

	}

	// Public show ------------------------------------------------------------

	@RequestMapping(value = "/all/show", method = RequestMethod.GET)
	public ModelAndView publicShow(@RequestParam final int companyId) {
		ModelAndView res;
		final Company company = this.companyService.findOne(companyId);
		final Collection<Position> positions = this.positionService.findPositionsForPublic(company);

		res = new ModelAndView("company/all/show");
		res.addObject("positions", positions);
		return res;
	}
}
