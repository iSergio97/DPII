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
import forms.AdministratorForm;
import forms.BrotherhoodForm;
import forms.MemberForm;

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
		final MemberForm mf = this.memberService.deconstruct(member);
		final String reqURI = "member";

		result = new ModelAndView("profile/member/show");
		result.addObject("actor", mf);
		result.addObject("reqURI", reqURI);
		return result;
	}

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.GET)
	public ModelAndView brotherhoodShow() {
		ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId());
		final BrotherhoodForm bhForm = this.brotherhoodService.deconstruct(brotherhood);

		final String reqURI = "brotherhood";
		result = new ModelAndView("profile/brotherhood/show");
		result.addObject("actor", bhForm);
		result.addObject("reqURI", reqURI);
		return result;
	}

	@RequestMapping(value = "/admin/show", method = RequestMethod.GET)
	public ModelAndView showAdmin() {
		ModelAndView result;
		final Administrator admin = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final AdministratorForm adminf = this.administratorService.deconstruct(admin);
		final String reqURI = "admin";

		result = new ModelAndView("profile/admin/show");
		result.addObject("actor", adminf);
		result.addObject("admin", reqURI);

		return result;
	}
	@RequestMapping(value = "member/edit", method = RequestMethod.GET)
	public ModelAndView editMember() {
		ModelAndView result;
		final Member member = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		final MemberForm memberf = this.memberService.deconstruct(member);
		final String reqURI = "member";
		result = new ModelAndView("profile/member/edit");
		result.addObject("memberf", memberf);
		result.addObject("reqURI", reqURI);

		return result;
	}

	@RequestMapping(value = "brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView editBrotherhood() {
		ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId());
		final BrotherhoodForm brotherhoodf = this.brotherhoodService.deconstruct(brotherhood);
		final String reqURI = "brotherhood";
		result = new ModelAndView("profile/brotherhood/edit");
		result.addObject("brotherhoodf", brotherhoodf);
		result.addObject("reqURI", reqURI);

		return result;
	}

	@RequestMapping(value = "admin/edit", method = RequestMethod.GET)
	public ModelAndView editAdmin() {
		ModelAndView result;
		final Administrator admin = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final AdministratorForm adminf = this.administratorService.deconstruct(admin);
		final String reqURI = "admin";
		result = new ModelAndView("profile/admin/edit");
		result.addObject("adminf", adminf);
		result.addObject("reqURI", reqURI);

		return result;
	}

	@RequestMapping(value = "member/export", method = RequestMethod.GET)
	public ModelAndView exportMember() {
		ModelAndView result;
		final Member member = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		final String reqURI = "member";
		result = new ModelAndView("profile/member/edit");
		result.addObject("actor", member);
		result.addObject("username", member.getUserAccount().getUsername());
		result.addObject("password", member.getUserAccount().getPassword());
		result.addObject("member", reqURI);

		return result;
	}

	@RequestMapping(value = "brotherhood/export", method = RequestMethod.GET)
	public ModelAndView exportBrotherhood() {
		ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId());
		final String reqURI = "brotherhood";
		result = new ModelAndView("profile/brotherhood/edit");
		result.addObject("actor", brotherhood);
		result.addObject("username", brotherhood.getUserAccount().getUsername());
		result.addObject("password", brotherhood.getUserAccount().getPassword());
		result.addObject("size", brotherhood.getPictures().size());
		result.addObject("brotherhood", reqURI);

		return result;
	}

	@RequestMapping(value = "admin/export", method = RequestMethod.GET)
	public ModelAndView exportAdmin() {
		ModelAndView result;
		final Administrator admin = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final String reqURI = "admin";
		result = new ModelAndView("profile/admin/edit");
		result.addObject("actor", admin);
		result.addObject("username", admin.getUserAccount().getUsername());
		result.addObject("password", admin.getUserAccount().getPassword());
		result.addObject("admin", reqURI);

		return result;
	}

}
