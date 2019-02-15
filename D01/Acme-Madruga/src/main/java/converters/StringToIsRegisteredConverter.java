package converters;

import org.springframework.beans.fisRegisteredy.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.IsRegisteredRepository;
import domain.IsRegistered;

@Component
@Transactional
public class StringToIsRegisteredConverter implements Converter<String, IsRegistered> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private IsRegisteredRepository	isRegisteredRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public IsRegistered convert(final String text) {
		try {
			return this.isRegisteredRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
