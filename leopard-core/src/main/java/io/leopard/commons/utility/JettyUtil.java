package io.leopard.commons.utility;

import org.springframework.core.env.AbstractEnvironment;

/**
 * Resin相关方法类.
 * <p>
 * 
 * @author 阿海
 * 
 */

public final class JettyUtil {

	/**
	 * 判断是否为Jetty
	 * 
	 * @return boolean
	 */
	public static boolean isJetty() {
		String value = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
		return "jetty".equals(value);
	}

}
