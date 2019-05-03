
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.MessageBox;
import repositories.MessageBoxRepository;

@Service
@Transactional
public class MessageBoxService extends AbstractService<MessageBoxRepository, MessageBox> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private ActorService actorService;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public MessageBox create() {
		final MessageBox messageBox = new MessageBox();
		messageBox.setActor(this.actorService.findPrincipal());
		messageBox.setName("");
		return messageBox;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public List<MessageBox> createSystemBoxes() {
		final List<MessageBox> messageBoxes = new ArrayList<>();
		final MessageBox inBox = new MessageBox();
		inBox.setName("InBox");
		inBox.setIsSystem(true);
		messageBoxes.add(inBox);

		final MessageBox outBox = new MessageBox();
		outBox.setName("OutBox");
		outBox.setIsSystem(true);
		messageBoxes.add(outBox);

		final MessageBox trashBox = new MessageBox();
		trashBox.setName("TrashBox");
		trashBox.setIsSystem(true);
		messageBoxes.add(trashBox);

		final MessageBox spamBox = new MessageBox();
		spamBox.setName("SpamBox");
		spamBox.setIsSystem(true);
		messageBoxes.add(spamBox);

		final MessageBox notificationBox = new MessageBox();
		notificationBox.setName("NotificationBox");
		notificationBox.setIsSystem(true);
		messageBoxes.add(notificationBox);

		return messageBoxes;
	}

	public List<MessageBox> messageFromActor(final int actorId) {
		return this.repository.messageFromActor(actorId);
	}

	public MessageBox findInbox(final int actorId) {
		return this.repository.findInBox(actorId);
	}

	public MessageBox findOutbox(final int actorId) {
		return this.repository.findOutBox(actorId);
	}

	public MessageBox findSpamBox(final int actorId) {
		return this.repository.findSpamBox(actorId);
	}

	public MessageBox findTrashBox(final int actorId) {
		return this.repository.findTrashBox(actorId);
	}

	public MessageBox findNotificationBox(final int actorId) {
		return this.repository.findNotificationBox(actorId);
	}

	public MessageBox findByPrincipalAndName(final int actorId, final String name) {
		return this.repository.findByPrincipalAndName(actorId, name);
	}

	public Collection<MessageBox> findSystemBoxes(final int actorId) {
		return this.repository.findSystemBoxes(actorId);
	}

	public Collection<MessageBox> findMessageBoxes(final int actorId) {
		return this.repository.findMessageBoxes(actorId);
	}
}
