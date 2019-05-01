/*
 * StringToHackerConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.HackerRepository;
import domain.Hacker;

@Component
@Transactional
public class StringToHackerConverter implements Converter<String, Hacker> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private HackerRepository	hackerRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Hacker convert(final String text) {
		try {
			return this.hackerRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
