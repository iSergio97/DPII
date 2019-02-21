
package controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.ExitService;
import services.MemberService;
import domain.Brotherhood;
import domain.Exit;
import domain.Member;

public class ExitController {

	@Autowired
	private ExitService			exitService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MemberService		memberService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Exit exit;

		exit = this.exitService.create();
		result = this.createEditModelAndView(exit);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int exitId) {
		ModelAndView result;
		Exit exit;

		exit = this.exitService.findOne(exitId);
		Assert.notNull(exit);
		result = this.createEditModelAndView(exit);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Exit exit, final BindingResult binding) {
		ModelAndView result;

		if(
			result = this.createEditModelAndView(exit);
		else
			try {
				this.exitService.save(exit);
				result = new ModelAndView("redirect::list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(exit, "exit.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Exit exit, final BindingResult binding) {
		ModelAndView result;

		try {
			this.exitService.delete(exit);
			result = new ModelAndView("redirect::list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(exit, "exit.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Exit exit) {
		ModelAndView result;

		result = this.createEditModelAndView(exit, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Exit exit, final String messageCode) {
		ModelAndView result;
		final Date moment;
		final Brotherhood brotherhood;
		final Member member;

		final int principalId = LoginService.getPrincipal().getId();
		member = this.memberService.findOne(principalId);

		result = new ModelAndView("exit/member/edit");
		result.addObject("moment", moment);
		result.addObject("brotherhood", brotherhood);
		result.addObject("member", member);
		result.addObject("exit", exit);

		result.addObject("message", messageCode);

		return null;
	}
}
