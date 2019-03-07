/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import services.BrotherhoodService;
import services.MemberService;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private MemberService			memberService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private AdministratorService	administratorService;


	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/member/show", method = RequestMethod.GET)
	public ModelAndView memberShow() {
		ModelAndView result;
		final Member member = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());

		result = new ModelAndView("profile/member/show");
		result.addObject("actor", member);
		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.GET)
	public ModelAndView brotherhoodShow() {
		ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId());

		final String reqURI = "brotherhood";
		result = new ModelAndView("profile/brotherhood/show");
		result.addObject("actor", brotherhood);
		result.addObject("reqURI", reqURI);
		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping(value = "/admin/show", method = RequestMethod.GET)
	public ModelAndView showAdmin() {
		ModelAndView result;
		final Administrator admin = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());

		result = new ModelAndView("profile/admin/show");
		result.addObject("actor", admin);

		return result;
	}

}
