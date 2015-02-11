package io.leopard.test.hosts;

import io.leopard.javahost.JavaHost;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class HostLeiImpl implements HostLei {

	public HostLeiImpl() {
		try {
			this.initHosts();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initHosts() throws IOException {
		Resource resource = this.getResource();
		if (resource == null) {
			return;
		}

		Properties config = PropertiesLoaderUtils.loadProperties(resource);
		Iterator<Entry<Object, Object>> iterator = config.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Object, Object> entry = iterator.next();
			String host = (String) entry.getKey();
			String ip = (String) entry.getValue();
			host = host.trim();
			ip = ip.trim();
			if (isValidIp(ip)) {
				boolean isLocalHost = JavaHost.isLocalHost(host);
				if (!isLocalHost) {
					JavaHost.updateVirtualDns(host, ip);
				}
			}
		}
	}

	@Override
	public Resource getResource() {
		Resource resource = new ClassPathResource("/dev/dns.properties");
		if (!resource.exists()) {
			String message = "host文件[classpath:/dev/dns.properties]不存在.";
			System.out.println(message);
			return null;
		}
		return resource;
	}

	private boolean isValidIp(String ip) {
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

}