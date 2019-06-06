
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageBoxService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import domain.MessageBox;
import forms.MessageForm;

@Controller
@RequestMapping("/message/all")
public class MessageController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private ActorService		actorService;
	@Autowired
	private MessageBoxService	messageBoxService;
	@Autowired
	private MessageService		messageService;


	// Constructors ----------------------------------------------------------------

	public MessageController() {
		super();
	}

	// Methods ---------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		return this.createEditModelAndView(this.messageService.createForm(), "create");
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		return this.createEditModelAndView(this.messageService.createBroadcast(), "broadcast");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("message") final MessageForm mf, final BindingResult bindingResult) {
		ModelAndView result;
		Message m;
		try {
			if (mf.getBroadcast())
				mf.setRecipients(this.actorService.findAll());
			m = this.messageService.reconstruct(mf, bindingResult);
			for (final Actor a : m.getRecipients())
				if (!m.getIsSpam()) {
					final Collection<MessageBox> mbs = m.getMessageBoxes();
					final MessageBox inbox = this.messageBoxService.save(this.messageBoxService.findInbox(a.getId()));
					mbs.add(inbox);
					//					}
				} else {
					final Collection<MessageBox> mbs = m.getMessageBoxes();
					final MessageBox spambox = this.messageBoxService.save(this.messageBoxService.findSpamBox(a.getId()));
					mbs.add(spambox);

				}
			this.messageService.save(m);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException valExp) {
			if (mf.getBroadcast())
				result = this.createEditModelAndView(mf, "broadcast");
			else
				result = this.createEditModelAndView(mf, "create");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(mf, "message.error");
		}

		return result;
	}

	// PROTECTED SHOW -----------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		final Message m = this.messageService.findOne(id);
		ModelAndView result;
		final Actor principal = this.actorService.findPrincipal();
		if (m.getSender().equals(principal) || m.getRecipients().contains(principal)) {
			result = this.createModelAndViewWithSystemConfiguration("message/all/show");
			result.addObject(m);
		} else
			result = this.createModelAndViewWithSystemConfiguration("/welcome/index.do");

		return result;

	}

	@RequestMapping(value = "/trashBox", method = RequestMethod.GET)
	public ModelAndView trashBox(@RequestParam final int messageId) {
		final Actor a = this.actorService.findPrincipal();
		final Message msg = this.messageService.findOne(messageId);
		MessageBox box;
		final MessageBox trash = this.messageBoxService.findTrashBox(a.getId());
		Assert.isTrue(msg.getSender().equals(a) || msg.getRecipients().contains(a));
		if (msg.getSender().equals(a)) {
			box = this.messageBoxService.findOutbox(a.getId());
			msg.getMessageBoxes().remove(box);
		} else if (msg.getIsSpam()) {
			box = this.messageBoxService.findSpamBox(a.getId());
			msg.getMessageBoxes().remove(box);
		} else {
			box = this.messageBoxService.findInbox(a.getId());
			msg.getMessageBoxes().remove(box);
		}
		msg.getMessageBoxes().add(trash);
		this.messageService.save(msg);
		return new ModelAndView("redirect:/message-box/all/show.do?name=inBox");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		final Actor a = this.actorService.findPrincipal();
		final Message msg = this.messageService.findOne(messageId);
		final MessageBox trash = this.messageBoxService.findTrashBox(a.getId());
		//Assert.isTrue(msg.getSender().equals(a) || msg.getRecipients().contains(a));
		//Assert.isTrue(msg.getMessageBoxes().contains(trash));
		if ((msg.getSender().equals(a) || msg.getRecipients().contains(a)) && msg.getMessageBoxes().contains(trash)) {
			msg.getMessageBoxes().remove(trash);
			if (msg.getRecipients().contains(a))
				msg.getRecipients().remove(a);
			this.messageService.save(msg);
		}

		return new ModelAndView("redirect:/message-box/all/show.do?name=trashBox");

	}

	// PROTECTED METHODS -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MessageForm mf) {

		return this.createEditModelAndView(mf, null);
	}

	protected ModelAndView createEditModelAndView(final MessageForm mf, final String message) {
		final ModelAndView result = this.createModelAndViewWithSystemConfiguration("message/all/" + message);
		result.addObject("message", mf);
		result.addObject("messageb", message);
		result.addObject("recipients", this.actorService.findAll());

		return result;
	}

}
