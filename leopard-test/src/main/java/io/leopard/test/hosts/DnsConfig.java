package io.leopard.test.hosts;

import io.leopard.data.env.EnvDaoImpl;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class DnsConfig {
	public static boolean initHosts() {
		try {
			Resource resource = getDnsPropertiesResource();
			if (resource == null) {
				return false;
			}

			Properties config = PropertiesLoaderUtils.loadProperties(resource);
			HostsUtil.initHosts(config);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return true;
	}

	public static Resource getDnsPropertiesResource() throws IOException {
		String rootDir = EnvDaoImpl.getInstance().getRootDir();
		String hostPropertiesFilename = rootDir + "/config/dev/dns.properties";

		File file = new File(hostPropertiesFilename);
		if (!file.exists()) {
			String message = "host文件[" + hostPropertiesFilename + "]不存在.";
			System.out.println(message);
			// throw new FileNotFoundException("host文件[" +
			// hostPropertiesFilename + "]不存在.");
			return null;
		}
		return new InputStreamResource(FileUtils.openInputStream(file));
	}
}
