/*
 * StringToSeasonConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SeasonRepository;
import domain.Season;

@Component
@Transactional
public class StringToSeasonConverter implements Converter<String, Season> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private SeasonRepository	seasonRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Season convert(final String text) {
		try {
			return this.seasonRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
