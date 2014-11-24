package io.leopard.monitor.log;

import io.leopard.data.env.Log4jConfigurator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log4jService {

	public static String getLevel(String name) {
		String content = Log4jConfigurator.getContent();
		return getLevel(name, content);
	}

	public static String getLevel(String name, String content) {
		Pattern p = Pattern.compile("log4j.appender." + name + ".Threshold=([a-zA-Z]+)");
		Matcher m = p.matcher(content);
		String level = "FATAL";
		while (m.find()) {
			level = m.group(1);
		}
		return level;
	}

	public static String updateLevel(String name, String level, String content) {
		Pattern p = Pattern.compile("log4j.appender." + name + ".Threshold=([a-zA-Z]+)");
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String replacement = "log4j.appender." + name + ".Threshold=" + level;
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String update(String jvmLog, String allLog, String warnLog, String errorLog) {
		String content = Log4jConfigurator.getContent();
		return update(jvmLog, allLog, warnLog, errorLog, content);
	}

	protected static String update(String jvmLog, String allLog, String warnLog, String errorLog, String content) {
		content = updateLevel("stdout", jvmLog, content);
		content = updateLevel("A1", allLog, content);
		content = updateLevel("W1", warnLog, content);
		content = updateLevel("E1", errorLog, content);

		Log4jConfigurator.configure(content);
		return content;
	}

}