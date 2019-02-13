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

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import services.ActorService;
import services.AdminService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import domain.Actor;
import domain.Admin;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.SocialProfile;
import domain.Sponsor;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdminService			adminService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private RefereeService			refereeService;
	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int id = LoginService.getPrincipal().getId();
			final Actor actor = this.actorService.findByUserAccountId(id);

			result = new ModelAndView("profile/show");
			final List<Authority> au = (List<Authority>) actor.getUserAccount().getAuthorities();
			final Authority auth = au.get(0);
			if (auth.equals("HANDY_WORKER")) {
				final HandyWorker hw = this.handyWorkerService.findById(actor.getId());
				result.addObject("handyWorker", hw);
			}
			final List<SocialProfile> sp = (List<SocialProfile>) actor.getSocialProfiles();
			if (sp != null) {
				result = new ModelAndView("profile/show");
				result.addObject("actor", actor);
				result.addObject("socialProfiles", sp);
			} else
				result = new ModelAndView("redirect:../social-profile/edit.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../j_spring_security_logout");
		}

		return result;
	}
	// Edit GET -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editGet() {
		ModelAndView result;

		final int id = LoginService.getPrincipal().getId();
		final Actor actor = this.actorService.findByUserAccountId(id);

		result = new ModelAndView("profile/edit");
		result.addObject("actor", actor);
		return result;
	}

	//Edit POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editPost(@Valid final Actor actor, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			final UserAccount userAccount = this.userAccountRepository.findOne(actor.getUserAccount().getId());
			final String authority = userAccount.getAuthorities().toArray(new Authority[1])[0].getAuthority();
			switch (authority) {
			case Authority.ADMIN:
				Admin admin = this.adminService.findByUserAccountId(userAccount.getId());
				admin.setName(actor.getName());
				admin.setMiddleName(actor.getMiddleName());
				admin.setSurname(actor.getSurname());
				admin.setPhoto(actor.getPhoto());
				admin.setEmail(actor.getEmail());
				admin.setPhoneNumber(actor.getPhoneNumber());
				admin.setAddress(actor.getAddress());
				admin.setIsBanned(actor.getIsBanned());
				admin = this.adminService.save(admin);
				break;
			case Authority.CUSTOMER:
				Customer customer = this.customerService.findByUserAccountId(userAccount.getId());
				customer.setName(actor.getName());
				customer.setMiddleName(actor.getMiddleName());
				customer.setSurname(actor.getSurname());
				customer.setPhoto(actor.getPhoto());
				customer.setEmail(actor.getEmail());
				customer.setPhoneNumber(actor.getPhoneNumber());
				customer.setAddress(actor.getAddress());
				customer.setIsBanned(actor.getIsBanned());
				customer = this.customerService.save(customer);
				break;
			case Authority.HANDY_WORKER:
				HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userAccount.getId());
				handyWorker.setName(actor.getName());
				handyWorker.setMiddleName(actor.getMiddleName());
				handyWorker.setSurname(actor.getSurname());
				handyWorker.setPhoto(actor.getPhoto());
				handyWorker.setEmail(actor.getEmail());
				handyWorker.setPhoneNumber(actor.getPhoneNumber());
				handyWorker.setAddress(actor.getAddress());
				handyWorker.setIsBanned(actor.getIsBanned());
				handyWorker = this.handyWorkerService.save(handyWorker);
				break;
			case Authority.REFEREE:
				Referee referee = this.refereeService.findByUserAccountId(userAccount.getId());
				referee.setName(actor.getName());
				referee.setMiddleName(actor.getMiddleName());
				referee.setSurname(actor.getSurname());
				referee.setPhoto(actor.getPhoto());
				referee.setEmail(actor.getEmail());
				referee.setPhoneNumber(actor.getPhoneNumber());
				referee.setAddress(actor.getAddress());
				referee.setIsBanned(actor.getIsBanned());
				referee = this.refereeService.save(referee);
				break;
			case Authority.SPONSOR:
				Sponsor sponsor = this.sponsorService.findByUserAccountId(userAccount.getId());
				sponsor.setName(actor.getName());
				sponsor.setMiddleName(actor.getMiddleName());
				sponsor.setSurname(actor.getSurname());
				sponsor.setPhoto(actor.getPhoto());
				sponsor.setEmail(actor.getEmail());
				sponsor.setPhoneNumber(actor.getPhoneNumber());
				sponsor.setAddress(actor.getAddress());
				sponsor.setIsBanned(actor.getIsBanned());
				sponsor = this.sponsorService.save(sponsor);
				break;
			default:
			}
			result = this.show();
		} else {
			result = new ModelAndView("profile/edit");
			result.addObject("actor", actor);
			result.addObject("errors", binding);
		}

		return result;
	}

}
