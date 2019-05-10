
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Message;
import domain.MessageBox;
import forms.MessageForm;
import services.ActorService;
import services.MessageBoxService;
import services.MessageService;

@Controller
@RequestMapping("/message/all")
public class MessageController {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private MessageService		messageService;


	public MessageController() {
		super();
	}

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
		Collection<Actor> ls = this.actorService.findAll();
		try {
			if (mf.getBroadcast()) {
				mf.setRecipients(this.actorService.findAll());
			}
			m = this.messageService.reconstruct(mf, bindingResult);
			for (final Actor a : m.getRecipients())
				if (!m.getIsSpam()) {
					//					if (!mf.getBroadcast()) {
					//						final Collection<MessageBox> mbs = m.getMessageBoxes();
					//						final MessageBox inbox = this.messageBoxService.save(this.messageBoxService.findInbox(a.getId()));
					//						mbs.add(inbox);
					//					} else {
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
			result = new ModelAndView("redirect:../welcome/index.do");
		} catch (final ValidationException valExp) {
			if (mf.getBroadcast()) {
				result = this.createEditModelAndView(mf, "broadcast");
			} else {
				result = this.createEditModelAndView(mf, "create");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(mf, "message.error");
		}

		return result;
	}

	//PROTECTED METHODS -------------------------------------------------------
	protected ModelAndView createEditModelAndView(final MessageForm mf) {

		return this.createEditModelAndView(mf, null);
	}

	protected ModelAndView createEditModelAndView(final MessageForm mf, final String message) {
		final ModelAndView result = new ModelAndView("message/all/" + message);
		result.addObject("message", mf);
		result.addObject("messageb", message);
		result.addObject("recipients", this.actorService.findAll());

		return result;
	}
}
