package io.leopard.commons.utility;

public class WebServer {

	public static boolean isWebServer() {
		{
			boolean isWebServer = Resin.isResin();
			if (isWebServer) {
				return isWebServer;
			}
		}
		{
			boolean isWebServer = JettyUtil.isJetty();
			if (isWebServer) {
				return isWebServer;
			}
		}
		return false;
	}
}
