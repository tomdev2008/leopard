package io.leopard.data.env;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ProjectInfoDaoImpl implements ProjectInfoDao {

	@Override
	public Properties load() throws IOException {
		Resource resource = new ClassPathResource("/leopard.properties");
		Properties config = PropertiesLoaderUtils.loadProperties(resource);
		return config;

	}

	@Override
	public String getCode() {
		Properties config;
		try {
			config = this.load();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		String code = config.getProperty("project.code");
		code = StringUtils.trim(code);
		return code;
	}

}
