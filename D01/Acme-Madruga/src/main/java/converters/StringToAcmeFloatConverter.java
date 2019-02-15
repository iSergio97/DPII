package converters;

import org.springframework.beans.facmeFloaty.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AcmeFloatRepository;
import domain.AcmeFloat;

@Component
@Transactional
public class StringToAcmeFloatConverter implements Converter<String, AcmeFloat> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private AcmeFloatRepository	acmeFloatRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public AcmeFloat convert(final String text) {
		try {
			return this.acmeFloatRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
