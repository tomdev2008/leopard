package io.leopard.test.internal;

import io.leopard.commons.utility.ArrayUtil;
import io.leopard.data4j.context.LeopardClassPathXmlApplicationContext;
import io.leopard.test.hosts.DnsConfig;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextLoader;

/**
 * 
 * @author 阿海
 * 
 */
public class TestContextLoader implements ContextLoader {
	/** 第一个入口 */
	private static final String ENTRY_FIRST = "/integrationTest.xml";

	@Override
	public String[] processLocations(Class<?> clazz, String... locations) {
		return locations;
	}

	@Override
	public ApplicationContext loadContext(String... locations) throws Exception {
		DnsConfig.initHosts();
		String[] files = getFiles(locations);
		files = ArrayUtil.insertFirst(files, "/leopard-test/annotation-config.xml");
		return new LeopardClassPathXmlApplicationContext(locations);
	}

	protected String[] getFiles(String... locations) {
		if (locations.length > 0) {
			return locations;
		}

		ClassPathResource resource = new ClassPathResource(ENTRY_FIRST);
		System.err.println("resource.exists():" + resource.exists() + " ENTRY_FIRST:" + ENTRY_FIRST);
		String filename;
		if (resource.exists()) {
			filename = ENTRY_FIRST;
		}
		else {
			filename = this.getModuleConfig();
			System.err.println("filename:" + filename);
		}
		return new String[] { filename };
	}

	private static Set<String> MODULE_SET = new HashSet<String>();
	static {
		MODULE_SET.add("dao");
		MODULE_SET.add("service");
		MODULE_SET.add("web");
	}

	protected String getModuleConfig() {
		ModuleParser moduleParser = new ModuleParser();
		if (!moduleParser.isModule()) {
			// 单模块项目
			return "/leopard-test/applicationContext-default.xml";
		}
		String moduleName = moduleParser.getModuleName();
		// System.out.println("moduleName:" + moduleName);
		if (!MODULE_SET.contains(moduleName)) {
			throw new RuntimeException("未知模块名称[" + moduleName + "]，请配置src/test/resources/integrationTest.xml");
		}
		return "/leopard-test/applicationContext-" + moduleName + ".xml";
	}
}
