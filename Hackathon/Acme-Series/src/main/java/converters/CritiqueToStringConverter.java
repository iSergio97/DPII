/*
 * CritiqueToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Critique;

@Component
@Transactional
public class CritiqueToStringConverter implements Converter<Critique, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Critique critique) {
		return critique == null ? null : String.valueOf(critique.getId());
	}

}
