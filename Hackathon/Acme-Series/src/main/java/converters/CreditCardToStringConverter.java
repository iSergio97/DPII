/*
 * CreditCardToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.CreditCard;

@Component
@Transactional
public class CreditCardToStringConverter implements Converter<CreditCard, String> {

	@Override
	public String convert(final CreditCard creditCard) {

		if (creditCard == null)
			return null;
		else {
			final StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(creditCard.getBrand());
			stringBuilder.append('|');
			stringBuilder.append(creditCard.getNumber());
			stringBuilder.append('|');
			stringBuilder.append(creditCard.getHolder());
			stringBuilder.append('|');
			stringBuilder.append(Integer.toString(creditCard.getExpirationMonth()));
			stringBuilder.append('|');
			stringBuilder.append(Integer.toString(creditCard.getExpirationYear()));
			stringBuilder.append('|');
			stringBuilder.append(Integer.toString(creditCard.getCVV()));
			return stringBuilder.toString();
		}
	}

}
