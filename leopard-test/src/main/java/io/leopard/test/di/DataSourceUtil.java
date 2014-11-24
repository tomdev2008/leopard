package io.leopard.test.di;

import io.leopard.core.exception.IORuntimeException;
import io.leopard.data.env.AppProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class DataSourceUtil {
	private static Properties appConfig = null;
	private static String daoConfigContent;

	static {
		daoConfigContent = getDaoConfig();
		appConfig = getAppConfig();
		// System.out.println("keySet:" + StringUtils.join(appConfig.keySet(), ","));
	}

	protected static String getDaoConfig() {
		try {
			ClassPathResource resource = new ClassPathResource("/applicationContext-dao.xml");
			InputStream input = resource.getInputStream();
			String content = IOUtils.toString(input);
			input.close();
			return content;

		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

	private static Properties getAppConfig() {
		try {
			Resource resource = AppProperties.getResource("dsn.properties");
			return PropertiesLoaderUtils.loadProperties(resource);
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

	public static Map<String, String> get(String beanId) {
		// <leopard:mysql-dsn id="jdbc" name="olla" />

		String regex = "<leopard:[a-zA-Z0-9\" =\\-]+ id=\"(.*?)\"[a-zA-Z0-9\" =]+/>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(daoConfigContent);
		while (m.find()) {
			String id = m.group(1);
			if (!beanId.equals(id)) {
				continue;
			}
			String line = m.group();
			// System.out.println("line:" + line);
			return parseLine(line);
		}
		throw new RuntimeException("找不到数据源[" + beanId + "].");
	}

	public static Map<String, String> getJdbc(String beanId) {
		String name = get(beanId).get("name");
		Map<String, String> map = new HashMap<String, String>();
		map.put("driverClassName", appConfig.getProperty(name + ".jdbc.driverClassName"));
		map.put("url", appConfig.getProperty(name + ".jdbc.url"));
		map.put("username", appConfig.getProperty(name + ".jdbc.username"));
		map.put("password", appConfig.getProperty(name + ".jdbc.password"));
		return map;
	}

	public static Map<String, String> parseLine(String line) {
		String regex = "([a-zA-Z]+)=\"([a-zA-Z0-9]+)\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		Map<String, String> map = new HashMap<String, String>();
		while (m.find()) {
			String key = m.group(1);
			String value = m.group(2);
			map.put(key, value);
		}
		return map;
	}

	public static String getRedisServer(String beanId) {
		String name = get(beanId).get("name");
		String server = appConfig.getProperty(name + ".redis");
		return server;
	}

}
