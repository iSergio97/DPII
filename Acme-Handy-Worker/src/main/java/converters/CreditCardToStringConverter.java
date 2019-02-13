
package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.CreditCard;

@Component
@Transactional
public class CreditCardToStringConverter implements Converter<CreditCard, String> {

	@Override
	public String convert(final CreditCard creditCard) {
		// TODO Auto-generated method stub
		String result;
		StringBuilder builder;

		if (creditCard == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(creditCard.getBrand(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(creditCard.getNumber(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(creditCard.getHolder(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(creditCard.getExpirationMonth()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(creditCard.getExpirationYear()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(creditCard.getCVV()), "UTF-8"));
				result = builder.toString();
			} catch (final Throwable wopsi) {
				throw new RuntimeException(wopsi);
			}
		return result;
	}

}
