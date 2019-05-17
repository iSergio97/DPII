/*
 * StringToMiscellaneousDataConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MiscellaneousDataRepository;
import domain.MiscellaneousData;

@Component
@Transactional
public class StringToMiscellaneousDataConverter implements Converter<String, MiscellaneousData> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public MiscellaneousData convert(final String text) {
		try {
			return this.miscellaneousDataRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
