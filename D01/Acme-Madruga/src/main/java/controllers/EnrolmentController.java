
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

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

	@RequestMapping(value = "/member-brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Member actual = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		final List<Enrolment> enrolments = actual.getEnrolments();
		final List<Brotherhood> bhs = new ArrayList<>();
		for (final Enrolment e : enrolments)
			bhs.add(e.getBrotherhood());

		result = new ModelAndView("enrolment/member-brotherhood/list");
		result.addObject("brotherhood", bhs);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Enrolment enrolment;

		enrolment = this.enrolmentService.create();
		result = this.createEditModelAndView(enrolment);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int enrolmentId) {
		ModelAndView result;
		final Enrolment enrolment;

		enrolment = this.enrolmentService.findOne(enrolmentId);
		Assert.notNull(enrolment);
		result = this.createEditModelAndView(enrolment);

		return result;
	}

	// Save -------------------------------------------------------------------
	@RequestMapping(value = "/member/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Enrolment enrolment, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(enrolment);
		else
			try {
				this.enrolmentService.save(enrolment);
				final Member member = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
				final List<Enrolment> enrolments = member.getEnrolments();
				final List<Brotherhood> bhs = new ArrayList<>();
				for (final Enrolment e : enrolments)
					bhs.add(e.getBrotherhood());

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

	protected ModelAndView createEditModelAndView(final Enrolment enrolment) {
		ModelAndView result;

		result = this.createAndEditModelAndView(enrolment, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final Enrolment enrolment, final String message) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		//TODO: La solicitud se hace al cargo más pequeño y la hermandad decide si cambiarlo a otro o no
		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("enrolment/member/create");
		result.addObject("enrolment", enrolment);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", message);

		return result;
	}
}
