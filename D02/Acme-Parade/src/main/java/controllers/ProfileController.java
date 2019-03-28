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
import services.InceptionRecordService;
import services.LegalRecordService;
import services.LinkRecordService;
import services.MemberService;
import services.MiscellaneousRecordService;
import services.PeriodRecordService;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import domain.AcmeFloat;
import domain.Administrator;
import domain.Brotherhood;
import domain.Enrolment;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.Member;
import domain.Message;
import domain.MiscellaneousRecord;
import domain.Parade;
import domain.PeriodRecord;
import domain.SocialProfile;
import forms.AdministratorForm;
import forms.BrotherhoodForm;
import forms.MemberForm;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private MemberService				memberService;

	@Autowired
	private BrotherhoodService			brotherhoodService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private LinkRecordService			linkRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private LegalRecordService			legalRecordService;

	@Autowired
	private InceptionRecordService		inceptionRecordService;

	@Autowired
	private PeriodRecordService			periodRecordService;


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
		result.addObject("reqURI", reqURI);

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
	@RequestMapping(value = "/brotherhood/export", method = RequestMethod.GET)
	public ModelAndView exportBrotherhood() {
		ModelAndView result;
		final Brotherhood member = this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId());
		result = new ModelAndView("redirect:/welcome/index.do");
		final String username = member.getUserAccount().getUsername();
		final String password = member.getUserAccount().getPassword();
		final Document as = new Document(PageSize.A4);
		try {
			final String locale = System.getProperty("user.home");
			PdfWriter.getInstance(as, new FileOutputStream(locale + "\\Desktop\\export.pdf"));
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
					as.add(new Paragraph("\n"));
				}
			else
				as.add(new Paragraph("[]"));

			as.add(new Paragraph("\n\nArea"));
			as.add(new Paragraph(member.getArea().getName()));
			as.add(new Paragraph("\n\npictures: "));
			for (final String s : member.getArea().getPictures())
				as.add(new Paragraph(s));

			final int idHistory = member.getHistory().getId();
			as.add(new Paragraph("Link records: "));
			final List<LinkRecord> lR = (List<LinkRecord>) this.linkRecordService.getLinkRecordsByHistory(idHistory);
			if (!lR.isEmpty())
				for (final LinkRecord r : lR) {
					as.add(new Paragraph("title: " + r.getTitle()));
					as.add(new Paragraph("description: " + r.getDescription()));
					as.add(new Paragraph("brotherhood: " + r.getBrotherhood().getTitle()));
					as.add(new Paragraph("\n"));
				}
			else
				as.add(new Paragraph("[]"));
			as.add(new Paragraph("Miscellaneous records: "));
			final List<MiscellaneousRecord> mR = (List<MiscellaneousRecord>) this.miscellaneousRecordService.getMiscellaneousRecordsByHistory(idHistory);
			if (!mR.isEmpty())
				for (final MiscellaneousRecord r : mR) {
					as.add(new Paragraph("title: " + r.getTitle()));
					as.add(new Paragraph("title: " + r.getDescription()));
					as.add(new Paragraph("\n"));
				}
			else
				as.add(new Paragraph("[]"));
			as.add(new Paragraph("Legal records: "));
			final List<LegalRecord> legalRecord = (List<LegalRecord>) this.legalRecordService.getLegalRecordsByHistory(idHistory);
			if (!legalRecord.isEmpty())
				for (final LegalRecord r : legalRecord) {
					as.add(new Paragraph("title: " + r.getTitle()));
					as.add(new Paragraph("description: " + r.getDescription()));
					as.add(new Paragraph("legal name: " + r.getLegalName()));
					final String vat = String.valueOf(r.getVAT());
					as.add(new Paragraph("vat: " + vat));
					as.add(new Paragraph("aplicable laws: " + r.getApplicableLaws()));
					as.add(new Paragraph("\n"));
				}
			else {
				as.add(new Paragraph("[]"));
				as.add(new Paragraph("\n"));
			}
			final InceptionRecord iR = this.inceptionRecordService.getInceptionRecordByHistory(idHistory);
			as.add(new Paragraph("Inception record: "));
			if (iR != null) {
				as.add(new Paragraph("title: " + iR.getTitle()));
				as.add(new Paragraph("description: " + iR.getDescription()));
				as.add(new Paragraph("photos: "));
				if (!iR.getPhotos().isEmpty())
					for (final String s : iR.getPhotos())
						as.add(new Paragraph(s));
				else
					as.add(new Paragraph("[]"));
			}
			as.add(new Paragraph("\n"));

			final List<PeriodRecord> pR = (List<PeriodRecord>) this.periodRecordService.getPeriodRecordsByHistory(idHistory);
			if (!pR.isEmpty())
				for (final PeriodRecord p : pR) {
					as.add(new Paragraph(p.getTitle()));
					as.add(new Paragraph(p.getDescription()));
					as.add(new Paragraph(String.valueOf(p.getStartYear())));
					as.add(new Paragraph(String.valueOf(p.getEndYear())));
					if (!p.getPhotos().isEmpty())
						for (final String s : p.getPhotos())
							as.add(new Paragraph(s));
					as.add(new Paragraph("\n"));
				}
			else
				as.add(new Paragraph("[]"));

			as.add(new Paragraph("\n\nParades"));
			final List<Parade> ls = (List<Parade>) member.getParades();
			if (!ls.isEmpty())
				for (final Parade p : ls) {
					as.add(new Paragraph("title: " + p.getTitle()));
					as.add(new Paragraph("description: " + p.getDescription()));
					as.add(new Paragraph("floats: "));
					if (p.getAcmeFloats().size() != 0)
						for (final AcmeFloat a : p.getAcmeFloats()) {
							as.add(new Paragraph("title: " + a.getTitle()));
							as.add(new Paragraph("description: " + a.getDescription()));
							as.add(new Paragraph("pictures: "));
							if (a.getPictures().size() != 0)
								for (final String s : a.getPictures())
									as.add(new Paragraph(s));
							else
								as.add(new Paragraph("[]"));
						}
					else
						as.add(new Paragraph("[]"));
					as.add(new Paragraph("\n"));
				}
			else
				as.add(new Paragraph("c[]"));

		} catch (FileNotFoundException | DocumentException e1) {
			e1.printStackTrace();
		}

		as.close();
		result.addObject("as", as);

		return result;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/member/export", method = RequestMethod.GET)
	public ModelAndView exportMember() {
		final ModelAndView result;
		final Member member = this.memberService.findByUserAccountId(LoginService.getPrincipal().getId());
		result = new ModelAndView("redirect:/welcome/index.do");
		final String username = member.getUserAccount().getUsername();
		final String password = member.getUserAccount().getPassword();
		final Document as = new Document(PageSize.A4);
		try {
			final String locale = System.getProperty("user.home");
			PdfWriter.getInstance(as, new FileOutputStream(locale + "\\Desktop\\export.pdf"));
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
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/admin/export", method = RequestMethod.GET)
	public ModelAndView exportAdmin() {
		final ModelAndView result;
		final Administrator member = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		result = new ModelAndView("redirect:/welcome/index.do");
		final String username = member.getUserAccount().getUsername();
		final String password = member.getUserAccount().getPassword();
		final Document as = new Document(PageSize.A4);
		try {
			final String locale = System.getProperty("user.home");
			PdfWriter.getInstance(as, new FileOutputStream(locale + "\\Desktop\\export.pdf"));
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

}
