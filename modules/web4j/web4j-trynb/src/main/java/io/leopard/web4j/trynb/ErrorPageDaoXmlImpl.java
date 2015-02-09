package io.leopard.web4j.trynb;

import io.leopard.web4j.trynb.model.ErrorConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ErrorPageDaoXmlImpl implements ErrorPageDao {

	@Override
	public List<ErrorConfig> list() {
		try {
			return this.parse();
		}
		catch (Exception e) {
			throw new RuntimeException("解析trynb.xml出错:" + e.getMessage(), e);
		}
	}

	protected List<ErrorConfig> parse() throws IOException, ParserConfigurationException, SAXException {
		Resource resource = new ClassPathResource("/trynb.xml");
		InputStream input = resource.getInputStream();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(input);
		NodeList nodeList = document.getElementsByTagName("error");
		List<ErrorConfig> list = new ArrayList<ErrorConfig>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			// String content = element.getElementsByTagName("NAME").item(0).getFirstChild().getNodeValue();
			System.out.println("element:" + element.toString());
		}
		input.close();
		return list;
	}
}
