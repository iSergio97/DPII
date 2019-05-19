/*
 * SerieToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Serie;

@Component
@Transactional
public class SerieToStringConverter implements Converter<Serie, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Serie serie) {
		return serie == null ? null : String.valueOf(serie.getId());
	}

}
