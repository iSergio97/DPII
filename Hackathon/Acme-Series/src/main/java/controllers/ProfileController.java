
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Comment;
import domain.Critic;
import domain.Finder;
import domain.Message;
import domain.MessageBox;
import domain.Publisher;
import domain.Serie;
import domain.SocialProfile;
import domain.User;
import forms.RegisterAdministratorForm;
import forms.RegisterCriticForm;
import forms.RegisterPublisherForm;
import forms.RegisterUserForm;
import security.UserAccount;
import security.UserAccountRepository;
import services.ActorService;
import services.AdministratorService;
import services.ApplicationService;
import services.CommentService;
import services.CriticService;
import services.CritiqueService;
import services.FinderService;
import services.MessageBoxService;
import services.MessageService;
import services.PublisherService;
import services.SerieService;
import services.SocialProfileService;
import services.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	//Supporting classes
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private PublisherService		publisherService;
	@Autowired
	private CriticService			criticService;
	@Autowired
	private UserService				userService;
	@Autowired
	private UserAccountRepository	userAccountRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private CommentService			commentService;
	@Autowired
	private CritiqueService			critiqueService;
	@Autowired
	private SerieService			serieService;
	@Autowired
	private FinderService			finderService;


	public ProfileController() {
		super();
	}

	//Administrator web pages
	@RequestMapping(value = "/administrator/show", method = RequestMethod.GET)
	public ModelAndView showAdmin() {
		final ModelAndView result;
		final Administrator admin = this.administratorService.findPrincipal();
		result = this.createEditModelAndView(admin, "show");

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView editAdmin() {
		ModelAndView result = new ModelAndView("/welcome/index.do");
		final Administrator admin = this.administratorService.findPrincipal();
		if (admin != null) {
			final RegisterAdministratorForm raf = this.administratorService.deconstruct(admin);
			result = this.createEditModelAndView(raf, "edit");
		}
		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST)
	public ModelAndView saveAdmin(@ModelAttribute("administrator") final RegisterAdministratorForm raf, final BindingResult bindingResult) {
		ModelAndView result;
		final Administrator admin;
		try {
			admin = this.administratorService.reconstructForm(raf, bindingResult);
			final UserAccount ua = admin.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(raf.getPassword(), null));
			final UserAccount savedUA = this.userAccountRepository.save(ua);
			admin.setUserAccount(savedUA);
			this.administratorService.save(admin);
			result = this.showAdmin();
		} catch (final ValidationException valExp) {
			result = this.createEditModelAndView(raf, "edit");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(raf, "edit");
		}
		return result;
	}

	@RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
	public ModelAndView deleteAdmin() {
		final Administrator admin = this.administratorService.findPrincipal();
		final Collection<Application> applies = this.applicationService.findAllAppliesByAdmin(admin);
		this.applicationService.delete(applies);
		final Collection<Message> ms = this.messageService.findAllMessages(admin.getId());
		final Collection<MessageBox> mb = this.messageBoxService.findBoxes(admin.getId());
		for (final Message m : ms)
			m.getMessageBoxes().removeAll(mb);

		this.messageBoxService.delete(mb);

		final Collection<SocialProfile> profiles = this.socialProfileService.findByActor(admin);
		this.socialProfileService.delete(profiles);
		final UserAccount ua = admin.getUserAccount();
		this.administratorService.delete(admin);
		this.userAccountRepository.delete(ua);

		return new ModelAndView("j_spring_security_check");
	}
	//Publisher web pages
	@RequestMapping(value = "/publisher/show", method = RequestMethod.GET)
	public ModelAndView showPublisher() {
		final ModelAndView result;
		final Publisher publisher = this.publisherService.findPrincipal();
		result = this.createEditModelAndView(publisher, "show");

		return result;
	}

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.GET)
	public ModelAndView editPublisher() {
		ModelAndView result = new ModelAndView("/welcome/index.do");
		final Publisher publisher = this.publisherService.findPrincipal();
		if (publisher != null) {
			final RegisterPublisherForm rpf = this.publisherService.deconstruct(publisher);
			result = this.createEditModelAndView(rpf, "edit");
		}
		return result;
	}

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.POST)
	public ModelAndView savePublisher(@ModelAttribute("publisher") final RegisterPublisherForm rpf, final BindingResult bindingResult) {
		ModelAndView result = new ModelAndView("/welcome/index.do");
		final Publisher publisher;
		try {
			publisher = this.publisherService.reconstructForm(rpf, bindingResult);
			final UserAccount ua = publisher.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(rpf.getPassword(), null));
			final UserAccount savedUA = this.userAccountRepository.save(ua);
			publisher.setUserAccount(savedUA);
			this.publisherService.save(publisher);
			result = this.showPublisher();
		} catch (final ValidationException valExp) {
			result = this.createEditModelAndView(rpf, "edit");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rpf, "edit");
		}
		return result;
	}
	//Critic web pages
	@RequestMapping(value = "/critic/show", method = RequestMethod.GET)
	public ModelAndView showCritic() {
		final ModelAndView result;
		final Critic critic = this.criticService.findPrincipal();
		result = this.createEditModelAndView(critic, "show");

		return result;
	}

	@RequestMapping(value = "critic/edit", method = RequestMethod.GET)
	public ModelAndView editCritic() {
		final ModelAndView result;
		final Critic critic = this.criticService.findPrincipal();
		final RegisterCriticForm rcf = this.criticService.deconstruct(critic);
		result = this.createEditModelAndView(rcf, "edit");
		return result;
	}

	@RequestMapping(value = "/critic/edit", method = RequestMethod.POST)
	public ModelAndView saveCritic(@ModelAttribute("critic") final RegisterCriticForm rcf, final BindingResult bindingResult) {
		ModelAndView result;
		final Critic critic;
		try {
			critic = this.criticService.reconstructForm(rcf, bindingResult);
			final UserAccount ua = critic.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(rcf.getPassword(), null));
			final UserAccount savedUA = this.userAccountRepository.save(ua);
			critic.setUserAccount(savedUA);
			this.criticService.save(critic);
			result = this.showCritic();
		} catch (final ValidationException valExp) {
			result = this.createEditModelAndView(rcf, "edit");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rcf, "edit");
		}
		return result;
	}

	//User web pages
	@RequestMapping(value = "/user/show", method = RequestMethod.GET)
	public ModelAndView showUser() {
		final ModelAndView result;
		final User user = this.userService.findPrincipal();
		result = this.createEditModelAndView(user, "show");

		return result;
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public ModelAndView editUser() {
		final ModelAndView result;
		final User user = this.userService.findPrincipal();
		final RegisterUserForm ruf = this.userService.deconstruct(user);
		result = this.createEditModelAndView(ruf, "edit");
		return result;
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute("user") final RegisterUserForm ruf, final BindingResult bindingResult) {
		ModelAndView result;
		final User user;
		try {
			user = this.userService.reconstructForm(ruf, bindingResult);
			final UserAccount ua = user.getUserAccount();
			ua.setPassword(new Md5PasswordEncoder().encodePassword(ruf.getPassword(), null));
			final UserAccount savedUA = this.userAccountRepository.save(ua);
			user.setUserAccount(savedUA);
			this.userService.save(user);
			result = this.showUser();
		} catch (final ValidationException valExp) {
			result = this.createEditModelAndView(ruf, "edit");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ruf, "edit");
		}
		return result;
	}

	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser() {
		final User user = this.userService.findPrincipal();
		final Collection<Message> ms = this.messageService.findAllMessages(user.getId());
		final Collection<MessageBox> mb = this.messageBoxService.findBoxes(user.getId());
		for (final Message m : ms)
			m.getMessageBoxes().removeAll(mb);

		this.messageBoxService.delete(mb);

		final Collection<Comment> comments = this.commentService.findAllByUserAccountId(user.getUserAccount().getId());
		this.commentService.delete(comments);
		final Collection<Serie> series = this.serieService.findAll();
		for (final Serie s : series) {
			s.getFavouritedUsers().remove(user);
			s.getWatchedUsers().remove(user);
			s.getPendingUsers().remove(user);
			s.getWatchingUsers().remove(user);
			this.serieService.save(s);
		}
		final Finder finder = this.finderService.findByPrincipal();
		finder.setSeries(new ArrayList<Serie>());
		this.finderService.save(finder);
		this.finderService.delete(finder);
		final Collection<SocialProfile> profiles = this.socialProfileService.findByActor(user);
		this.socialProfileService.delete(profiles);
		final UserAccount ua = user.getUserAccount();
		this.userService.delete(user);
		this.userAccountRepository.delete(ua);

		return new ModelAndView("j_spring_security_check");
	}

	@RequestMapping(value = "/actor/export", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView exportActor(final HttpServletResponse response) {
		final Actor actor = this.actorService.findPrincipal();
		ServletOutputStream outStream;
		ModelAndView result;
		try {
			final StringBuilder sb = new StringBuilder();
			sb.append("name");
			sb.append(";");
			sb.append("surnames");
			sb.append(";");
			sb.append("email");
			sb.append(";");
			sb.append("phoneNumber");
			sb.append(";");
			sb.append("photo");
			sb.append(";");
			sb.append("address");
			sb.append(";");
			sb.append("username");
			sb.append(";");
			sb.append("hashed pass");
			sb.append(";");
			sb.append("authority");
			sb.append(";");
			sb.append("\n");
			sb.append(actor.getName());
			sb.append(";");
			sb.append(actor.getSurnames());
			sb.append(";");
			sb.append(actor.getEmail());
			sb.append(";");
			sb.append(actor.getPhoneNumber());
			sb.append(";");
			sb.append(actor.getPhoto());
			sb.append(";");
			sb.append(actor.getAddress());
			sb.append(";");
			sb.append(actor.getUserAccount().getUsername());
			sb.append(";");
			sb.append(actor.getUserAccount().getPassword());
			sb.append(";");
			sb.append(actor.getUserAccount().getAuthorities().toArray()[0]);
			sb.append(";");
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment;filename=data.csv");
			outStream = response.getOutputStream();
			outStream.println(sb.toString());
			outStream.flush();
			outStream.close();
		} catch (final IOException e) {
			e.printStackTrace();
			return new ModelAndView("/welcome/index.do");
		}

		if (actor.getUserAccount().getAuthorities().toArray()[0].equals("USER"))
			result = this.showUser();
		else if (actor.getUserAccount().getAuthorities().toArray()[0].equals("ADMINISTRATOR"))
			result = this.showAdmin();
		else if (actor.getUserAccount().getAuthorities().toArray()[0].equals("PUBLISHER"))
			result = this.showPublisher();
		else if (actor.getUserAccount().getAuthorities().toArray()[0].equals("CRITIC"))
			result = this.showCritic();
		else
			result = new ModelAndView("/welcome/index.do");

		return result;
	}

	// Model and view methods ------------------------------------------------------

	protected <T> ModelAndView createEditModelAndView(final T t) {
		final ModelAndView result;

		result = this.createEditModelAndView(t, null);

		return result;
	}

	//
	protected <T> ModelAndView createEditModelAndView(final T t, final String messageCode) {
		ModelAndView result = null;

		if (t instanceof RegisterAdministratorForm || t instanceof Administrator) {
			result = new ModelAndView("profile/administrator/" + messageCode);
			result.addObject("administrator", t);
		} else if (t instanceof RegisterPublisherForm || t instanceof Publisher) {
			result = new ModelAndView("profile/publisher/" + messageCode);
			result.addObject("publisher", t);
		} else if (t instanceof RegisterCriticForm || t instanceof Critic) {
			result = new ModelAndView("profile/critic/" + messageCode);
			result.addObject("critic", t);
		} else if (t instanceof RegisterUserForm || t instanceof User) {
			result = new ModelAndView("profile/user/" + messageCode);
			result.addObject("user", t);
		}

		return result;
	}

}
