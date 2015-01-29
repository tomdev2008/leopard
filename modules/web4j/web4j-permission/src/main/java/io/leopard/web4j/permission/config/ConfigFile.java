package io.leopard.web4j.permission.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

public class ConfigFile {
	private Log logger = LogFactory.getLog(this.getClass());

	private static final String DEFAULT_CONFIGURATION_FILE = "/permission.xml";

	/**
	 * 获取配置文件的内容.
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getContent() throws IOException {
		String content = this.getContent(DEFAULT_CONFIGURATION_FILE);
		content = new ConfigParent(content).getContent();
		// System.out.println("content:" + content);
		return content;
	}

	/**
	 * 获取配置文件的内容.
	 * 
	 * @param filename
	 *            配置文件名
	 * @return
	 * @throws IOException
	 */
	protected String getContent(String filename) throws IOException {
		InputStream input = new ClassPathResource(filename).getInputStream();
		String content;
		try {
			content = IOUtils.toString(input);
		}
		finally {
			IOUtils.closeQuietly(input);
		}

		// String content = IOUtil.readByClassPath(filename);
		// InputStream input = Config.class.getResourceAsStream(filename);
		// if (input == null) {
		// String dir = Config.class.getResource("/").toString();
		// logger.error("file not found:" + filename + " dir:" + dir);
		// return null;
		// }
		// String content = IOUtils.toString(input);

		content = content.replaceAll("<!--.*?-->", "");

		String regex = "<import resource=\"(.*?)\" />";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String resource = m.group(1);
			String subContent = this.getSubContent(resource);
			if (subContent == null) {
				logger.error("file not found:" + resource);
				subContent = "";
			}
			m.appendReplacement(sb, subContent);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 获取import节点对应文件的内容.
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	protected String getSubContent(String filename) throws IOException {
		String content = this.getContent(filename);
		if (content == null) {
			return null;
		}
		content = content.replaceAll("<\\?xml.*?\\?>", "");
		content = content.replace("<root>", "");
		content = content.replace("</root>", "");
		return content;
	}

	// private void start() throws IOException {
	// String content = this.getContent();
	// System.out.println(content);
	//
	// }
	//
	// public static void main(String[] args) {
	// try {
	// new ConfigFile().start();
	// }
	// catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

}
