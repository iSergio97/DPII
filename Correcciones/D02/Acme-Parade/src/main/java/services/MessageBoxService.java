
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageBoxRepository;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MessageBoxRepository	messageBoxRepository;

	@Autowired
	private ActorService			actorService;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public MessageBoxService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public MessageBox create() {
		final MessageBox messageBox = new MessageBox();
		messageBox.setActor(this.actorService.findPrincipal());
		messageBox.setName("");
		messageBox.setMessages(new ArrayList<Message>());
		return messageBox;
	}

	public MessageBox save(final MessageBox messageBox) {
		Assert.isTrue(messageBox != null);
		return this.messageBoxRepository.save(messageBox);
	}

	public Iterable<MessageBox> save(final Iterable<MessageBox> messageBoxs) {
		Assert.isTrue(messageBoxs != null);
		return this.messageBoxRepository.save(messageBoxs);
	}

	public void delete(final MessageBox messageBox) {
		Assert.isTrue(messageBox != null);
		this.messageBoxRepository.delete(messageBox);
	}

	public void delete(final Iterable<MessageBox> messageBoxs) {
		Assert.isTrue(messageBoxs != null);
		this.messageBoxRepository.delete(messageBoxs);
	}

	public MessageBox findOne(final int id) {
		return this.messageBoxRepository.findOne(id);
	}

	public List<MessageBox> findAll() {
		return this.messageBoxRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public List<MessageBox> createSystemBoxes() {
		final List<MessageBox> messageBoxes = new ArrayList<>();
		final MessageBox inBox = new MessageBox();
		inBox.setName("InBox");
		inBox.setMessages(new ArrayList<Message>());
		inBox.setIsSystem(true);
		messageBoxes.add(inBox);

		final MessageBox outBox = new MessageBox();
		outBox.setName("OutBox");
		outBox.setMessages(new ArrayList<Message>());
		outBox.setIsSystem(true);
		messageBoxes.add(outBox);

		final MessageBox trashBox = new MessageBox();
		trashBox.setName("TrashBox");
		trashBox.setMessages(new ArrayList<Message>());
		trashBox.setIsSystem(true);
		messageBoxes.add(trashBox);

		final MessageBox spamBox = new MessageBox();
		spamBox.setName("SpamBox");
		spamBox.setMessages(new ArrayList<Message>());
		spamBox.setIsSystem(true);
		messageBoxes.add(spamBox);

		final MessageBox notifications = new MessageBox();
		notifications.setName("Notifications");
		notifications.setMessages(new ArrayList<Message>());
		notifications.setIsSystem(true);
		messageBoxes.add(notifications);

		return messageBoxes;
	}

	public List<MessageBox> messageFromActor(final Actor a) {
		final int id = a.getId();
		return this.messageBoxRepository.messageFromActor(id);
	}
}
