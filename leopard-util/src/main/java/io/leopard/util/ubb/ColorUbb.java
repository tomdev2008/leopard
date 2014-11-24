package io.leopard.util.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUbb implements Ubb {
	private static final Pattern pattern = Pattern.compile("\\[color=(.*?)\\](.*?)\\[/color\\]");

	@Override
	public String parse(String content) {
		Matcher m = pattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String color = m.group(1);
			String body = m.group(2);

			StringBuilder replacement = new StringBuilder();
			replacement.append("<span style='color:");
			replacement.append(color);
			replacement.append("'>");
			replacement.append(body);
			replacement.append("</span>");
			// String replacement = "<span style='color:";
			m.appendReplacement(sb, replacement.toString());
		}
		m.appendTail(sb);
		// System.out.println("content:" + sb.toString());
		return sb.toString();
	}

}
