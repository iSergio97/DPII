
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RequestRepository;
import domain.Request;

@Component
@Transactional
public class StringToRequestConverter implements Converter<String, Request> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private RequestRepository	requestRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Request convert(final String text) {
		try {
			return this.requestRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
