package io.leopard.burrow.lang;

import io.leopard.burrow.httpnb.Httpnb;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlUtils {

	private static final Log logger = LogFactory.getLog(XmlUtils.class);

	public static String cdata(String content) {
		return "<![CDATA[" + content + "]]>";
	}

	public static Element toRootElement(String content) {
		InputStream input = new ByteArrayInputStream(content.getBytes());
		Element root = read(input).getDocumentElement();
		return root;
	}

	public static Document read(InputStream input) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(input);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		// SAXReader saxReader = new SAXReader();
		// Document document;
		// try {
		// document = saxReader.read(input);
		// }
		// catch (DocumentException e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// return document;
	}

	public static String escape(String input) {
		if (input == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(input);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			}
			else if (character == '>') {
				result.append("&gt;");
			}
			else if (character == '\"') {
				result.append("&quot;");
			}
			else if (character == '\'') {
				result.append("&#039;");
			}
			else if (character == '&') {
				result.append("&amp;");
			}
			else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	public static Element getRootElement(String url) {
		return getRootElement(url, -1);
	}

	public static Element getRootElement(String url, long timeout) {
		String content = Httpnb.doGet(url, timeout).trim();
		// System.out.println("content:" + content);
		if (StringUtils.isEmpty(content)) {
			logger.warn("result is empty, url:" + url);
			return null;
		}
		return toRootElement(content);
	}

}
