package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Message;

@Service
@Transactional
public class MessageService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MessageRepository		messageRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public MessageService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

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

	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}

	public List<Message> findAll() {
		return this.messageRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
