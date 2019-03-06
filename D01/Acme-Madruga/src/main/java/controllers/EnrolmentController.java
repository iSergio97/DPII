
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import forms.EnrolmentForm;

@Controller
@RequestMapping("/enrolment")
public class EnrolmentController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MemberService		memberService;


	// Constructors -----------------------------------------------------------

	public EnrolmentController() {
		super();
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Member actual = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		final List<Enrolment> enrolments = actual.getEnrolments();
		final List<Brotherhood> bhs = new ArrayList<>();
		final List<Date> dates = new ArrayList<>();
		for (final Enrolment e : enrolments) {
			bhs.add(e.getBrotherhood());
			dates.add(e.getMoment());
		}

		result = new ModelAndView("enrolment/member/list");
		result.addObject("brotherhood", bhs);
		result.addObject("dates", dates);

		return result;
	}
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EnrolmentForm enrolment;

		enrolment = this.enrolmentService.createForm();
		result = this.createEditModelAndView(enrolment);

		return result;
	}

	// Edit -------------------------------------------------------------------
	/*
	 * TODO: REVISAR
	 * 
	 * @RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	 * public ModelAndView edit(@RequestParam final int enrolmentId, final BindingResult bindingResult) {
	 * ModelAndView result;
	 * EnrolmentForm enrolment;
	 * enrolment = this.enrolmentService.findOne(rec.getId());
	 * Assert.notNull(enrolment);
	 * result = this.createEditModelAndView(enrolment);
	 * 
	 * return result;
	 * }
	 */
	// Save -------------------------------------------------------------------
	@RequestMapping(value = "/member/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final EnrolmentForm enrolment, final BindingResult bindingResult) {
		ModelAndView result;
		Enrolment enrolment2;
		enrolment.setMoment(new Date());

		enrolment2 = this.enrolmentService.reconstructForm(enrolment, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(enrolment);
		else
			try {
				this.enrolmentService.save(enrolment2);

				result = new ModelAndView("redirect:/enrolment/member-brotherhood/list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(enrolment, "enrolment.commit.error");
			}
		return result;
	}
	// Delete -----------------------------------------------------------------
	/*
	 * @RequestMapping(value = "/edit", params = "delete", method = RequestMethod.POST)
	 * public ModelAndView delete(final Enrolment enrolment, final BindingResult bindingResult) {
	 * ModelAndView result;
	 * 
	 * try {
	 * this.enrolmentService.delete(enrolment);
	 * 
	 * result = new ModelAndView("enrolment/edit");
	 * } catch (final Throwable oops) {
	 * result = this.createAndEditModelAndView(enrolment, "enrolment.commit.error");
	 * }
	 * return result;
	 * }
	 */
	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EnrolmentForm enrolment) {
		ModelAndView result;

		result = this.createAndEditModelAndView(enrolment, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final EnrolmentForm enrolment, final String message) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		//TODO: La solicitud se hace al cargo más pequeño y la hermandad decide si cambiarlo a otro o no
		final Member actual = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		final List<Enrolment> ls = actual.getEnrolments();
		brotherhoods = this.brotherhoodService.findAll();
		for (final Enrolment e : ls)
			brotherhoods.remove(e.getBrotherhood());

		result = new ModelAndView("enrolment/member/create");
		result.addObject("enrolment", enrolment);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", message);

		return result;
	}
}
