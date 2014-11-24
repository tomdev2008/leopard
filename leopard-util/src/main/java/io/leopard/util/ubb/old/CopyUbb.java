package io.leopard.util.ubb.old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CopyUbb {
	protected static final Log logger = LogFactory.getLog(CopyUbb.class);

	private static final Pattern p4 = Pattern.compile("(\\[copy\\].*?\\[\\/copy\\])");

	public static String copy(String content) {
		String result = getPDLink(content);
		String result2 = new io.leopard.util.ubb.CopyUbb().parse(content);

		if (!result.equals(result2)) {
			logger.error("ubb:" + content);
		}
		return result;
	}

	public static String getPDLink(String content) {
		Matcher m = p4.matcher(content);
		String str = "";
		while (m.find()) {
			String temp = m.group();
			temp = replacePDstr(temp); // 处理[d]字符
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

	protected static String replacePDstr(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String temp = content;
		temp = temp.replace("[copy]", "");
		temp = temp.replace("[/copy]", "");

		if (StringUtils.isEmpty(temp)) {
			return null;
		}

		// [d]content[/d]
		StringBuilder sb = new StringBuilder();
		// sb.append("<a href='javascript:void(0);' onclick='YyGameClient.copyCode(this.innerHTML);'>");
		sb.append("<a href='javascript:void(0);' onclick='YyGameClient.copyCode(this.innerHTML);return false;'>");
		sb.append(temp);
		sb.append("</a>");

		return sb.toString();
	}
}
