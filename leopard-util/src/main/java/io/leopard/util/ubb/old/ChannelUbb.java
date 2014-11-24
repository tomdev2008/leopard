package io.leopard.util.ubb.old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChannelUbb {
	protected static final Log logger = LogFactory.getLog(ChannelUbb.class);

	private static final Pattern p3 = Pattern.compile("(\\[channel\\].*?\\[\\/channel\\])");

	public static String getPCLink(String content) {
		Matcher m = p3.matcher(content);
		String str = "";
		while (m.find()) {
			String temp = m.group();
			temp = replacePCstr(temp); // 处理[c]字符
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

	private static String replacePCstr(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String temp = content;
		temp = temp.replace("[channel]", "");
		temp = temp.replace("[/channel]", "");

		if (StringUtils.isEmpty(temp)) {
			return null;
		}

		String str[] = temp.split(",");
		if (str.length != 2) {
			return null;
		}
		// [c]name,channel[/c] <a
		// href='http://www.yy.com/go.html#channel'>name</a>

		// onclick='AnalyzeClick.CHANNEL_NOTIFY();' // 点击统计

		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"javascript:void(0)\" onclick=\"YyGameClient.openChannel('");
		sb.append(str[1]);
		sb.append("');AnalyzeClick.CHANNEL_NOTIFY();\">");
		sb.append(str[0]);
		sb.append("</a>");

		return sb.toString();
	}
}
