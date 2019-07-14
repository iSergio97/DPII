
package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConversionUtils {

	public static String listToString(final List<String> list, final String elementSeparator) {
		String result = "";
		if (list.size() > 0) {
			for (final String string : list)
				result = result + string + elementSeparator;
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static List<String> stringToList(final String string, final String elementSeparator) {
		final List<String> result = new ArrayList<>();
		for (final String s : string.split(elementSeparator))
			if (s.length() > 0)
				result.add(s);
		return result;
	}

	public static String mapToString(final Map<String, String> map, final String pairSeparator, final String entrySeparator) {
		String result = "";
		if (map.size() > 0) {
			for (final Entry<String, String> entry : map.entrySet())
				result = result + entry.getKey() + pairSeparator + entry.getValue() + entrySeparator;
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static Map<String, String> stringToMap(final String string, final String pairSeparator, final String entrySeparator) {
		final Map<String, String> result = new HashMap<>();
		for (final String entry : string.split(entrySeparator))
			if (entry.length() > 0) {
				final String[] pair = entry.split(pairSeparator);
				result.put(pair[0], pair[1]);
			}
		return result;
	}

}
