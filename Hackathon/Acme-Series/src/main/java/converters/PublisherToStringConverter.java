
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Publisher;

@Component
@Transactional
public class PublisherToStringConverter implements Converter<Publisher, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Publisher publisher) {
		return publisher == null ? null : String.valueOf(publisher.getId());
	}

}
