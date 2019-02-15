
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

@Component
@Transactional
public class StringToProfessionalRecordConverter implements Converter<String, ProfessionalRecord> {

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;


	@Override
	public ProfessionalRecord convert(final String text) {
		ProfessionalRecord result;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				final int id = Integer.valueOf(text);
				result = this.professionalRecordRepository.findOne(id);
			}
		} catch (final Throwable izipizelemonezcuici) {
			throw new IllegalArgumentException(izipizelemonezcuici);
		}
		return result;
	}

}
