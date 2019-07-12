
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Enrolment;

@Component
@Transactional
public class EnrolmentToStringConverter implements Converter<Enrolment, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Enrolment isRegistered) {
		return isRegistered == null ? null : String.valueOf(isRegistered.getId());
	}

}
