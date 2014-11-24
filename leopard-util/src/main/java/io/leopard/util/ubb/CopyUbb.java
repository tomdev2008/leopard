package io.leopard.util.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CopyUbb implements Ubb {

	private static final Pattern pattern = Pattern.compile("\\[copy\\](.*?)\\[/copy\\]");
	private final String jsClassName;

	public CopyUbb() {
		this("YyGameClient");
	}

	public CopyUbb(String jsClassName) {
		this.jsClassName = jsClassName;
	}

	@Override
	public String parse(String content) {
		Matcher m = pattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String body = m.group(1);
			StringBuilder replacement = new StringBuilder();
			replacement.append("<a href='javascript:void(0);' onclick='" + jsClassName + ".copyCode(this.innerHTML);return false;'>");
			replacement.append(body);
			replacement.append("</a>");
			m.appendReplacement(sb, replacement.toString());
		}
		m.appendTail(sb);
		return sb.toString();
	}

}
