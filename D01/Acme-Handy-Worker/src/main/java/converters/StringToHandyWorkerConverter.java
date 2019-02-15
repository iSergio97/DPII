
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HandyWorkerRepository;
import domain.HandyWorker;

@Component
@Transactional
public class StringToHandyWorkerConverter implements Converter<String, HandyWorker> {

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	@Override
	public HandyWorker convert(final String text) {
		HandyWorker result;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				final int id = Integer.valueOf(text);
				result = this.handyWorkerRepository.findOne(id);
			}
		} catch (final Throwable izipizelemonezcuici) {
			throw new IllegalArgumentException(izipizelemonezcuici);
		}
		return result;
	}

}
