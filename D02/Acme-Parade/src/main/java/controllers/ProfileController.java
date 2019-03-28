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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import services.BrotherhoodService;
import services.LinkRecordService;
import services.MemberService;
import services.MessageBoxService;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import domain.Administrator;
import domain.Brotherhood;
import domain.Enrolment;
import domain.LinkRecord;
import domain.Member;
import domain.Message;
import domain.MessageBox;
import domain.SocialProfile;
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

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private LinkRecordService		linkRecordService;


	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/member/show", method = RequestMethod.GET)
	public ModelAndView memberShow() {
		ModelAndView result;
		final Member member = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		//final MemberForm mf = this.memberService.deconstruct(member);
		final String reqURI = "member";

		result = new ModelAndView("profile/member/show");
		result.addObject("actor", member);
		result.addObject("reqURI", reqURI);
		return result;
	}

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.GET)
	public ModelAndView brotherhoodShow() {
		ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId());
		//final BrotherhoodForm bhForm = this.brotherhoodService.deconstruct(brotherhood);

		final String reqURI = "brotherhood";
		result = new ModelAndView("profile/brotherhood/show");
		result.addObject("actor", brotherhood);
		result.addObject("reqURI", reqURI);
		return result;
	}

	@RequestMapping(value = "/admin/show", method = RequestMethod.GET)
	public ModelAndView showAdmin() {
		ModelAndView result;
		final Administrator admin = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		//final AdministratorForm adminf = this.administratorService.deconstruct(admin);
		final String reqURI = "admin";

		result = new ModelAndView("profile/admin/show");
		result.addObject("actor", admin);
		result.addObject("admin", reqURI);

		return result;
	}
	@RequestMapping(value = "/member/edit", method = RequestMethod.GET)
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

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
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

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/member/export", method = RequestMethod.GET)
	public ModelAndView exportMember() {
		ModelAndView result;
		final Brotherhood member = this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId());
		result = new ModelAndView("redirect:/welcome/index.do");
		final String username = member.getUserAccount().getUsername();
		final String password = member.getUserAccount().getPassword();
		final Document as = new Document(PageSize.A4);
		try {
			final String locale = System.getProperty("user.home");
			PdfWriter.getInstance(as, new FileOutputStream(locale + "\\Desktop\\test.pdf"));
			as.open();
			final Paragraph gpdr = new Paragraph("GPDR Legislation\n\n");
			gpdr.setAlignment(Element.ALIGN_CENTER);
			as.add(gpdr);
			System.out.println("Entra al try");
			System.out.println("Crea el documento");
			final Paragraph userData = new Paragraph("Userdata");
			as.add(userData);
			System.out.println("Crea userData");
			final Paragraph name = new Paragraph("name: " + member.getName());
			as.add(name);
			final Paragraph md = new Paragraph("middle name: " + member.getMiddleName());
			as.add(md);
			as.add(new Paragraph("surname: " + member.getSurname()));
			as.add(new Paragraph("photo: " + member.getPhoto()));
			as.add(new Paragraph("email: " + member.getEmail()));
			as.add(new Paragraph("phone number: " + member.getPhoneNumber()));
			as.add(new Paragraph("address: " + member.getAddress()));
			as.add(new Paragraph("polarity score: " + member.getPolarityScore().toString()));
			as.add(new Paragraph("title: " + member.getTitle()));
			as.add(new Paragraph("establishment date: " + member.getEstablishmentDate().toGMTString()));
			as.add(new Paragraph("pictures: "));
			for (final String s : member.getPictures())
				as.add(new Paragraph(s));
			final Paragraph userAccount = new Paragraph("\n\nuserAccount");
			as.add(userAccount);
			System.out.println("Crea userAccount");
			as.add(new Paragraph("useraccount: " + username));
			as.add(new Paragraph("password: " + password));
			as.add(new Paragraph("authority: " + member.getUserAccount().getAuthorities().toArray()[0]));
			final Paragraph messages = new Paragraph("\n\nMessages Recieved");
			as.add(messages);
			System.out.println("Crea messages");
			if (member.getMessagesSent().size() != 0)
				for (final Message m : member.getMessagesSent()) {
					as.add(new Paragraph(m.getRecipients().toArray()[0].toString()));
					as.add(new Paragraph(m.getSubject()));
					as.add(new Paragraph(m.getBody()));
					as.add(new Paragraph(m.getTags().toString()));
					as.add(new Paragraph(m.getDate().toGMTString()));
					as.add(new Paragraph(m.getPriority().toString()));
				}
			else
				as.add(new Paragraph("[]"));

			as.add(new Paragraph("\n\nMessages received"));
			if (member.getMessagesReceived().size() != 0)
				for (final Message m : member.getMessagesReceived()) {
					as.add(new Paragraph(m.getSender().getName()));
					as.add(new Paragraph(m.getBody()));
					as.add(new Paragraph(m.getSubject()));
					as.add(new Paragraph(m.getTags().toString()));
					as.add(new Paragraph(m.getPriority().toString()));
					as.add(new Paragraph(m.getDate().toGMTString()));
				}
			else
				as.add(new Paragraph("[]"));
			final Paragraph profiles = new Paragraph("\n\nProfiles");
			as.add(profiles);
			System.out.println("Crea profiles");
			if (member.getSocialProfiles().size() != 0)
				for (final SocialProfile p : member.getSocialProfiles()) {
					as.add(new Paragraph("nick: " + p.getNick()));
					as.add(new Paragraph("link: " + p.getProfileLink()));
					as.add(new Paragraph("social network: " + p.getSocialNetworkName()));
				}
			else
				as.add(new Paragraph("[]"));
			final Paragraph enrols = new Paragraph("\n\nEnrols");
			as.add(enrols);
			System.out.println("Crea enrols");
			if (member.getEnrolments().size() != 0)
				for (final Enrolment e : member.getEnrolments()) {
					as.add(new Paragraph("brotherhood: " + e.getMember().getName()));
					as.add(new Paragraph("join moment: " + e.getMoment().toGMTString()));
					if (e.getExitMoment() != null)
						as.add(new Paragraph("exit moment: " + e.getExitMoment().toGMTString()));
					else
						as.add(new Paragraph("exit moment: null"));
					as.add(new Paragraph("position: " + e.getPosition().getStrings()));
					as.add(new Paragraph("\n)"));
				}
			else
				as.add(new Paragraph("[]"));

			as.add(new Paragraph("\n\narea"));
			as.add(new Paragraph(member.getArea().getName()));
			as.add(new Paragraph("\n\npictures: "));
			for (final String s : member.getArea().getPictures())
				as.add(new Paragraph(s));

			as.add(new Paragraph("Link records: "));
			final List<LinkRecord> lR = (List<LinkRecord>) this.linkRecordService.getLinkRecordsByHistory(member.getHistory().getId());
			for (final LinkRecord r : lR) {
				as.add(new Paragraph(r.getTitle()));
				as.add(new Paragraph(r.getDescription()));
				as.add(new Paragraph(r.getBrotherhood().getTitle()));
			}

			//TODO: Seguir acabando la exportación de records
		} catch (FileNotFoundException | DocumentException e1) {
			e1.printStackTrace();
		}

		as.close();
		result.addObject("as", as);

		return result;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/brotherhood/export", method = RequestMethod.GET)
	public ModelAndView exportBrotherhood() {
		final ModelAndView result;
		final Member member = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		result = new ModelAndView("redirect:/welcome/index.do");
		final String username = member.getUserAccount().getUsername();
		final String password = member.getUserAccount().getPassword();
		final Document as = new Document(PageSize.A4);
		try {
			final String locale = System.getProperty("user.home");
			PdfWriter.getInstance(as, new FileOutputStream(locale + "\\Desktop\\test.pdf"));
			as.open();
			final Paragraph gpdr = new Paragraph("GPDR Legislation\n\n");
			gpdr.setAlignment(Element.ALIGN_CENTER);
			as.add(gpdr);
			System.out.println("Entra al try");
			System.out.println("Crea el documento");
			final Paragraph userData = new Paragraph("Userdata");
			as.add(userData);
			System.out.println("Crea userData");
			final Paragraph name = new Paragraph("name: " + member.getName());
			as.add(name);
			final Paragraph md = new Paragraph("middle name: " + member.getMiddleName());
			as.add(md);
			as.add(new Paragraph("surname: " + member.getSurname()));
			as.add(new Paragraph("photo: " + member.getPhoto()));
			as.add(new Paragraph("email: " + member.getEmail()));
			as.add(new Paragraph("phone number: " + member.getPhoneNumber()));
			as.add(new Paragraph("address: " + member.getAddress()));
			as.add(new Paragraph("polarity score: " + member.getPolarityScore().toString()));
			final Paragraph userAccount = new Paragraph("\n\nuserAccount");
			as.add(userAccount);
			System.out.println("Crea userAccount");
			as.add(new Paragraph("useraccount: " + username));
			as.add(new Paragraph("password: " + password));
			as.add(new Paragraph("authority: " + member.getUserAccount().getAuthorities().toArray()[0]));
			final Paragraph messages = new Paragraph("\n\nMessages Recieved");
			as.add(messages);
			System.out.println("Crea messages");
			if (member.getMessagesSent().size() != 0)
				for (final Message m : member.getMessagesSent()) {
					as.add(new Paragraph(m.getRecipients().toArray()[0].toString()));
					as.add(new Paragraph(m.getSubject()));
					as.add(new Paragraph(m.getBody()));
					as.add(new Paragraph(m.getTags().toString()));
					as.add(new Paragraph(m.getDate().toGMTString()));
					as.add(new Paragraph(m.getPriority().toString()));
				}
			else
				as.add(new Paragraph("[]"));

			as.add(new Paragraph("\n\nMessages received"));
			if (member.getMessagesReceived().size() != 0)
				for (final Message m : member.getMessagesReceived()) {
					as.add(new Paragraph(m.getSender().getName()));
					as.add(new Paragraph(m.getBody()));
					as.add(new Paragraph(m.getSubject()));
					as.add(new Paragraph(m.getTags().toString()));
					as.add(new Paragraph(m.getPriority().toString()));
					as.add(new Paragraph(m.getDate().toGMTString()));
				}
			else
				as.add(new Paragraph("[]"));
			final Paragraph profiles = new Paragraph("\n\nProfiles");
			as.add(profiles);
			System.out.println("Crea profiles");
			if (member.getSocialProfiles().size() != 0)
				for (final SocialProfile p : member.getSocialProfiles()) {
					as.add(new Paragraph("nick: " + p.getNick()));
					as.add(new Paragraph("link: " + p.getProfileLink()));
					as.add(new Paragraph("social network: " + p.getSocialNetworkName()));
				}
			else
				as.add(new Paragraph("[]"));
		} catch (FileNotFoundException | DocumentException e1) {
			e1.printStackTrace();
		}

		return result;
	}
	@RequestMapping(value = "/admin/export", method = RequestMethod.GET)
	public ModelAndView exportAdmin() {
		ModelAndView result;
		final Administrator admin = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final String reqURI = "admin";
		final List<MessageBox> ls = this.messageBoxService.messageFromActor(admin);
		result = new ModelAndView("profile/admin/export");
		result.addObject("actor", admin);
		result.addObject("username", admin.getUserAccount().getUsername());
		result.addObject("password", admin.getUserAccount().getPassword());
		result.addObject("messageBox", ls);
		result.addObject("admin", reqURI);

		return result;
	}

}
