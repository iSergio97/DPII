
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Enrolment;

import repositories.EnrolmentRepository;

@Component
@Transactional
public class StringToEnrolmentConverter implements Converter<String, Enrolment> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private EnrolmentRepository	enrolmentRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Enrolment convert(final String text) {
		try {
			return this.enrolmentRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
