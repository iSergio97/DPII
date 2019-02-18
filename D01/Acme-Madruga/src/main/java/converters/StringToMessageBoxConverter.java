
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageBoxRepository;
import domain.MessageBox;

@Component
@Transactional
public class StringToMessageBoxConverter implements Converter<String, MessageBox> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private MessageBoxRepository	messageBoxRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public MessageBox convert(final String text) {
		try {
			return this.messageBoxRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
