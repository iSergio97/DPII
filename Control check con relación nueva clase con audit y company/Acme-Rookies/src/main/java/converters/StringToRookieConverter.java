/*
 * StringToRookieConverter.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Rookie;
import repositories.RookieRepository;

@Component
@Transactional
public class StringToRookieConverter implements Converter<String, Rookie> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private RookieRepository rookieRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Rookie convert(final String text) {
		try {
			return this.rookieRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
