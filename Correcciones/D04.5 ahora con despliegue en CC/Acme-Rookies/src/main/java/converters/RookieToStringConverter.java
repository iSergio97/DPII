/*
 * RookieToStringConverter.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Rookie;

@Component
@Transactional
public class RookieToStringConverter implements Converter<Rookie, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Rookie rookie) {
		return rookie == null ? null : String.valueOf(rookie.getId());
	}

}
