
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.FixUpTaskCategory;

@Component
@Transactional
public class FixUpTaskCategoryToStringConverter implements Converter<FixUpTaskCategory, String> {

	@Override
	public String convert(final FixUpTaskCategory category) {
		String result;
		if (category == null)
			result = null;
		else
			result = String.valueOf(category.getId());

		return result;
	}

}
