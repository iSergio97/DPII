
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageBoxRepository;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MessageBoxRepository	messageBoxRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private ActorService			actorService;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public MessageBoxService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public MessageBox create() {
		final MessageBox mb = new MessageBox();
		mb.setActor(this.actorService.findPrincipal());
		mb.setName("");
		mb.setMessages(new ArrayList<Message>());
		return mb;
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
		final List<MessageBox> mbls = new ArrayList<>();
		final MessageBox inBox = new MessageBox();
		inBox.setName("InBox");
		inBox.setMessages(new ArrayList<Message>());
		//inBox.setActor(this.actorService.findPrincipal());
		mbls.add(inBox);

		final MessageBox outBox = new MessageBox();
		outBox.setName("OutBox");
		outBox.setMessages(new ArrayList<Message>());
		//outBox.setActor(this.actorService.findPrincipal());
		mbls.add(outBox);

		final MessageBox trashBox = new MessageBox();
		trashBox.setName("SpamBox");
		trashBox.setMessages(new ArrayList<Message>());
		//trashBox.setActor(this.actorService.findPrincipal());
		mbls.add(trashBox);

		final MessageBox spamBox = new MessageBox();
		spamBox.setName("TrashBox");
		spamBox.setMessages(new ArrayList<Message>());
		//spamBox.setActor(this.actorService.findPrincipal());
		mbls.add(spamBox);

		return mbls;
	}

}
