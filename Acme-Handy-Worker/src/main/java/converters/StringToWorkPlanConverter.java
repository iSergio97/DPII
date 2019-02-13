
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.WorkPlanRepository;
import domain.WorkPlan;

@Component
@Transactional
public class StringToWorkPlanConverter implements Converter<String, WorkPlan> {

	@Autowired
	private WorkPlanRepository	workPlanRepository;


	@Override
	public WorkPlan convert(final String text) {
		WorkPlan result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.workPlanRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
