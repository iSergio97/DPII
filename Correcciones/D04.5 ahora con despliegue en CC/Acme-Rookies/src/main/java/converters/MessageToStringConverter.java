/*
 * MessageToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Message;

@Component
@Transactional
public class MessageToStringConverter implements Converter<Message, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Message message) {
		return message == null ? null : String.valueOf(message.getId());
	}

}
