/*
 * StringToSerieConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SerieRepository;
import domain.Serie;

@Component
@Transactional
public class StringToSerieConverter implements Converter<String, Serie> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private SerieRepository	serieRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Serie convert(final String text) {
		try {
			return this.serieRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
