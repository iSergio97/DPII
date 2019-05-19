/*
 * GenreToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Genre;

@Component
@Transactional
public class GenreToStringConverter implements Converter<Genre, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Genre genre) {
		return genre == null ? null : String.valueOf(genre.getId());
	}

}
