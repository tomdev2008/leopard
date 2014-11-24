package io.leopard.util.ubb.old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StrongUbb {
	protected static final Log logger = LogFactory.getLog(StrongUbb.class);

	private static final Pattern c1 = Pattern.compile("(\\[b\\].*?\\[\\/b\\])");

	public static String strong(String content) {
		String result = getCALink(content);
		String result2 = new io.leopard.util.ubb.StrongUbb().parse(content);
		// System.out.println("result2:" + result2);
		if (!result.equals(result2)) {
			logger.error("ubb:" + content);
			logger.error("ubb result1:" + result);
			logger.error("ubb result2:" + result2);
		}
		return result;
	}

	public static String getCALink(String content) {
		Matcher m = c1.matcher(content);
		String str = "";
		while (m.find()) {
			String temp = m.group();
			temp = replaceCAstr(temp); // 处理[b]字符
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

	private static String replaceCAstr(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String temp = content;
		temp = temp.replace("[b]", "");
		temp = temp.replace("[/b]", "");

		if (StringUtils.isEmpty(temp)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<b>");
		sb.append(temp);
		sb.append("</b>");

		return sb.toString();
	}
}
