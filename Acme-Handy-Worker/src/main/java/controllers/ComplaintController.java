
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import services.RefereeService;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Report;

@Controller
@RequestMapping("/complaint")
public class ComplaintController extends AbstractController {

	//-- Services --

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private FixUpTaskService	futService;


	//-- Constructors --

	public ComplaintController() {
		super();
	}

	// List --------------------------------------------
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Complaint> complaints;

		final Customer c = this.customerService.findPrincipal();
		complaints = this.complaintService.getComplaints(c);

		result = new ModelAndView("complaint/customer/list");
		result.addObject("complaints", complaints);
		result.addObject("customer", c);
		result.addObject("requestURI", "complaint/customer/list.do");

		return result;
	}

	@RequestMapping(value = "/referee/list", method = RequestMethod.GET)
	public ModelAndView listReferee() {
		ModelAndView result;
		Collection<Complaint> complaints;

		complaints = this.complaintService.getUnassignedComplaints();
		result = new ModelAndView("complaint/referee/list");

		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/referee/list.do");

		return result;

	}

	//Create----------------------------------------------

	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Complaint complaint;

		complaint = this.complaintService.create();

		result = this.createEditModelAndView(complaint);

		return result;
	}

	//Save-----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(complaint);
		else
			try {
				this.complaintService.save(complaint);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}
		return result;
	}

	//Show----------------------------------------------
	@RequestMapping(value = "/customer/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId) {
		ModelAndView result;
		Complaint complaint;
		Collection<Report> reports;

		result = new ModelAndView("complaint/customer/show");
		complaint = this.complaintService.findById(complaintId);
		reports = complaint.getReports();

		result.addObject("complaint", complaint);
		result.addObject("reports", reports);

		return result;
	}

	// List-----------------------------------------------

	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView listReferee() {
	//		ModelAndView result;
	//		Collection<Complaint> complaints;
	//		Actor a;
	//		Referee r;
	//		a = this.actorService.findPrincipal();
	//		Assert.isTrue(a.getUserAccount().getAuthorities().contains(Authority.REFEREE));
	//		r = this.refereeService.findById(a.getId());
	//
	//		complaints = this.complaintService.getComplaints(r);
	//
	//		result = new ModelAndView("complaint/list");
	//		result.addObject("complaints", complaints);
	//
	//		return result;
	//	}

	//Ancillary Methods-----------------------------------

	protected ModelAndView createEditModelAndView(final Complaint complaint) {

		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode) {
		ModelAndView result;
		Collection<FixUpTask> futs;

		futs = this.futService.findAll();

		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("fixUpTasks", futs);
		result.addObject("message", messageCode);

		return result;

	}
}
