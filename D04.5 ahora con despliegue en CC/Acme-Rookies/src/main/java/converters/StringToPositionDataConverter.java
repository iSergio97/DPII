/*
 * StringToPositionDataConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PositionDataRepository;
import domain.PositionData;

@Component
@Transactional
public class StringToPositionDataConverter implements Converter<String, PositionData> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private PositionDataRepository	positionDataRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public PositionData convert(final String text) {
		try {
			return this.positionDataRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
