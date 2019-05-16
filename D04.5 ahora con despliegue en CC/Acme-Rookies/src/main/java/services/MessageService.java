
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
import security.LoginService;

@Service
@Transactional
public class MessageService extends AbstractService<MessageRepository, Message> {

	@Autowired
	private ActorService				actorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationservice;

	@Autowired
	private MessageBoxService			messageBoxService;


	@Override
	public Message create() {
		final Message m = new Message();

		final Actor actual = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		m.setBody("");
		m.setIsSpam(false);
		m.setSender(actual);
		m.setTags("");
		m.setRecipients(new ArrayList<Actor>());
		m.setSubject("");
		final MessageBox outBox = this.messageBoxService.findOutbox(actual.getId());
		final Collection<MessageBox> mbs = new ArrayList<>();
		mbs.add(outBox);
		m.setMessageBoxes(mbs);
		return m;
	}

	public MessageForm createForm() {
		final MessageForm mf = new MessageForm();

		mf.setSubject("");
		mf.setBody("");
		mf.setTags("");
		mf.setRecipients(new ArrayList<Actor>());
		mf.setBroadcast(false);

		return mf;
	}

	public MessageForm createBroadcast() {
		final MessageForm mf = new MessageForm();

		mf.setSubject("");
		mf.setBody("");
		mf.setTags("");
		mf.setPriority("HIGH");
		mf.setRecipients(new ArrayList<Actor>());
		mf.setBroadcast(true);

		return mf;
	}

	public Message reconstruct(final MessageForm mf, final BindingResult bindingResult) {
		final Message m = this.create();

		m.setBody(mf.getBody());
		m.setSubject(mf.getSubject());
		m.setPriority(mf.getPriority());
		m.setTags(mf.getTags());
		m.setRecipients(mf.getRecipients());
		m.setDate(new Date());
		final List<String> sw = this.systemConfigurationservice.getSystemConfiguration().getSpamWords();
		List<String> swUpper = new ArrayList<>();
		for (String s : sw) {
			swUpper.add(s.toUpperCase());
		}
		String body = m.getBody().toUpperCase();
		String subject = m.getSubject().toUpperCase();
		String tags = m.getTags().toUpperCase();
		if (m.getSender().getIsFlagged() || swUpper.contains(body) || swUpper.contains(subject) || swUpper.contains(tags))
			m.setIsSpam(true);

		this.validator.validate(m, bindingResult);
		if (bindingResult.hasErrors())
			throw new ValidationException();

		return m;
	}

	public Collection<Message> findMessages(final int messageBoxId) {
		return this.repository.findMessages(messageBoxId);
	}

	public int countSpam(int id) {
		return this.repository.countSpam(id);
	}

	public int countMails(int id) {
		return this.repository.countMails(id);
	}

}
