/*
 * SeasonToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Season;

@Component
@Transactional
public class SeasonToStringConverter implements Converter<Season, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Season season) {
		return season == null ? null : String.valueOf(season.getId());
	}

}
