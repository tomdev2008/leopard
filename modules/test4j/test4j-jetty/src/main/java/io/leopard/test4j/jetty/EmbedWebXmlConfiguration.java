package io.leopard.test4j.jetty;

import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class EmbedWebXmlConfiguration extends WebXmlConfiguration {
	@Override
	public void preConfigure(WebAppContext context) throws Exception {
		super.preConfigure(context);
	}

	// @Override
	// public void configure(WebAppContext context) throws Exception {
	// super.configure(context);
	// }
}
