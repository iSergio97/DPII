
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EducationalRecordRepository;
import domain.EducationalRecord;

@Component
@Transactional
public class StringToEducationalRecordConverter implements Converter<String, EducationalRecord> {

	@Autowired
	private EducationalRecordRepository	educationalRecordRepository;


	@Override
	public EducationalRecord convert(final String text) {
		EducationalRecord result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.educationalRecordRepository.findOne(id);
			}
		} catch (final Throwable izipizelemonezcuici) {
			throw new IllegalArgumentException(izipizelemonezcuici);
		}
		return result;
	}

}
