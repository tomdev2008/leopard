package io.leopard.test4j.jetty;

import org.eclipse.jetty.server.Server;
import org.springframework.core.env.AbstractEnvironment;

/**
 * 适用于servlet3.0+jetty8，本机开发使用。
 */
public class JettyServer {

	public static Server start() throws Exception {
		// if (!(SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_MAC)) {
		// return start(8080);
		// }
		return start(80);
	}

	public static Server start(int port) throws Exception {
		Server server = start("/", port);
		return server;
	}

	public static Server start(String contextPath, int port) throws Exception {
		String className = "io.leopard.test.hosts.HostLeiImpl";

		try {
			Class.forName(className).newInstance();
		}
		catch (Exception e) {
			// System.err.println("init hosts error:" + e.getMessage());
			e.printStackTrace();
		}

		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "jetty");

		Server server = BuildServer.buildNormalServer(port, contextPath);
		server.start();
		return server;
	}

}
