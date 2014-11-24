package io.leopard.util.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnderLineUbb implements Ubb {

	private static final Pattern pattern = Pattern.compile("\\[u\\](.*?)\\[/u\\]", Pattern.DOTALL);

	@Override
	public String parse(String content) {
		Matcher m = pattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String body = m.group(1);

			StringBuilder replacement = new StringBuilder();
			replacement.append("<u>");
			replacement.append(body);
			replacement.append("</u>");
			// String replacement = "<span style='color:";
			m.appendReplacement(sb, replacement.toString());
		}
		m.appendTail(sb);
		// System.out.println("content:" + sb.toString());
		return sb.toString();
	}

}
