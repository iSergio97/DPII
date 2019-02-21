
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.EnrolmentService;
import domain.Brotherhood;
import domain.Enrolment;

@Controller
@RequestMapping("/enrolment")
public class EnrolmentController extends AbstractController {

	@Autowired
	EnrolmentService	enrolmentService;

	@Autowired
	BrotherhoodService	brotherhoodService;


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

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Enrolment enrolment, final BindingResult bindingResult) {
		ModelAndView result;
		if (bindingResult.hasErrors())
			result = this.createAndEditModelAndView(enrolment);
		else
			try {
				this.enrolmentService.save(enrolment);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(enrolment, "enrolment.commit.error");
			}

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final Enrolment enrolment) {
		ModelAndView result;

		result = this.createAndEditModelAndView(enrolment, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final Enrolment enrolment, final String message) {
		ModelAndView result;
		List<String> positions;
		List<Brotherhood> brotherhoods;

		if (enrolment.getBrotherhood() == null) {
			positions = null;
			brotherhoods = null;
		} else {
			brotherhoods = this.brotherhoodService.findAll();
			positions = new ArrayList<>();
			positions.add("enrolment.president");
			positions.add("enrolment.vicePresident");
			positions.add("enrolment.secretary");
			positions.add("enrolment.treasurer");
			positions.add("enrolment.historian");
			positions.add("enrolment.fundraiser");
			positions.add("enrolment.officer");
		}

		result = new ModelAndView("enrolment/create");
		result.addObject("brothergoods", brotherhoods);
		result.addObject("positons", positions);

		return result;
	}
}
