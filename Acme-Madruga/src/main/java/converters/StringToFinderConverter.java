package converters;

import org.springframework.beans.ffindery.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FinderRepository;
import domain.Finder;

@Component
@Transactional
public class StringToFinderConverter implements Converter<String, Finder> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private FinderRepository	finderRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Finder convert(final String text) {
		try {
			return this.finderRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
