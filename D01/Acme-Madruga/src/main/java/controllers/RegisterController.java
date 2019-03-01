
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.MemberService;
import services.MessageBoxService;
import domain.Brotherhood;
import domain.Member;
import domain.MessageBox;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MessageBoxService	messageBoxService;


	public RegisterController() {
		super();
	}

	//Create -----------------------------------------------------------------
	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView registerMember() {
		ModelAndView result;
		Member member;

		member = this.memberService.create();
		result = this.createEditModelAndView(member);

		return result;

	}

	//Save --------------------------------------------------------------------
	@RequestMapping(value = "/member/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Member member, final BindingResult bindingResult) {
		ModelAndView result;
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(member);
		else
			try {
				this.memberService.save(member);
				for (final MessageBox mb : member.getMessageBoxes()) {
					mb.setActor(member);
					this.messageBoxService.save(mb);
				}
				result = new ModelAndView("redirect:../profile/show.do");
			} catch (final Throwable e) {
				result = this.createAndEditModelAndView(member, "register.member.error");
			}

		return result;
	}

	//Create -----------------------------------------------------------------
	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView registerBrotherhood() {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.create();
		result = this.createEditModelAndView(brotherhood);

		return result;

	}

	//Save --------------------------------------------------------------------
	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Brotherhood brotherhood, final BindingResult bindingResult) {
		ModelAndView result;
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(brotherhood);
		else
			try {
				this.brotherhoodService.save(brotherhood);
				for (final MessageBox mb : brotherhood.getMessageBoxes()) {
					mb.setActor(brotherhood);
					this.messageBoxService.save(mb);
				}
				result = new ModelAndView("redirect:../profile/show.do");
			} catch (final Throwable e) {
				result = this.createAndEditModelAndView(brotherhood, "register.brotherhood.error");
			}

		return result;
	}

	// Ancillary Methods for members ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Member member) {
		ModelAndView result;

		result = this.createAndEditModelAndView(member, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final Member member, final String message) {
		ModelAndView result;
		final String s = "member";
		result = new ModelAndView("register/member/create");
		result.addObject("member", member);
		result.addObject("requestURI", s);
		return result;
	}

	// Ancillary Methods for brotherhoods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood) {
		ModelAndView result;

		result = this.createAndEditModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final Brotherhood brotherhood, final String message) {
		ModelAndView result;
		final String s = "brotherhood";

		result = new ModelAndView("register/brotherhood/create");
		result.addObject("brotherhood", brotherhood);
		result.addObject("brotherhood", s);

		return result;
	}
}
