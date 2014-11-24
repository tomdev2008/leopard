package io.leopard.util.ubb.old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ColorUbb {
	protected static final Log logger = LogFactory.getLog(ColorUbb.class);

	private static final Pattern c3 = Pattern.compile("(\\[color=.*?\\].*?\\[\\/color\\])");

	public static String color(String content) {
		String result;
		try {
			result = getCSLink(content);
		}
		catch (IllegalArgumentException e) {
			logger.error("content:" + content);
			throw e;
		}
		String result2 = new io.leopard.util.ubb.ColorUbb().parse(content);

		if (!result.equals(result2)) {
			logger.error("ubb:" + content);
		}
		return result;
	}

	public static String getCSLink(String content) {
		Matcher m = c3.matcher(content);
		String str = "";
		while (m.find()) {
			String temp = m.group();
			temp = replaceCSstr(temp); // 处理[u]字符
			if (StringUtils.isEmpty(temp)) {
				continue;
			}
			str = m.replaceFirst(temp);
			m.reset(str); // 替换字符会改变适配器，必须重置适配器，并且替换内容
		}
		if (StringUtils.isEmpty(str)) {
			return content;
		}
		return str;
	}

	private static String replaceCSstr(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}

		String str = content.replaceAll("\\[color=.*?\\]", "");
		str = content.replace(str, "");
		str = str.replace("[color=", "");
		str = str.replace("]", "");

		String temp = content;
		temp = temp.replaceAll("\\[color=.*?\\]", "");
		temp = temp.replace("[/color]", "");

		if (StringUtils.isEmpty(temp)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<span style='color:");
		sb.append(str);
		sb.append("'>");
		sb.append(temp);
		sb.append("</span>");

		return sb.toString();
	}
}
