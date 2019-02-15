
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.MessageBoxService;
import services.MessageService;
import services.SystemConfigurationService;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService				messageService;

	@Autowired
	private MessageBoxService			messageBoxService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	public MessageController() {
		super();
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
	public ModelAndView sendMessageGet() {
		ModelAndView result;
		List<Actor> lsActors;
		lsActors = this.actorService.findAll();
		try {
			final Actor a = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
			lsActors.remove(a);
			Message mesage;
			mesage = this.messageService.create();

			result = new ModelAndView("message/sendMessage");
			final MessageBox outBox = this.messageBoxService.findByPrincipalAndName(a.getId(), "OutBox");

			final List<MessageBox> ls = new ArrayList<>();
			ls.add(outBox);
			mesage.setMessageBoxes(ls);
			outBox.getMessages().add(mesage);
			mesage.setMessageBoxes(ls);
			result.addObject("domainMessage", mesage);
			result.addObject("actors", lsActors);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../j_spring_security_logout");
		}

		return result;
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public ModelAndView sendMessagePost(@Valid Message mesage, final BindingResult bindingResult) {

		ModelAndView result;
		if (!bindingResult.hasErrors()) {
			//TODO FALTA AÑADIR QUE EL MENSAJE SE GUARDE EN LA CAJA DE OUTBOX DEL ACTOR QUE LO ENVÍA
			Actor sender;
			sender = mesage.getSender();

			Collection<Actor> recipients;
			recipients = mesage.getRecipients();

			this.messageBoxService.save(sender.getMessageBoxes());
			mesage = this.messageService.save(mesage);
			sender.getMessagesSent().add(mesage);
			//Añadido ahora
			final MessageBox outBox = this.messageBoxService.findByPrincipalAndName(sender.getId(), "OutBox");
			final List<MessageBox> ls = new ArrayList<>();
			ls.add(outBox);
			mesage.setMessageBoxes(ls);
			outBox.getMessages().add(mesage);
			sender.getMessageBoxes().add(outBox);
			this.messageService.save(mesage);
			this.messageBoxService.save(outBox);

			for (final Actor a : recipients) {
				final MessageBox inBox = this.messageBoxService.findByPrincipalAndName(a.getId(), "InBox");
				a.getMessagesReceived().add(mesage);
				final List<MessageBox> ls1 = new ArrayList<>();
				final MessageBox spamBox = this.messageBoxService.findByPrincipalAndName(a.getId(), "SpamBox");
				if (this.spam(mesage)) {
					ls1.add(spamBox);
					ls1.add(outBox);
					spamBox.getMessages().add(mesage);
					mesage.setMessageBoxes(ls1);
				} else {
					ls1.add(inBox);
					ls1.add(outBox);
					inBox.getMessages().add(mesage);
					mesage.setMessageBoxes(ls1);
				}
				this.messageService.save(mesage);
				this.messageBoxService.save(inBox);
			}
			sender.getMessagesSent().add(mesage);
			outBox.getMessages().add(mesage);
			this.messageBoxService.save(outBox);
			result = new ModelAndView("redirect:../welcome/index.do");

		} else {
			for (int i = 0; i < bindingResult.getErrorCount(); i++)
				System.out.println(bindingResult.getAllErrors().get(i));
			result = new ModelAndView("message/sendMessage");
			List<Actor> lsActors;
			lsActors = this.actorService.findAll();
			result.addObject("domainMessage", mesage);
			result.addObject("actors", lsActors);
			result.addObject("bindingResult", bindingResult);
			for (int i = 0; i < bindingResult.getAllErrors().size(); i++)
				System.out.println("Error " + i + " " + bindingResult.getAllErrors().get(i));
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int id) {
		ModelAndView result;
		Message message;

		result = new ModelAndView("message/show");
		message = this.messageService.findById(id);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/displaybox", method = RequestMethod.GET)
	public ModelAndView displayBox(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		MessageBox messageBox;

		messageBox = this.messageBoxService.findById(id);
		Assert.isTrue(LoginService.getPrincipal().equals(messageBox.getActor().getUserAccount()));
		result = new ModelAndView("message/displaybox");
		result.addObject("messageBox", messageBox);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam(value = "messageId") final int messageId, @RequestParam(value = "fromMessageBoxId") final int fromMessageBoxId, @RequestParam(value = "toMessageBoxId") final int toMessageBoxId) {
		Message message;
		MessageBox fromMessageBox;
		MessageBox toMessageBox;

		message = this.messageService.findById(messageId);
		fromMessageBox = this.messageBoxService.findById(fromMessageBoxId);
		toMessageBox = this.messageBoxService.findById(toMessageBoxId);

		Assert.isTrue(LoginService.getPrincipal().equals(fromMessageBox.getActor().getUserAccount()));
		Assert.isTrue(LoginService.getPrincipal().equals(toMessageBox.getActor().getUserAccount()));

		final Collection<MessageBox> messageBoxes = message.getMessageBoxes();
		messageBoxes.remove(fromMessageBox);
		messageBoxes.add(toMessageBox);
		message.setMessageBoxes(messageBoxes);
		message = this.messageService.save(message);

		final Collection<Message> fromMessages = fromMessageBox.getMessages();
		fromMessages.remove(message);
		fromMessageBox = this.messageBoxService.save(fromMessageBox);

		final Collection<Message> toMessages = toMessageBox.getMessages();
		toMessages.add(message);
		toMessageBox = this.messageBoxService.save(toMessageBox);

		return this.displayBox(toMessageBox.getId());
	}

	private boolean spam(final Message message) {
		boolean spam = false;
		final List<String> spamWords = this.systemConfigurationService.getSystemConfiguration().getSpamWords();
		for (int i = 0; i < spamWords.size(); i++)
			if (message.getBody().contains(spamWords.get(i)) || message.getSubject().contains(spamWords.get(i)) || message.getTags().contains(spamWords.get(i)))
				spam = true;
		return spam;
	}

}
