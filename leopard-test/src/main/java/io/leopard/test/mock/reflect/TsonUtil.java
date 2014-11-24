package io.leopard.test.mock.reflect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TsonUtil {

	public static String[] split(String str, Character separator) {
		if (str.indexOf('\'') == -1 && str.indexOf('"') == -1) {
			return StringUtils.split(str, separator);
		}
		List<String> list = new ArrayList<String>();
		char[] chars = str.toCharArray();
		int prefix = 0;
		boolean hasQuotes = false;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '\'' || chars[i] == '"') {
				hasQuotes = !hasQuotes;
				continue;
			}
			if (chars[i] == separator && !hasQuotes) {
				String word = str.substring(prefix, i);
				// System.out.println("prefix:" + prefix + " i:" + i + " word:" + word);
				list.add(word);
				prefix = i + 1;
			}
		}
		if (prefix < chars.length) {
			list.add(str.substring(prefix));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
}
