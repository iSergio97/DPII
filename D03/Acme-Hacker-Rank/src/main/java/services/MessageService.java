/*
 * MessageService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.UserAccountRepository;
import utilities.ConversionUtils;
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MessageRepository		messageRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private ActorService			actorService;
	@Autowired
	private UserAccountRepository	userAccountRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator				validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public MessageService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Message create() {
		final Message message = new Message();

		message.setDate(new Date());
		message.setSubject("");
		message.setBody("");
		message.setTags(new ArrayList<String>());

		return message;
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

	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}

	public List<Message> findAll() {
		return this.messageRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public MessageForm createForm() {
		final MessageForm messageForm = new MessageForm();

		messageForm.setRecipient("");
		messageForm.setSubject("");
		messageForm.setBody("");
		messageForm.setTags("");

		return messageForm;
	}

	public Message reconstructForm(final MessageForm messageForm, final BindingResult bindingResult) {
		final Message message = this.create();

		message.setSender(this.actorService.findPrincipal());
		message.setRecipient(this.actorService.findByUserAccountId(this.userAccountRepository.findByUsername(messageForm.getRecipient()).getId()));
		message.setSubject(messageForm.getSubject());
		message.setBody(messageForm.getBody());
		message.setTags(ConversionUtils.stringToList(messageForm.getTags(), ","));

		this.validator.validate(message, bindingResult);

		return message;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
