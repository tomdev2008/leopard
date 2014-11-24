package io.leopard.util.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 飞机票解析.
 * 
 * @author 阿海
 * 
 */
public class FeijipiaoUbb implements Ubb {
	private final String jsClassName;

	public FeijipiaoUbb() {
		this("YyGameClient");
	}

	public FeijipiaoUbb(String jsClassName) {
		this.jsClassName = jsClassName;
	}

	/**
	 * 格式:[fjp='yy://yxdt-[key=yg0vi8-txjivgameivs10&from=from_web]/']测试地址[/fjp]
	 */
	@Override
	public String parse(String content) {
		// [fjp=http://dddddd]ddddd[/fjp]
		String regex = "\\[fjp=['\"](.*?)['\"]\\](.*?)\\[/fjp\\]";
		Pattern p = Pattern.compile(regex, Pattern.DOTALL);
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String url = m.group(1);
			String title = m.group(2);
			StringBuilder replacement = new StringBuilder();
			replacement.append("<a href=\"javascript:void(0)\" onclick=\"" + jsClassName + ".openFeijipiao('" + url + "');return false;\">");
			replacement.append(title);
			replacement.append("</a>");
			m.appendReplacement(sb, replacement.toString());
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
