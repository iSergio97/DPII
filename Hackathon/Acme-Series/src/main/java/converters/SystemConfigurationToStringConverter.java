/*
 * SystemConfigurationToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SystemConfiguration;

@Component
@Transactional
public class SystemConfigurationToStringConverter implements Converter<SystemConfiguration, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final SystemConfiguration systemConfiguration) {
		return systemConfiguration == null ? null : String.valueOf(systemConfiguration.getId());
	}

}
