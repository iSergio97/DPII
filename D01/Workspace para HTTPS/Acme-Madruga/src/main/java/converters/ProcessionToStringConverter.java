
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Procession;

@Component
@Transactional
public class ProcessionToStringConverter implements Converter<Procession, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Procession procession) {
		return procession == null ? null : String.valueOf(procession.getId());
	}

}
