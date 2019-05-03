
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
@RequestMapping("/message/actor")
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
		return this.createEditModelAndView(this.messageService.createForm());
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("message") final MessageForm mf, final BindingResult bindingResult) {
		ModelAndView result;
		Message m;
		try {
			m = this.messageService.reconstruct(mf, bindingResult);
			for (final Actor a : m.getRecipients())
				if (!m.getIsSpam()) {
					final Collection<MessageBox> mbs = m.getMessageBoxes();
					final MessageBox inbox = this.messageBoxService.save(this.messageBoxService.findInbox(a.getId()));
					mbs.add(inbox);
				} else {
					final Collection<MessageBox> mbs = m.getMessageBoxes();
					final MessageBox spambox = this.messageBoxService.save(this.messageBoxService.findSpamBox(a.getId()));
					mbs.add(spambox);

				}
			//this.messageBoxService.save(m.getMessageBoxes());
			this.messageService.save(m);
			result = new ModelAndView("redirect:../welcome/index.do");
		} catch (final ValidationException valExp) {
			result = this.createEditModelAndView(mf);
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
		final ModelAndView result = new ModelAndView("message/actor/create");
		result.addObject("message", mf);
		result.addObject("recipients", this.actorService.findAll());

		return result;
	}
}
