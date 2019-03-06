
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
import services.AdministratorService;
import services.AreaService;
import services.BrotherhoodService;
import services.FinderService;
import services.MemberService;
import services.MessageBoxService;
import domain.Administrator;
import domain.Area;
import domain.Brotherhood;
import domain.Finder;
import domain.Member;
import domain.MessageBox;
import forms.AdministratorForm;
import forms.BrotherhoodForm;
import forms.MemberForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private MemberService			memberService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private UserAccountRepository	userAccountRepository;
	@Autowired
	private AreaService				areaService;


	// Constructors ----------------------------------------------------------------

	public RegisterController() {
		super();
	}

	// Create administrator --------------------------------------------------------

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView registerAdministrator() {
		ModelAndView result;
		AdministratorForm administrator;

		administrator = this.administratorService.createForm();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	// Save administrator ----------------------------------------------------------

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final AdministratorForm administrator, final BindingResult bindingResult) {
		ModelAndView result;
		Administrator administrator2;

		administrator2 = this.administratorService.reconstructForm(administrator, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(administrator);
		else
			try {
				if (administrator2.getId() == 0) {
					administrator2.getUserAccount().setUsername(administrator.getUsername());
					administrator2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(administrator.getPassword(), null));
					final UserAccount ua = administrator2.getUserAccount();
					final UserAccount uas = this.userAccountRepository.save(ua);
					administrator2.setUserAccount(uas);
					final Administrator savedAdministrator = this.administratorService.save(administrator2);
					final Collection<MessageBox> mbs = this.messageBoxService.createSystemBoxes();
					for (final MessageBox mb : mbs) {
						mb.setActor(savedAdministrator);
						this.messageBoxService.save(mb);
					}
				} else
					this.administratorService.save(administrator2);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable e) {
				result = this.createAndEditModelAndView(administrator, "register.administrator.error");
			}

		return result;
	}

	// Administrator ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final AdministratorForm administrator) {
		ModelAndView result;

		result = this.createAndEditModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final AdministratorForm administrator, final String message) {
		ModelAndView result;
		final String s = "administrator";
		result = new ModelAndView("register/administrator/create");
		result.addObject("administrator", administrator);
		result.addObject("requestURI", s);
		return result;
	}

	// Create brotherhood ----------------------------------------------------------

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView registerBrotherhood() {
		ModelAndView result;
		BrotherhoodForm brotherhood;

		brotherhood = this.brotherhoodService.createForm();
		result = this.createEditModelAndView(brotherhood);

		return result;
	}

	// Save brotherhood ------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final BrotherhoodForm brotherhood, final BindingResult bindingResult) {
		ModelAndView result;
		Brotherhood brotherhood2;

		brotherhood2 = this.brotherhoodService.reconstruct(brotherhood, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(brotherhood);
		else
			try {
				if (brotherhood.getPassword().equals(brotherhood.getConfirmPassword())) {
					if (brotherhood2.getId() == 0) {
						brotherhood2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(brotherhood.getPassword(), null));
						final UserAccount ua = brotherhood2.getUserAccount();
						final UserAccount saved = this.userAccountRepository.saveAndFlush(ua);
						brotherhood2.setUserAccount(saved);
						final Brotherhood bh = this.brotherhoodService.save(brotherhood2);
						final Area area = this.areaService.create();
						final Area area2 = this.areaService.save(area);
						bh.setArea(area2);
						for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
							mb.setActor(bh);
							this.messageBoxService.save(mb);
						}
					} else
						this.brotherhoodService.save(brotherhood2);
					result = new ModelAndView("redirect:/welcome/index.do");
				} else
					result = this.createAndEditModelAndView(brotherhood, "register.brotherhood.error");
			} catch (final Throwable e) {
				result = this.createAndEditModelAndView(brotherhood, "register.brotherhood.error");
			}

		return result;
	}

	// Brotherhood ancillary methods -----------------------------------------------

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

	// Create member ---------------------------------------------------------------

	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView registerMember() {
		ModelAndView result;
		MemberForm member;

		member = this.memberService.createForm();
		result = this.createEditModelAndView(member);

		return result;
	}

	// Save member -----------------------------------------------------------------

	@RequestMapping(value = "/member/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MemberForm member, final BindingResult bindingResult) {
		ModelAndView result;
		Member member2;

		member2 = this.memberService.reconstructForm(member, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(member);
		else
			try {
				if (member.getPassword().equals(member.getConfirmPassword())) {
					if (member2.getId() == 0) {
						final Finder finder = member2.getFinder();
						final Finder saved = this.finderService.save(finder);
						member2.setFinder(saved);
						member2.getUserAccount().setUsername(member.getUsername());
						member2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(member.getPassword(), null));
						final UserAccount ua = member2.getUserAccount();
						final UserAccount uas = this.userAccountRepository.save(ua);
						member2.setUserAccount(uas);
						final Member savedM = this.memberService.save(member2);
						final Collection<MessageBox> mbs = this.messageBoxService.createSystemBoxes();
						for (final MessageBox mb : mbs) {
							mb.setActor(savedM);
							this.messageBoxService.save(mb);
						}
					} else
						this.memberService.save(member2);
					result = new ModelAndView("redirect:/welcome/index.do");
				} else
					result = this.createAndEditModelAndView(member, "register.member.error");
			} catch (final Throwable e) {
				result = this.createAndEditModelAndView(member, "register.member.error");
			}

		return result;
	}

	// Member ancillary methods ----------------------------------------------------

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

}
