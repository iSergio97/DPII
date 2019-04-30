/*
 * HackerToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Hacker;

@Component
@Transactional
public class HackerToStringConverter implements Converter<Hacker, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Hacker hacker) {
		return hacker == null ? null : String.valueOf(hacker.getId());
	}

}
