package io.leopard.burrow.lang;

import io.leopard.burrow.httpnb.Httpnb;

import java.io.InputStream;
import java.io.StringReader;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtils {
	
	private static final Log logger = LogFactory.getLog(XmlUtils.class);

	public static String cdata(String content) {
		return "<![CDATA[" + content + "]]>";
	}

	public static Document read(InputStream input) {
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(input);
		}
		catch (DocumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return document;
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

	public static Object toBean(String url, Class<?> c) {
		Element element = getElement(url);
		return toBean(element, c);
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

	public static Element toRootElement(String content) {
		StringReader in = new StringReader(content);
		Document document;
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(in);
			in.close();
		}
		catch (DocumentException e) {
			throw new RuntimeException(e);
		}

		Element root = document.getRootElement();
		return root;
	}

	public static Element getElement(String url) {
		String content = Httpnb.doGet(url).trim();
		// System.out.println("content:" + content);
		if (StringUtils.isEmpty(content)) {
			logger.warn("result is empty, url:" + url);
			return null;
		}
		return parseElement(content);
	}

	public static Element parseElement(String content) {
		StringReader in = new StringReader(content);
		Document document;
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(in);
			in.close();
		}
		catch (DocumentException e) {
			throw new RuntimeException(e);
		}

		Element root = document.getRootElement();
		// System.out.println("root:" + root);
		if (root == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	protected static <T> T toBean(Element root, Class<T> c) {
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		if (list == null) {
			return null;
		}
		// for (Element element : list) {
		// String name = element.getName();
		// System.out.println("name:" + name);
		// }
		return null;
	}

	public static String getText(Element element, String nodeName) {
		Element e = element.element(nodeName);
		return e.getText();
	}

	// public static void main(String[] args) throws IOException {
	// // String url = "http://121.9.221.7:8081/detail_uinfo?uid=50000071";
	// // DetailUserinfo userinfo = XmlUtils.toUserinfo(url);
	// // System.out.println("userinfo:" + userinfo.toString());
	//
	// String content = FileUtils.readFileToString(new File("/tmp.txt"));
	// System.out.println(content);
	// // Element element = null;
	// try {
	// XmlUtils.parseElement(content);
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// }
}
