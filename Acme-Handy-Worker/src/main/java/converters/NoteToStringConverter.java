
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Note;

@Component
@Transactional
public class NoteToStringConverter implements Converter<Note, String> {

	@Override
	public String convert(final Note note) {
		String result;
		if (note == null)
			result = null;
		else
			result = String.valueOf(note.getId());

		return result;
	}

}
