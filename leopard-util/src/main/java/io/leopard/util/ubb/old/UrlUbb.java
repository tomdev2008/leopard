package io.leopard.util.ubb.old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UrlUbb {
	protected static final Log logger = LogFactory.getLog(UrlUbb.class);

	private static final Pattern p1 = Pattern.compile("(\\[url\\].*?\\[\\/url\\])");

	public static String getPALink(String content) {
		Matcher m = p1.matcher(content);
		String str = "";
		while (m.find()) {
			String temp = m.group();
			temp = replacePAstr(temp); // 处理[a]字符
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

	private static String replacePAstr(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String temp = content;
		temp = temp.replace("[url]", "");
		temp = temp.replace("[/url]", "");

		if (StringUtils.isEmpty(temp)) {
			return null;
		}

		String str[] = temp.split(",");
		if (str.length != 2) {
			return null;
		}

		if (str[1].indexOf("http://") == -1 && str[1].indexOf("yy://") == -1) {
			str[1] = "http://" + str[1];
		}

		// onclick="AnalyzeClick.OUTLINK_NOTIFY();" // 点击统计

		StringBuilder sb = new StringBuilder();
		if (StringUtils.startsWith(str[1], "http://")) {
			// 注释待发布 伍婷婷
			sb.append("<a target='_blank' onclick='AnalyzeClick.OUTLINK_NOTIFY();' href='");
			sb.append(str[1]);
			sb.append("' >");
			// 注释待发布 －》 伍婷婷
			// sb.append("<a onclick='AnalyzeClick.OUTLINK_NOTIFY();YyGameClient.openTab(\"" + str[0] + "\",\"" + str[1] + "\");return false;' href='javascript:void(0);' >");

			sb.append(str[0]);
			sb.append("</a>");
		}
		else {
			sb.append("<a href=\"javascript:YyGameClient.openYYfjp('");
			sb.append(str[1]);
			sb.append("');\">");
			sb.append(str[0]);
			sb.append("</a>");
		}
		// System.err.println(sb.toString());
		return sb.toString();
	}
}
