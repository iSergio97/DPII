package converters;

import org.springframework.beans.fsystemConfigurationy.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Component
@Transactional
public class StringToSystemConfigurationConverter implements Converter<String, SystemConfiguration> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public SystemConfiguration convert(final String text) {
		try {
			return this.systemConfigurationRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
