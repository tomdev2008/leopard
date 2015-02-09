package io.leopard.monitor;

import io.leopard.burrow.lang.Json;
import io.leopard.schema.model.MonitorConfig;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class MonitorDaoXmlImplTest {

	private MonitorDaoXmlImpl errorPageDaoXmlImpl = new MonitorDaoXmlImpl();

	@Test
	public void parse() throws IOException, ParserConfigurationException, SAXException {
		InputStream input = this.getClass().getResourceAsStream("monitor.xml");
		MonitorConfig config = errorPageDaoXmlImpl.parse(input);
		Json.printFormat(config, "config");
	}

}