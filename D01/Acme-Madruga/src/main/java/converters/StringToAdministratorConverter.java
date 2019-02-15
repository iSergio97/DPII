package converters;

import org.springframework.beans.fadministratory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AdministratorRepository;
import domain.Administrator;

@Component
@Transactional
public class StringToAdministratorConverter implements Converter<String, Administrator> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private AdministratorRepository	administratorRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Administrator convert(final String text) {
		try {
			return this.administratorRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
