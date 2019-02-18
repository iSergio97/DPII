
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
import domain.Message;

@Component
@Transactional
public class StringToMessageConverter implements Converter<String, Message> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private MessageRepository	messageRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Message convert(final String text) {
		try {
			return this.messageRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
