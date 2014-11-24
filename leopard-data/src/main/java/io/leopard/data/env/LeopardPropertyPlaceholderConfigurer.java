package io.leopard.data.env;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;

public class LeopardPropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	static {
		Log4jConfigurator.initConfig();
	}

	public LeopardPropertyPlaceholderConfigurer() {
		super.setIgnoreResourceNotFound(true);
		super.setOrder(999);
		super.setIgnoreUnresolvablePlaceholders(true);
		try {
			super.setLocations(this.getLocations());
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		super.setSystemPropertiesMode(SYSTEM_PROPERTIES_MODE_FALLBACK);
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		try {
			super.processProperties(beanFactoryToProcess, props);
		}
		catch (BeanDefinitionStoreException e) {
			// this.log();
			throw e;
		}
		this.processProperties(props);
	}

	protected void processProperties(Properties props) {
		Iterator<Entry<Object, Object>> iterator = props.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Object, Object> entry = iterator.next();
			if (!(entry.getKey() instanceof String)) {
				continue;
			}
			// System.err.println("setProperty2 key:" + entry.getKey() + " value:" + entry.getValue());

			String key = (String) entry.getKey();
			boolean isPropertyKey = isPropertyKey(key);
			// System.err.println("key:" + key + " isPropertyKey:" + isPropertyKey);
			if (isPropertyKey) {
				// 将所有host配置写入property
				String value = (String) entry.getValue();
				if (System.getProperty(key) == null) {
					System.setProperty(key, value);
					String message = "setProperty key:" + entry.getKey() + " value:" + entry.getValue();
					logger.debug(message);
					// System.err.println("message:" + message);
				}
			}
		}
	}

	protected boolean isPropertyKey(String key) {
		boolean isPropertyKey = key.indexOf(".") != -1 || key.startsWith("DW");
		// System.err.println("isPropertyKey:" + isPropertyKey + " key:" + key);
		return isPropertyKey;
	}

	private Resource[] getLocations() throws IOException {
		List<Resource> resourceList = new ArrayList<Resource>();
		{
			Resource appPropertiesResource = AppProperties.getAppPropertiesResource();
			// logger.info("app.properties path:" + appPropertiesResource.toString());
			resourceList.add(appPropertiesResource);
		}
		{
			try {
				Resource dsnResource = DsnResourceUtil.getResource();
				resourceList.add(dsnResource);
			}
			catch (FileNotFoundException e) {
				logger.warn(e.getMessage());
			}
			// catch (Exception e) {
			// logger.warn(e.getMessage());
			// }
		}
		Resource[] resources = new Resource[resourceList.size()];
		return resourceList.toArray(resources);
	}

	@Override
	// 执行顺序:1
	protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
		String result = super.resolvePlaceholder(placeholder, props, systemPropertiesMode);
		// System.err.println("placeholder:" + placeholder + " result:" + result);
		if (result == null) {
			boolean isDomain = isDomain(placeholder);
			if (isDomain) {
				result = placeholder;
			}
			// System.err.println("placeholder:" + result + " isDomain:" + isDomain);
		}
		return result;
	}

	private static final String[] DOMAINS = new String[] { "duowan.com", "yy.com", "kuaikuai.cn", "laopao.com", "leopard.io" };

	protected static boolean isDomain(String placeholder) {
		for (String domain : DOMAINS) {
			if (placeholder.endsWith(domain)) {
				return true;
			}
		}
		return false;
	}

	@Override
	// 执行顺序:2
	protected String resolvePlaceholder(String placeholder, Properties props) {
		// System.err.println("resolvePlaceholder:" + placeholder);
		String value = super.resolvePlaceholder(placeholder, props);
		if (value == null && placeholder.endsWith(".redis")) {
			value = this.resolveRedisDsnPlaceholder(placeholder, props);
		}
		// System.err.println("resolvePlaceholder22:" + placeholder + " value:" + value + " props:" + props.getProperty(placeholder));
		return value;
	}

	/**
	 * 解析redis数据源.
	 */
	protected String resolveRedisDsnPlaceholder(String placeholder, Properties props) {
		String hostPlaceholder = placeholder + ".host";
		String portPlaceholder = placeholder + ".port";
		return props.getProperty(hostPlaceholder) + ":" + props.getProperty(portPlaceholder);
	}
}
