package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageBoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MessageBoxRepository		messageBoxRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public MessageBoxService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

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

}
