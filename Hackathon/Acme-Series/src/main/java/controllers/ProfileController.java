
package controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import domain.Administrator;
import domain.Application;
import domain.Critic;
import domain.Message;
import domain.MessageBox;
import domain.Publisher;
import domain.SocialProfile;
import domain.User;
import forms.RegisterAdministratorForm;
import forms.RegisterCriticForm;
import forms.RegisterPublisherForm;
import forms.RegisterUserForm;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import services.AdministratorService;
import services.ApplicationService;
import services.CriticService;
import services.MessageBoxService;
import services.MessageService;
import services.PublisherService;
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
	private MessageService			messageService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ApplicationService		applicationService;


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
		final ModelAndView result;
		final Administrator admin = this.administratorService.findPrincipal();
		final RegisterAdministratorForm raf = this.administratorService.deconstruct(admin);
		result = this.createEditModelAndView(raf, "edit");
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

	@RequestMapping(value = "/administrator/export", method = RequestMethod.GET)
	public ModelAndView exportAdmin() {
		final Administrator admin = this.administratorService.findPrincipal();
		final Administrator actual = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Document document = new Document(PageSize.A4);
		if (actual != admin)
			return new ModelAndView("/welcome/index.do");

		try {

			final String locale = System.getProperty("user.home");
			PdfWriter.getInstance(document, new FileOutputStream(locale + "\\Escritorio\\export.pdf"));
			document.open();
			final Paragraph gpdr = new Paragraph("GPDR Legislation\n\n");
			gpdr.setAlignment(Element.ALIGN_CENTER);
			document.add(gpdr);
			final Paragraph userData = new Paragraph("Userdata");
			document.add(userData);
			final Paragraph name = new Paragraph("name: " + admin.getName());
			document.add(name);
			document.add(new Paragraph("photo: " + admin.getPhoto()));
			document.add(new Paragraph("email: " + admin.getEmail()));
			document.add(new Paragraph("phone number: " + admin.getPhoneNumber()));
			document.add(new Paragraph("address: " + admin.getAddress()));
			final Paragraph userAccount = new Paragraph("\n\nuserAccount");
			document.add(userAccount);
			document.add(new Paragraph("useraccount: " + admin.getUserAccount().getUsername()));
			document.add(new Paragraph("password: " + admin.getUserAccount().getPassword()));
			document.add(new Paragraph("authority: " + admin.getUserAccount().getAuthorities().toArray()[0]));
			final Paragraph messages = new Paragraph("\n\nMessages Recieved");
			document.add(messages);
			final Collection<Message> messagesSent = this.messageService.getSent(admin.getId());
			final Collection<Message> messagesReceived = this.messageService.getRecieved(admin.getId());
			if (messagesSent.size() == 0)
				document.add(new Paragraph("[]"));

			for (final Message m : messagesSent) {
				document.add(new Paragraph(m.getRecipients().toArray()[0].toString()));
				document.add(new Paragraph(m.getSubject()));
				document.add(new Paragraph(m.getBody()));
				document.add(new Paragraph(m.getTags().toString()));
				document.add(new Paragraph(m.getMoment().toGMTString()));
				document.add(new Paragraph(m.getPriority().toString()));
			}

			document.add(new Paragraph("\n\nMessages received"));
			if (messagesReceived.size() == 0)
				document.add(new Paragraph("[]"));
			for (final Message m : messagesReceived) {
				document.add(new Paragraph(m.getSender().getName()));
				document.add(new Paragraph(m.getBody()));
				document.add(new Paragraph(m.getSubject()));
				document.add(new Paragraph(m.getTags().toString()));
				document.add(new Paragraph(m.getPriority().toString()));
				document.add(new Paragraph(m.getMoment().toGMTString()));
			}
			final Paragraph profiles = new Paragraph("\n\nProfiles");
			document.add(profiles);
			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(admin);
			if (socialProfiles.size() == 0)
				document.add(new Paragraph("[]"));
			for (final SocialProfile p : socialProfiles) {
				document.add(new Paragraph("Nick: " + p.getNick()));
				document.add(new Paragraph("Link: " + p.getProfileLink()));
				document.add(new Paragraph("Social Network: " + p.getSocialNetworkName()));
			}

			final List<MessageBox> mbs = (List<MessageBox>) this.messageBoxService.findMessageBoxes(admin.getUserAccount().getId());
			final List<MessageBox> systemBoxes = (List<MessageBox>) this.messageBoxService.findSystemBoxes(admin.getUserAccount().getId());
			final Paragraph cajas = new Paragraph("\n\nMessage boxes");
			document.add(cajas);
			for (final MessageBox mb : systemBoxes)
				document.add(new Paragraph("Name: " + mb.getName()));

			document.add(new Paragraph("\n"));
			for (final MessageBox mb : mbs)
				document.add(new Paragraph("Name: " + mb.getName()));

			final Paragraph appliedSeries = new Paragraph("\n\nApplied series");
			document.add(appliedSeries);
			final Collection<Application> app = this.applicationService.findAllAppliesByAdminId(admin);
			if (app.size() == 0)
				document.add(new Paragraph("[]"));
			for (final Application ap : app) {
				document.add(new Paragraph("Serie: " + ap.getSerie()));
				document.add(new Paragraph("Status: " + ap.getStatus()));
			}
		} catch (FileNotFoundException | DocumentException e1) {
			e1.printStackTrace();
		}
		document.close();
		return this.showAdmin();
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
		final ModelAndView result;
		final Publisher publisher = this.publisherService.findPrincipal();
		final RegisterPublisherForm rpf = this.publisherService.deconstruct(publisher);
		result = this.createEditModelAndView(rpf, "edit");
		return result;
	}

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.POST)
	public ModelAndView savePublisher(@ModelAttribute("publisher") final RegisterPublisherForm rpf, final BindingResult bindingResult) {
		ModelAndView result;
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

	// Model and view methods ------------------------------------------------------

	protected <T> ModelAndView createEditModelAndView(final T t) {
		final ModelAndView result;

		result = this.createEditModelAndView(t, null);

		return result;
	}

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
