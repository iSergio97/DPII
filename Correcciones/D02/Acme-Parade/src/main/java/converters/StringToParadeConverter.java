
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ParadeRepository;
import domain.Parade;

@Component
@Transactional
public class StringToParadeConverter implements Converter<String, Parade> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private ParadeRepository	paradeRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Parade convert(final String text) {
		try {
			return this.paradeRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
