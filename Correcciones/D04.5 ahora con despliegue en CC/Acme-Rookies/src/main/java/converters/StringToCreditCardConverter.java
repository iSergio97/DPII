/*
 * StringToCreditCardConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;

@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String, CreditCard> {

	@Override
	public CreditCard convert(final String text) {
		if (text == null)
			return null;
		else {
			final CreditCard creditCard = new CreditCard();
			final String[] creditCardFields = text.split("|");
			creditCard.setBrand(creditCardFields[0]);
			creditCard.setNumber(creditCardFields[1]);
			creditCard.setHolder(creditCardFields[2]);
			creditCard.setExpirationMonth(Integer.valueOf(creditCardFields[3]));
			creditCard.setExpirationYear(Integer.valueOf(creditCardFields[4]));
			creditCard.setCVV(Integer.valueOf(creditCardFields[5]));
			return creditCard;
		}
	}

}
