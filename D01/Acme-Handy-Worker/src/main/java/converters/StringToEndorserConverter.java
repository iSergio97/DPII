
package converters;

import domain.Endorsement;
import domain.EndorserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import repositories.EndorsementRepository;
import repositories.EndorserRecordRepository;

@Component
@Transactional
public class StringToEndorserConverter implements Converter<String, EndorserRecord> {

	@Autowired
	private EndorserRecordRepository endorserRecordRepository;


	@Override
	public EndorserRecord convert(final String text) {
		EndorserRecord result;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				final int id = Integer.valueOf(text);
				result = this.endorserRecordRepository.findOne(id);
			}
		} catch (final Throwable izipizelemonezcuici) {
			throw new IllegalArgumentException(izipizelemonezcuici);
		}
		return result;
	}

}
