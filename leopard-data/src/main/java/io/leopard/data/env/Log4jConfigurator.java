package io.leopard.data.env;

import io.leopard.commons.utility.ClassUtil;
import io.leopard.commons.utility.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Log4jConfigurator {

	protected static Resource getResource() {
		String env = AppProperties.getEnv();
		// System.err.println("env:" + env);
		Resource resource = new ClassPathResource("/log4j-" + env + ".properties");
		if (resource.exists()) {
			return resource;
		}
		else {
			return new ClassPathResource("/log4j.properties");
		}
	}

	protected static void initConfig() {
		if (isCustomInited) {
			return;
		}
		try {
			Class.forName("org.apache.log4j.PropertyConfigurator");
		}
		catch (ClassNotFoundException e) {
			// 没用启用log4j配置.
			return;
		}

		Resource resource = getResource();
		if (resource.exists()) {
			try {
				System.out.println("log4j.properties, path-222:" + resource.getURL().toString());
				configure(resource);
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		// try {
		// System.out.println("log4j.properties, path:" + resource.getURL().toString());
		// PropertyConfigurator.configure(resource.getInputStream());
		// }
		// catch (IOException e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }
	}

	protected static boolean isCustomInited = false;// 是否自定义初始化过?

	public static void initLogging(String classpathFilename) {
		isCustomInited = true;
		ClassPathResource resource = new ClassPathResource(classpathFilename);
		configure(resource);
		// System.err.println("resource:" + resource);

	}

	protected static void configure(Resource resource) {
		String content = ResourceUtil.toString(resource);
		try {
			Class.forName("io.leopard.monitor.alarm.DailyAutoRollingFileAppender");
			@SuppressWarnings("unchecked")
			Class<Log4jPropertiesParser> clazz = (Class<Log4jPropertiesParser>) YtestUtil.findClass(Log4jPropertiesParserImpl.class);
			Log4jPropertiesParser parser = ClassUtil.newInstance(clazz);
			content = parser.parse(content);
			// System.out.println("ytest clazz:" + content);
		}
		catch (ClassNotFoundException e) {

		}
		configure(content);
	}

	private static String content = null;

	public static String getContent() {
		return content;
	}

	public static boolean configure(String content) {
		// System.err.println("log4j content:" + content);
		ByteArrayResource resource2 = new ByteArrayResource(content.getBytes(), "log4j.properties");
		try {
			InputStream input = resource2.getInputStream();
			PropertyConfigurator.configure(input);
			input.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		Log4jConfigurator.content = content;
		return true;
	}

}
