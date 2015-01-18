package io.leopard.test.hosts;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

//http://zhwj184.iteye.com/blog/1202322
public class HostsUtil {

	public static boolean isValidIp(String ip) {
		if (ip == null || ip.length() == 0) {
			return false;
		}
		if (ip.length() < 7) {
			return false;
		}
		String regex = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ip);
		return m.find();
	}

	/**
	 * 是否本地设置的host?
	 * 
	 * @param host
	 * @return
	 */
	public static boolean isLocalHost(String host) {
		return LocalHost.isLocalHost(host);
	}

	public static boolean initHosts() {
		Resource resource = new ClassPathResource("/dev/dns.properties");
		if (!resource.exists()) {
			String message = "host文件[classpath:/dev/dns.properties]不存在.";
			System.out.println(message);
			return false;
		}
		try {
			Properties config = PropertiesLoaderUtils.loadProperties(resource);
			initHosts(config);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return true;
	}

	public static void initHosts(Properties prop) {
		Iterator<Entry<Object, Object>> iterator = prop.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Object, Object> entry = iterator.next();
			String host = (String) entry.getKey();
			String ip = (String) entry.getValue();
			host = host.trim();
			ip = ip.trim();
			boolean isLocalHost = isLocalHost(host);
			if (isValidIp(ip)) {
				// TODO ahai 需要设置Property吗？
				if (isLocalHost) {
					System.setProperty(host, host);
				}
				else {
					InetAddressUtil.putCache(host, ip);
					System.setProperty(host, ip);
				}
			}
		}
	}
}
