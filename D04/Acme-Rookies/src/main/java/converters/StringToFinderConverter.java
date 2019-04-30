/*
 * StringToFinderConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.FinderRepository;
import domain.Finder;

@Component
@Transactional
public class StringToFinderConverter implements Converter<String, Finder> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private FinderRepository	finderRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Finder convert(final String text) {
		try {
			return this.finderRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
