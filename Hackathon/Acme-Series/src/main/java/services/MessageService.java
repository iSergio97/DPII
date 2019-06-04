
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import domain.Actor;
import domain.Message;
import domain.MessageBox;
import forms.MessageForm;
import repositories.MessageRepository;

@Service
@Transactional
public class MessageService extends AbstractService<MessageRepository, Message> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private ActorService				actorService;
	@Autowired
	private MessageBoxService			messageBoxService;
	@Autowired
	private SystemConfigurationService	systemConfigurationservice;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Message create() {
		final Message message = super.create();

		final Actor principal = this.actorService.findPrincipal();

		message.setSender(principal);

		final MessageBox outBox = this.messageBoxService.findOutbox(principal.getId());
		final Collection<MessageBox> messageBoxes = new ArrayList<>();
		messageBoxes.add(outBox);
		message.setMessageBoxes(messageBoxes);

		return message;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public MessageForm createForm() {
		final MessageForm messageForm = super.instanceClass(MessageForm.class);

		messageForm.setBroadcast(false);

		return messageForm;
	}

	public MessageForm createBroadcast() {
		final MessageForm messageForm = super.instanceClass(MessageForm.class);

		messageForm.setPriority("HIGH");
		messageForm.setBroadcast(true);

		return messageForm;
	}

	public Message reconstruct(final MessageForm messageForm, final BindingResult bindingResult) {
		final Message message = this.create();

		message.setBody(messageForm.getBody());
		message.setSubject(messageForm.getSubject());
		message.setPriority(messageForm.getPriority());
		message.setTags(messageForm.getTags());
		message.setRecipients(messageForm.getRecipients());
		message.setMoment(new Date());
		final List<String> spamWordsUpperCase = new ArrayList<>();
		for (final String spamWord : this.systemConfigurationservice.getSystemConfiguration().getSpamWords())
			spamWordsUpperCase.add(spamWord.toUpperCase());
		final String body = message.getBody().toUpperCase();
		final String subject = message.getSubject().toUpperCase();
		final String tags = message.getTags().toUpperCase();
		if (message.getSender().getIsFlagged() || spamWordsUpperCase.contains(body) || spamWordsUpperCase.contains(subject) || spamWordsUpperCase.contains(tags))
			message.setIsSpam(true);

		this.validator.validate(message, bindingResult);
		if (bindingResult.hasErrors())
			throw new ValidationException();

		return message;
	}

	public Collection<Message> findMessages(final int messageBoxId) {
		return this.repository.findMessages(messageBoxId);
	}

	public int countSpam(final int id) {
		return this.repository.countSpam(id);
	}

	public int countMails(final int id) {
		return this.repository.countMails(id);
	}

	public Collection<Message> getSent(final int id) {
		return this.repository.getSent(id);
	}

	public Collection<Message> getRecieved(final int id) {
		return this.repository.getRecieved(id);
	}
}
