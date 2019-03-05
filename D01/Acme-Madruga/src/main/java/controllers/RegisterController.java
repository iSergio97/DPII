
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import security.UserAccountRepository;
import services.BrotherhoodService;
import services.FinderService;
import services.MemberService;
import services.MessageBoxService;
import domain.Brotherhood;
import domain.Finder;
import domain.Member;
import domain.MessageBox;
import forms.BrotherhoodForm;
import forms.MemberForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	@Autowired
	private MemberService			memberService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private FinderService			finderService;


	public RegisterController() {
		super();
	}

	//Create -----------------------------------------------------------------
	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView registerMember() {
		ModelAndView result;
		MemberForm member;

		member = this.memberService.createForm();
		result = this.createEditModelAndView(member);

		return result;

	}

	//Save --------------------------------------------------------------------
	@RequestMapping(value = "/member/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MemberForm member, final BindingResult bindingResult) {
		ModelAndView result;
		Member member2;

		member2 = this.memberService.reconstructForm(member, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(member);
		else
			try {
				if (member2.getId() == 0) {
					final Finder finder = member2.getFinder();
					this.finderService.save(finder);
					member2.setFinder(finder);
					member2.getUserAccount().setUsername(member.getUsername());
					member2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(member.getPassword(), null));
					final UserAccount ua = member2.getUserAccount();
					this.userAccountRepository.save(ua);
					member2.setUserAccount(ua);
					this.memberService.save(member2);
					final Collection<MessageBox> mbs = this.messageBoxService.createSystemBoxes();
					for (final MessageBox mb : mbs) {
						mb.setActor(member2);
						this.messageBoxService.save(mb);
					}
				} else
					this.memberService.save(member2);
				result = new ModelAndView("redirect:../profile/show.do");
			} catch (final Throwable e) {
				result = this.createAndEditModelAndView(member, "register.member.error");
			}

		return result;
	}
	/*
	 * //Save --------------------------------------------------------------------
	 * 
	 * @RequestMapping(value = "/member/edit", method = RequestMethod.POST, params = "save")
	 * public ModelAndView save(final Member member, final BindingResult bindingResult) {
	 * ModelAndView result;
	 * final Member member2;
	 * 
	 * member2 = this.memberService.reconstruct(member, bindingResult);
	 * if (bindingResult.hasErrors())
	 * result = this.createEditModelAndView(member2);
	 * else
	 * try {
	 * final UserAccount ua = member2.getUserAccount();
	 * this.userAccountRepository.save(ua);
	 * member2.setUserAccount(ua);
	 * if (member.getId() == 0) {
	 * this.memberService.save(member2);
	 * for (final MessageBox mb : member2.getMessageBoxes()) {
	 * mb.setActor(member2);
	 * this.messageBoxService.save(mb);
	 * }
	 * } else
	 * this.memberService.save(member2);
	 * result = new ModelAndView("redirect:../profile/show.do");
	 * } catch (final Throwable e) {
	 * result = this.createAndEditModelAndView(member2, "register.member.error");
	 * }
	 * 
	 * return result;
	 * }
	 */

	//Create -----------------------------------------------------------------
	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView registerBrotherhood() {
		ModelAndView result;
		BrotherhoodForm brotherhood;

		brotherhood = this.brotherhoodService.createForm();
		result = this.createEditModelAndView(brotherhood);

		return result;

	}

	//Save --------------------------------------------------------------------
	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final BrotherhoodForm brotherhood, final BindingResult bindingResult) {
		ModelAndView result;
		Brotherhood brotherhood2;

		brotherhood2 = this.brotherhoodService.reconstruct(brotherhood, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(brotherhood);
		else
			try {
				if (brotherhood.getId() == 0) {
					this.brotherhoodService.save(brotherhood2);
					for (final MessageBox mb : brotherhood2.getMessageBoxes()) {
						mb.setActor(brotherhood2);
						this.messageBoxService.save(mb);
					}
				} else
					this.brotherhoodService.save(brotherhood2);
				result = new ModelAndView("redirect:../profile/show.do");
			} catch (final Throwable e) {
				result = this.createAndEditModelAndView(brotherhood, "register.brotherhood.error");
			}

		return result;
	}

	// Ancillary Methods for members ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MemberForm member) {
		ModelAndView result;

		result = this.createAndEditModelAndView(member, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final MemberForm member, final String message) {
		ModelAndView result;
		final String s = "member";
		result = new ModelAndView("register/member/create");
		result.addObject("member", member);
		result.addObject("requestURI", s);
		return result;
	}

	// Ancillary Methods for brotherhoods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final BrotherhoodForm brotherhood) {
		ModelAndView result;

		result = this.createAndEditModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final BrotherhoodForm brotherhood, final String message) {
		ModelAndView result;
		final String s = "brotherhood";

		result = new ModelAndView("register/brotherhood/create");
		result.addObject("brotherhood", brotherhood);
		result.addObject("requestURI", s);

		return result;
	}
}
