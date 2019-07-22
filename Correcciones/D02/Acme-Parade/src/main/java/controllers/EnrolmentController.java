
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import services.PositionService;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Position;

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

	@Autowired
	private PositionService		positionService;


	// Constructors -----------------------------------------------------------

	public EnrolmentController() {
		super();
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Member principal = this.memberService.findPrincipal();
		List<Enrolment> enrolments;
		enrolments = principal.getEnrolments();
		List<Brotherhood> bhs;
		bhs = new ArrayList<>();
		List<Date> dates;
		dates = new ArrayList<>();
		for (final Enrolment e : enrolments) {
			bhs.add(e.getBrotherhood());
			dates.add(e.getMoment());
		}

		result = this.createModelAndViewWithSystemConfiguration("enrolment/member/list");
		result.addObject("enrolments", enrolments);
		result.addObject("brotherhoods", bhs);
		result.addObject("dates", dates);
		result.addObject("size", dates.size());

		return result;
	}
	// Leave brotherhood ------------------------------------------------------

	@RequestMapping(value = "/member/leave", method = RequestMethod.POST)
	public ModelAndView leave(@RequestParam(value = "id") final int id) {
		final Member principal = this.memberService.findPrincipal();
		final Enrolment enrolmentPrincipalIsLeaving = this.enrolmentService.findOne(id);
		final Date now = new Date();
		for (final Enrolment enrolment : principal.getEnrolments())
			if (enrolment.equals(enrolmentPrincipalIsLeaving)) {
				enrolment.setExitMoment(now);
				this.enrolmentService.save(enrolment);
			}
		return this.list();
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

	// List -------------------------------------------------------------------

	@RequestMapping(value = "brotherhood/list", method = RequestMethod.GET)
	public ModelAndView listBrotherhood() {
		final ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId());
		final List<Enrolment> enrolments = (List<Enrolment>) brotherhood.getEnrolments();

		result = this.createModelAndViewWithSystemConfiguration("enrolment/brotherhood/list");
		result.addObject("enrolments", enrolments);

		/*
		 * result.addObject("positionEng", enrl.getPosition().getStrings().values().toArray()[0]);
		 * result.addObject("positionEsp", enrl.getPosition().getStrings().values().toArray()[1]);
		 */

		return result;
	}

	// Info -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int enrolmentId) {
		// Create result object
		ModelAndView result;
		Enrolment enrolment;
		result = this.createModelAndViewWithSystemConfiguration("enrolment/brotherhood/show");
		enrolment = this.enrolmentService.findOne(enrolmentId);
		final String locale = Locale.getDefault().getLanguage();

		result.addObject("enrolment", enrolment);
		result.addObject("member", enrolment.getMember());
		result.addObject("locale", locale);
		result.addObject("en", enrolment.getPosition().getStrings().values().toArray()[0]);
		result.addObject("es", enrolment.getPosition().getStrings().values().toArray()[1]);

		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int enrolmentId) {
		ModelAndView result;
		final Enrolment e = this.enrolmentService.findOne(enrolmentId);
		final Brotherhood actual = this.brotherhoodService.findPrincipal();
		if (e.getBrotherhood() != actual)
			result = this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");

		final List<Enrolment> ls1 = (List<Enrolment>) actual.getEnrolments();

		Enrolment enrolment;
		result = this.createModelAndViewWithSystemConfiguration("enrolment/brotherhood/edit");
		enrolment = this.enrolmentService.findOne(enrolmentId);
		if (e.getBrotherhood() != actual)
			result = this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		final String locale = Locale.getDefault().getLanguage();
		final List<Position> ls = this.positionService.findAll();

		result.addObject("enrolment", enrolment);
		result.addObject("member", enrolment.getMember());
		result.addObject("positions", ls);
		result.addObject("locale", locale);
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
				if (enrolment.getBrotherhood() == null)
					result = this.createModelAndViewWithSystemConfiguration("redirect:/enrolment/member/list.do");
				else {
					enrolment.setMoment(new Date());
					this.enrolmentService.save(enrolment);
				}
				result = this.createModelAndViewWithSystemConfiguration("redirect:/enrolment/member/list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(enrolment, "enrolment.commit.error");
			}
		return result;
	}

	// Save -------------------------------------------------------------------
	@RequestMapping(value = "/brotherhood/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView saveEnrolment(@Valid final Enrolment enrolment, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(enrolment);
		else
			try {
				this.enrolmentService.save(enrolment);

				result = this.createModelAndViewWithSystemConfiguration("redirect:/enrolment/brotherhood/list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(enrolment, "enrolment.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/member/info", method = RequestMethod.GET)
	public ModelAndView info(@RequestParam final int id) {
		ModelAndView result;
		final Member actual = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Enrolment enrl = this.enrolmentService.findOne(id);

		if (enrl.getMember().getId() == actual.getId()) {
			result = this.createModelAndViewWithSystemConfiguration("/enrolment/member/info");
			result.addObject("enrolment", enrl);
		} else
			result = this.createModelAndViewWithSystemConfiguration("redirect:../j_spring_security_logout");

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
	 * result = this.createModelAndViewWithSystemConfiguration("enrolment/edit");
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

		final Member actual = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		final List<Enrolment> ls = actual.getEnrolments();
		brotherhoods = this.brotherhoodService.findAll();
		for (final Enrolment e : ls)
			if (e.getExitMoment() == null)
				brotherhoods.remove(e.getBrotherhood());

		result = this.createModelAndViewWithSystemConfiguration("enrolment/member/create");
		result.addObject("enrolment", enrolment);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", message);

		return result;
	}
}
