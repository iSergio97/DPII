
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorService		actorService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public Message create() {
		final Message m = new Message();
		m.setBody("");
		m.setDate(new Date());
		m.setPriority("");
		m.setTags(new ArrayList<String>());
		m.setSender(this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()));
		m.setSubject("");

		return m;
	}

	public Message save(final Message message) {
		Assert.isTrue(message != null);
		return this.messageRepository.save(message);
	}

	public Iterable<Message> save(final Iterable<Message> messages) {
		Assert.isTrue(messages != null);
		return this.messageRepository.save(messages);
	}

	public void delete(final Message message) {
		Assert.isTrue(message != null);
		this.messageRepository.delete(message);
	}

	public void delete(final Iterable<Message> messages) {
		Assert.isTrue(messages != null);
		this.messageRepository.delete(messages);
	}

	public Message findById(final int id) {
		return this.messageRepository.findOne(id);
	}

	public List<Message> findAll() {
		return this.messageRepository.findAll();
	}

	// Specific Methods ----------------------------------------------------------------

	public List<Actor> getActorsWhoSendMessagesWithWord(final String word) {
		return this.messageRepository.getActorsWhoSendMessagesWithWord(word);
	}

	public Message sendMessage(final Message message, final Actor actor, final List<Actor> recipients) {
		Assert.isTrue(message != null);
		Assert.isTrue(actor != null);
		final Collection<Message> ls = actor.getMessagesSent();
		ls.add(message);
		final Message message2 = this.messageRepository.save(message);
		actor.setMessagesSent(ls);
		for (final Actor a : recipients) {
			final Collection<Message> msRe = a.getMessagesReceived();
			msRe.add(message2);
			a.setMessagesReceived(msRe);
		}

		return message2;
	}
}
