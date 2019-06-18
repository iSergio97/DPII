/*
 * ApplicationToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Application;

@Component
@Transactional
public class ApplicationToStringConverter implements Converter<Application, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Application application) {
		return application == null ? null : String.valueOf(application.getId());
	}

}
