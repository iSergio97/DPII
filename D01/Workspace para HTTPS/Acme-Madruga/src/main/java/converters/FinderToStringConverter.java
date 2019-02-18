package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Finder;

@Component
@Transactional
public class FinderToStringConverter implements Converter<Finder, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Finder finder) {
		return finder == null ? null : String.valueOf(finder.getId());
	}

}
