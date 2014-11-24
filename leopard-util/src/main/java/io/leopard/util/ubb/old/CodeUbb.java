package io.leopard.util.ubb.old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CodeUbb {
	protected static final Log logger = LogFactory.getLog(CodeUbb.class);

	private static final Pattern CODE_PATTERN = Pattern.compile("(\\[code\\].*?\\[\\/code\\])");

	public static String getCodeLink(String content) {
		Matcher m = CODE_PATTERN.matcher(content);
		String str = "";
		while (m.find()) {
			String temp = m.group();
			temp = replaceCodestr(temp); // 处理[code]字符
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

	private static String replaceCodestr(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String temp = content;
		temp = temp.replace("[code]", "");
		temp = temp.replace("[/code]", "");

		if (StringUtils.isEmpty(temp)) {
			return null;
		}

		// [d]content[/d]
		StringBuilder sb = new StringBuilder();
		sb.append("<a href='javascript:void(0);' onclick='YyGameClient.copyCode(this.innerHTML);'>");
		sb.append(temp);
		sb.append("</a>");

		return sb.toString();
	}
}
