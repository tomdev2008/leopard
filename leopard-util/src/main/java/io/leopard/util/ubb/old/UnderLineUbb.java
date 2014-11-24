package io.leopard.util.ubb.old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UnderLineUbb {
	protected static final Log logger = LogFactory.getLog(UnderLineUbb.class);

	private static final Pattern c2 = Pattern.compile("(\\[u\\].*?\\[\\/u\\])");

	public static String underLine(String content) {
		String result = getCULink(content);
		String result2 = new io.leopard.util.ubb.UnderLineUbb().parse(content);

		if (!result.equals(result2)) {
			logger.error("ubb:" + content);
		}
		return result;
	}

	public static String getCULink(String content) {
		Matcher m = c2.matcher(content);
		String str = "";
		while (m.find()) {
			String temp = m.group();
			temp = replaceCUstr(temp); // 处理[u]字符
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

	protected static String replaceCUstr(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String temp = content;
		temp = temp.replace("[u]", "");
		temp = temp.replace("[/u]", "");

		if (StringUtils.isEmpty(temp)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<u>");
		sb.append(temp);
		sb.append("</u>");

		return sb.toString();
	}
}
