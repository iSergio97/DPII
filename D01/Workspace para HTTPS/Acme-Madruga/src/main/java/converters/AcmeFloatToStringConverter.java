package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AcmeFloat;

@Component
@Transactional
public class AcmeFloatToStringConverter implements Converter<AcmeFloat, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final AcmeFloat acmeFloat) {
		return acmeFloat == null ? null : String.valueOf(acmeFloat.getId());
	}

}
