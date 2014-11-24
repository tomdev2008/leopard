package io.leopard.util.ubb.old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BrUbb {
	protected static final Log logger = LogFactory.getLog(BrUbb.class);

	private static final Pattern c4 = Pattern.compile("(\\[br\\])");

	public static String br(String content) {
		String result = getCBRLink(content);
		String result2 = new io.leopard.util.ubb.BrUbb().parse(content);

		if (!result.equals(result2)) {
			logger.error("ubb:" + content);
		}
		return result;
	}

	public static String getCBRLink(String content) {
		Matcher m = c4.matcher(content);
		String str = "";
		while (m.find()) {
			String temp = m.group();
			temp = replaceCBRstr(temp); // 处理[b]字符
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

	private static String replaceCBRstr(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String temp = content;
		temp = temp.replace("[br]", "<br />");

		return temp;
	}
}
