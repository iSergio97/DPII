package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Brotherhood;

@Component
@Transactional
public class BrotherhoodToStringConverter implements Converter<Brotherhood, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Brotherhood brotherhood) {
		return brotherhood == null ? null : String.valueOf(brotherhood.getId());
	}

}
