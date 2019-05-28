
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PublisherRepository;
import domain.Publisher;

@Component
@Transactional
public class StringToPublisherConverter implements Converter<String, Publisher> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private PublisherRepository	publisherRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Publisher convert(final String text) {
		try {
			return this.publisherRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
