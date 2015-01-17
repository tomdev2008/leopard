package io.leopard.data.env;

import io.leopard.data4j.env.EnvLeiImpl;
import io.leopard.data4j.env.EnvUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * app.properties操作类.
 * 
 * @author 谭海潮
 * 
 */
public class AppProperties {

	public enum ConfigFile {
		APP_PROPERTIES("app.properties"), ADMIN_PROPERTIES("admin.properties");

		private final String name;

		private ConfigFile(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static ConfigFile toEnum(String name) {
			if (APP_PROPERTIES.getName().equals(name)) {
				return APP_PROPERTIES;
			}
			else if (ADMIN_PROPERTIES.getName().equals(name)) {
				return ADMIN_PROPERTIES;
			}
			else {
				throw new IllegalArgumentException("未知配置文件[" + name + "].");
			}
		}
	}

	private static ConfigFile currentConfigFile = ConfigFile.APP_PROPERTIES;

	public static ConfigFile getCurrentConfigFile() {
		return currentConfigFile;
	}

	public static void setCurrentConfigFile(ConfigFile currentConfigFile) {
		AppProperties.currentConfigFile = currentConfigFile;
	}

	// /**
	// * 是否开发环境.
	// *
	// * @return
	// */
	// public static boolean isDevEnv() {
	// return EnvLeiImpl.getInstance().isDevEnv();
	// }

	public static String getProjectName() {
		Properties config;
		try {
			Resource resource = getAppPropertiesResource();
			config = PropertiesLoaderUtils.loadProperties(resource);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		String projectName = (String) config.get("DWPROJECTNO");
		projectName = StringUtils.trim(projectName);
		// System.out.println("env projectName:" + projectName);
		return projectName;
	}

	public static Resource getAppPropertiesResource() throws IOException {
		return getResource(currentConfigFile.getName());
	}

	public static Resource getResource(String filename) throws IOException {
		String env = EnvUtil.getEnv();
		{
			ClassPathResource resource = new ClassPathResource("/" + env + "/" + filename);
			if (resource.exists()) {
				return resource;
			}
		}
		String rootDir = EnvLeiImpl.getInstance().getRootDir();
		String configDir = rootDir + "/config/" + env;
		String appPropertiesFilename = configDir + "/" + filename;
		// System.err.println("env configDir:" + appPropertiesFilename);
		// System.out.println("env configDir2:" + appPropertiesFilename);
		File file = new File(appPropertiesFilename);
		if (!file.exists()) {
			throw new FileNotFoundException("文件[" + appPropertiesFilename + "]不存在.");
		}
		return new InputStreamResource(FileUtils.openInputStream(file));
	}

	// public static String getEnv() {
	// String env = EnvLeiImpl.getInstance().getEnv();
	// if (StringUtils.isEmpty(env)) {
	// throw new RuntimeException("获取不到当前环境(DWENV).");
	// }
	// return env;
	// }

	public static String getRootDir() {
		String rootDir = EnvLeiImpl.getInstance().getRootDir();
		return rootDir;
	}
}
