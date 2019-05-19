/*
 * StringToGenreConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GenreRepository;
import domain.Genre;

@Component
@Transactional
public class StringToGenreConverter implements Converter<String, Genre> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private GenreRepository	genreRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Genre convert(final String text) {
		try {
			return this.genreRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
