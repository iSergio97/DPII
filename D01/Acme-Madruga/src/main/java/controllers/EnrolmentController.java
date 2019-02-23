
package controllers;

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
				result = new ModelAndView("redirect:..welcome/index.do");
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
		List<Brotherhood> brotherhoods;

		//TODO: Cambiar los add por la lista del systemConfiguration
		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("enrolment/create");
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}
}
