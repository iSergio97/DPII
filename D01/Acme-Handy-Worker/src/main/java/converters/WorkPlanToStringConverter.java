
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.WorkPlan;

@Component
@Transactional
public class WorkPlanToStringConverter implements Converter<WorkPlan, String> {

	@Override
	public String convert(final WorkPlan workPlan) {
		String result;
		if (workPlan == null)
			result = null;
		else
			result = String.valueOf(workPlan.getId());

		return result;
	}

}
