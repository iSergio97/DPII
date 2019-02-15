package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.IsRegistered;

@Component
@Transactional
public class IsRegisteredToStringConverter implements Converter<IsRegistered, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final IsRegistered isRegistered) {
		return isRegistered == null ? null : String.valueOf(isRegistered.getId());
	}

}
