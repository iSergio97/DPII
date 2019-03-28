
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import services.SystemConfigurationService;
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
	private BrotherhoodService			brotherhoodService;
	@Autowired
	private AdministratorService		administratorService;
	@Autowired
	private FinderService				finderService;
	@Autowired
	private MemberService				memberService;
	@Autowired
	private MessageBoxService			messageBoxService;
	@Autowired
	private UserAccountRepository		userAccountRepository;
	@Autowired
	private AreaService					areaService;
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


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
	public ModelAndView save(@ModelAttribute("administrator") final AdministratorForm administrator, final BindingResult bindingResult) {
		ModelAndView result;
		Administrator administrator2;

		final List<String> userNames = this.userAccountRepository.getUserNames();
		if (administrator.getId() == 0) {
			if (userNames.contains(administrator.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Administrator b = this.administratorService.findPrincipal();
			userNames.remove(b.getUserAccount().getUsername());
			if (userNames.contains(administrator.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		}
		if (administrator.getUsername().length() < 5 || administrator.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}
		if (!(administrator.getPassword().equals(administrator.getConfirmPassword()))) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (administrator.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (administrator.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

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
					result = new ModelAndView("redirect:/welcome/index.do");
				} else {
					final UserAccount ua = administrator2.getUserAccount();
					administrator2.getUserAccount().setUsername(administrator.getUsername());
					administrator2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(administrator.getPassword(), null));
					final UserAccount uas = this.userAccountRepository.save(ua);
					administrator2.setUserAccount(uas);
					this.administratorService.save(administrator2);
					result = new ModelAndView("redirect:/welcome/index.do");
				}
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
	public ModelAndView save(@ModelAttribute("brotherhood") final BrotherhoodForm brotherhood, final BindingResult bindingResult) {
		ModelAndView result;
		final Brotherhood brotherhood2;
		/*
		 * brotherhood2 = this.brotherhoodService.reconstruct(brotherhood, bindingResult);
		 * if (bindingResult.hasErrors())
		 * result = this.createEditModelAndView(brotherhood);
		 * else
		 * try {
		 * if (brotherhood.getPassword().equals(brotherhood.getConfirmPassword())) {
		 * if (brotherhood2.getId() == 0) {
		 * brotherhood2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(brotherhood.getPassword(), null));
		 * brotherhood2.getUserAccount().setUsername(brotherhood.getUsername());
		 * final UserAccount ua = brotherhood2.getUserAccount();
		 * final UserAccount saved = this.userAccountRepository.saveAndFlush(ua);
		 * brotherhood2.setUserAccount(saved);
		 * final Brotherhood bh = this.brotherhoodService.save(brotherhood2);
		 * final Area area = this.areaService.create();
		 * final Area area2 = this.areaService.save(area);
		 * bh.setArea(area2);
		 * for (final MessageBox mb : this.messageBoxService.createSystemBoxes()) {
		 * mb.setActor(bh);
		 * this.messageBoxService.save(mb);
		 * }
		 * } else {
		 * brotherhood2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(brotherhood.getPassword(), null));
		 * brotherhood2.getUserAccount().setUsername(brotherhood.getUsername());
		 * final UserAccount ua = brotherhood2.getUserAccount();
		 * final UserAccount saved = this.userAccountRepository.saveAndFlush(ua);
		 * brotherhood2.setUserAccount(saved);
		 * this.brotherhoodService.save(brotherhood2);
		 * }
		 * result = new ModelAndView("redirect:/welcome/index.do");
		 * } else
		 * result = this.createAndEditModelAndView(brotherhood, "register.brotherhood.error");
		 * } catch (final Throwable e) {
		 * result = this.createAndEditModelAndView(brotherhood, "register.brotherhood.error");
		 * }
		 */
		final List<String> userNames = this.userAccountRepository.getUserNames();
		if (brotherhood.getId() == 0) {
			if (userNames.contains(brotherhood.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Brotherhood b = this.brotherhoodService.findPrincipal();
			userNames.remove(b.getUserAccount().getUsername());
			if (userNames.contains(brotherhood.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		}
		if (brotherhood.getUsername().length() < 5 || brotherhood.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}
		if (!(brotherhood.getPassword().equals(brotherhood.getConfirmPassword()))) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (brotherhood.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (brotherhood.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}
		try {
			brotherhood2 = this.brotherhoodService.reconstructForm(brotherhood, bindingResult);
			if (!bindingResult.hasErrors()) {
				if (brotherhood2.getPhoneNumber().matches("[0-9]{4,}"))
					brotherhood2.setPhoneNumber(this.systemConfigurationService.getSystemConfiguration().getDefaultCountryCode() + " " + brotherhood2.getPhoneNumber());
				if (brotherhood2.getId() == 0) {
					brotherhood2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(brotherhood.getPassword(), null));
					final UserAccount uas = this.userAccountRepository.save(brotherhood2.getUserAccount());
					brotherhood2.setUserAccount(uas);
					final Area area = this.areaService.save(this.areaService.create());
					brotherhood2.setArea(area);
					//Area saved = this.areaService.save(area);
					final Brotherhood savedM = this.brotherhoodService.save(brotherhood2);
					final Collection<MessageBox> mbs = this.messageBoxService.createSystemBoxes();
					for (final MessageBox mb : mbs) {
						mb.setActor(savedM);
						this.messageBoxService.save(mb);
					}

				} else {
					final UserAccount ua = brotherhood2.getUserAccount();
					ua.setPassword(new Md5PasswordEncoder().encodePassword(brotherhood2.getUserAccount().getPassword(), null));
					final UserAccount saved = this.userAccountRepository.save(ua);
					brotherhood2.setUserAccount(saved);
					this.brotherhoodService.save(brotherhood2);
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} else
				result = this.createAndEditModelAndView(brotherhood, "register.brotherhood.error");
		} catch (final Throwable wops) {
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
	public ModelAndView save(@ModelAttribute("member") final MemberForm member, final BindingResult bindingResult) {
		ModelAndView result;
		final Member member2;
		final List<String> userNames = this.userAccountRepository.getUserNames();
		if (member.getId() == 0) {
			if (userNames.contains(member.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Member m = this.memberService.findPrincipal();
			userNames.remove(m.getUserAccount().getUsername());
			if (userNames.contains(member.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		}
		if (member.getUsername().length() < 5 || member.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}
		if (!(member.getPassword().equals(member.getConfirmPassword()))) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (member.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (member.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		try {
			member2 = this.memberService.reconstructForm(member, bindingResult);
			if (!bindingResult.hasErrors()) {
				if (member2.getPhoneNumber().matches("[0-9]{4,}"))
					member2.setPhoneNumber(this.systemConfigurationService.getSystemConfiguration().getDefaultCountryCode() + " " + member2.getPhoneNumber());
				if (member2.getId() == 0) {
					final Finder saved = this.finderService.save(member2.getFinder());
					member2.setFinder(saved);
					member2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(member.getPassword(), null));
					final UserAccount uas = this.userAccountRepository.save(member2.getUserAccount());
					member2.setUserAccount(uas);
					final Member savedM = this.memberService.save(member2);
					final Collection<MessageBox> mbs = this.messageBoxService.createSystemBoxes();
					for (final MessageBox mb : mbs) {
						mb.setActor(savedM);
						this.messageBoxService.save(mb);
					}
				} else {
					final UserAccount ua = member2.getUserAccount();
					ua.setPassword(new Md5PasswordEncoder().encodePassword(member2.getUserAccount().getPassword(), null));
					final UserAccount saved = this.userAccountRepository.save(ua);
					member2.setUserAccount(saved);
					this.memberService.save(member2);
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} else
				result = this.createAndEditModelAndView(member, "commit.error");
		} catch (final Throwable wops) {
			result = this.createAndEditModelAndView(member, "commit.error");
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
		result.addObject("message", message);

		return result;
	}

}
