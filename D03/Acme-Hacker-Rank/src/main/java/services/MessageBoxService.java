
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Actor;
import domain.MessageBox;
import repositories.MessageBoxRepository;

@Service
@Transactional
public class MessageBoxService extends AbstractService<MessageBox> {

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
		return messageBox;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public List<MessageBox> createSystemBoxes() {
		final List<MessageBox> messageBoxes = new ArrayList<>();
		final MessageBox inBox = new MessageBox();
		inBox.setName("InBox");
		inBox.setSystem(true);
		messageBoxes.add(inBox);

		final MessageBox outBox = new MessageBox();
		outBox.setName("OutBox");
		outBox.setSystem(true);
		messageBoxes.add(outBox);

		final MessageBox trashBox = new MessageBox();
		trashBox.setName("TrashBox");
		trashBox.setSystem(true);
		messageBoxes.add(trashBox);

		final MessageBox spamBox = new MessageBox();
		spamBox.setName("SpamBox");
		spamBox.setSystem(true);
		messageBoxes.add(spamBox);

		final MessageBox notifications = new MessageBox();
		notifications.setName("Notifications");
		notifications.setSystem(true);
		messageBoxes.add(notifications);

		return messageBoxes;
	}

	public List<MessageBox> messageFromActor(final Actor a) {
		return this.messageBoxRepository.messageFromActor(a.getId());
	}
}
