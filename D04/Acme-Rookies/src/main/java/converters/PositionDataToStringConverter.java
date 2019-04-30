/*
 * PositionDataToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PositionData;

@Component
@Transactional
public class PositionDataToStringConverter implements Converter<PositionData, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final PositionData positionData) {
		return positionData == null ? null : String.valueOf(positionData.getId());
	}

}
