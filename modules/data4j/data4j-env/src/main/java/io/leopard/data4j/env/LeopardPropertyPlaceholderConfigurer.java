package io.leopard.data4j.env;

import java.util.Properties;

public class LeopardPropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	private ResolvePlaceholderLei resolvePlaceholderLei;

	public LeopardPropertyPlaceholderConfigurer() {
		super.setIgnoreResourceNotFound(true);
		super.setOrder(999);
		super.setIgnoreUnresolvablePlaceholders(true);
		super.setSystemPropertiesMode(SYSTEM_PROPERTIES_MODE_FALLBACK);
	}

	public void setPropertyPlaceholderLei(PropertyPlaceholderLei propertyPlaceholderLei) {
		String env = EnvUtil.getEnv();
		super.setLocations(propertyPlaceholderLei.getResources(env));
	}

	public void setResolvePlaceholderLei(ResolvePlaceholderLei resolvePlaceholderLei) {
		this.resolvePlaceholderLei = resolvePlaceholderLei;
	}

	@Override
	// 执行顺序:2
	protected String resolvePlaceholder(String placeholder, Properties props) {
		String value = super.resolvePlaceholder(placeholder, props);
		if (value == null) {
			value = resolvePlaceholderLei.resolvePlaceholder(placeholder, props);
		}
		return value;
	}

}
