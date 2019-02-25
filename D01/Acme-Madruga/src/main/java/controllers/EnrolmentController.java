
package controllers;

import java.util.ArrayList;
import java.util.Collection;
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

@Controller
@RequestMapping("/enrolment")
public class EnrolmentController extends AbstractController {

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MemberService		memberService;


	public EnrolmentController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Enrolment enrolment;

		enrolment = this.enrolmentService.create();
		result = this.createAndEditModelAndView(enrolment);

		return result;
	}

	//TODO: Falta añadir vista /edit para probar método save
	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Enrolment enrolment, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createAndEditModelAndView(enrolment);
		else
			try {
				this.enrolmentService.save(enrolment);
				final Member member = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
				final List<Enrolment> enrolments = member.getEnrolments();
				final List<Brotherhood> bhs = new ArrayList<>();
				for (final Enrolment e : enrolments)
					bhs.add(e.getBrotherhood());

				result = new ModelAndView("enrolment/edit.do");
				result.addObject("brotherhood", bhs);
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(enrolment, "enrolment.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Member actual = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		final List<Enrolment> enrolments = actual.getEnrolments();
		final List<Brotherhood> bhs = new ArrayList<>();
		for (final Enrolment e : enrolments)
			bhs.add(e.getBrotherhood());

		result = new ModelAndView("enrolment/list.do");
		result.addObject("brotherhood", bhs);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final Enrolment enrolment) {
		ModelAndView result;

		result = this.createAndEditModelAndView(enrolment, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final Enrolment enrolment, final String message) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		//TODO: La solicitud se hace al cargo más pequeño y la hermanada decide si cambiarlo a otro o no
		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("enrolment/create");
		result.addObject("enrolment", enrolment);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", message);

		return result;
	}
}
