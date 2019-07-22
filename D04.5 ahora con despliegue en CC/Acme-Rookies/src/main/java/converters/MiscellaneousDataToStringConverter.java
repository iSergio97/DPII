/*
 * MiscellaneousDataToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MiscellaneousData;

@Component
@Transactional
public class MiscellaneousDataToStringConverter implements Converter<MiscellaneousData, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final MiscellaneousData miscellaneousData) {
		return miscellaneousData == null ? null : String.valueOf(miscellaneousData.getId());
	}

}
