package io.leopard.burrow.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * [b]粗体[/b]
 * 
 * @author 阿海.
 * 
 */
public class StrongUbb implements Ubb {
	private static final Pattern pattern = Pattern.compile("\\[b\\](.*?)\\[/b\\]", Pattern.DOTALL);

	@Override
	public String parse(String content) {
		Matcher m = pattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String body = m.group(1);
			StringBuilder replacement = new StringBuilder();
			replacement.append("<b>");
			replacement.append(body);
			replacement.append("</b>");
			// System.err.println("replacement:" + replacement);
			m.appendReplacement(sb, replacement.toString());
		}
		m.appendTail(sb);
		return sb.toString();
	}

}
