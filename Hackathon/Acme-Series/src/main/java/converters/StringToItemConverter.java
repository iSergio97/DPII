
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ItemRepository;
import domain.Item;

@Component
@Transactional
public class StringToItemConverter implements Converter<String, Item> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private ItemRepository	itemRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Item convert(final String text) {
		try {
			return this.itemRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
