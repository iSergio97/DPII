
package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;

import domain.CreditCard;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String, CreditCard> {

	@Override
	public CreditCard convert(final String arg0) {
		CreditCard creditCard;
		String[] parts;

		if (arg0 == null)
			creditCard = null;
		else
			try {
				parts = arg0.split("\\|");
				creditCard = new CreditCard();
				creditCard.setBrand(URLDecoder.decode(parts[0], "UTF-8"));
				creditCard.setNumber(URLDecoder.decode(parts[1], "UTF-8"));
				creditCard.setHolder(URLDecoder.decode(parts[2], "UTF-8"));
				creditCard.setExpirationMonth(Integer.valueOf(parts[3]));
				creditCard.setExpirationYear(Integer.valueOf(parts[4]));
				creditCard.setCVV(Integer.valueOf(URLDecoder.decode(parts[5], "UTF-8")));
			} catch (final Throwable e) {
				throw new RuntimeException(e);
			}
		return creditCard;
	}

}
