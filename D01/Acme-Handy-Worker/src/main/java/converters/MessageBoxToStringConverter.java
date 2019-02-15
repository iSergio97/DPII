
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.MessageBox;

@Component
@Transactional
public class MessageBoxToStringConverter implements Converter<MessageBox, String> {

	@Override
	public String convert(final MessageBox messageBox) {
		String result;
		if (messageBox == null)
			result = null;
		else
			result = String.valueOf(messageBox.getId());

		return result;
	}

}
