
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.FixUpTaskCategoryRepository;
import domain.FixUpTaskCategory;

@Component
@Transactional
public class StringToFixUpTaskCategoryConverter implements Converter<String, FixUpTaskCategory> {

	@Autowired
	private FixUpTaskCategoryRepository	fixUpTaskCategoryRepository;


	@Override
	public FixUpTaskCategory convert(final String text) {
		FixUpTaskCategory result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.fixUpTaskCategoryRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
