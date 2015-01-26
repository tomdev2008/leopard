package io.leopard.test.internal;

import io.leopard.commons.utility.ArrayUtil;
import io.leopard.data4j.context.LeopardClassPathXmlApplicationContext;
import io.leopard.test.hosts.HostLeiImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
		// HostsUtil.initHosts();
		new HostLeiImpl();

		String[] files = getFiles(locations);
		files = ArrayUtil.insertFirst(files, "/leopard-test/annotation-config.xml");
		return new LeopardClassPathXmlApplicationContext(files);
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
			filename = this.getModuleApplicationContextPath();
			System.err.println("filename:" + filename);
		}
		return new String[] { filename };
	}

	private String[] defaultModules = { "dao", "service", "web" };

	protected String getModuleApplicationContextPath() {
		ModuleParserLei moduleParserLei = new ModuleParserLeiImpl();
		if (moduleParserLei.isSingleModule()) {
			return "/leopard-test/applicationContext-web.xml";
		}
		{
			String moduleName = moduleParserLei.getModuleName();
			String path = "/leopard-test/applicationContext-" + moduleName + ".xml";
			Resource resource = new ClassPathResource(path);
			if (resource.exists()) {
				return path;
			}
		}

		for (String moduleName2 : defaultModules) {
			Resource resource = new ClassPathResource("/applicationContext-" + moduleName2 + ".xml");
			if (resource.exists()) {
				return "/leopard-test/applicationContext-" + moduleName2 + ".xml";
			}
		}

		return "/leopard-test/applicationContext-default.xml";
	}
}
