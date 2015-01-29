package io.leopard.web4j.permission.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 解析父类
 * 
 * @author 阿海
 * 
 */
public class ConfigParent {

	private String content;

	public ConfigParent(String content) {
		this.content = content;
	}

	/**
	 * 解析权限bean文本.
	 * 
	 * @param body
	 * @return
	 */
	protected String parseBean(String body) {
		// System.out.println(body);
		String regex = "parent=\"(.*?)\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(body);
		if (m.find()) {
			String id = m.group(1);
			String parentBody = this.getBody(id);
			// System.out.println(id + ":" + parentBody);

			if (StringUtils.isNotEmpty(parentBody)) {
				body = body.replace("</permission>", parentBody + "\n</permission>");
				// System.out.println(body);
			}

		}

		return body;
	}

	/**
	 * 获取权限节点内容.
	 * 
	 * @param id
	 * @return
	 */
	protected String getBody(String id) {
		String regex = "<permission.*?id=\"" + id + "\".*?>(.*?)</permission>";
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher m = p.matcher(content);
		if (m.find()) {
			String body = m.group(1);
			// System.out.println("parentBody:"+body);
			return body;
		}
		return null;
	}

	/**
	 * 解析出权限节点.
	 * 
	 * @return
	 */
	protected String parse() {
		String regex = "<permission.*?permission>";
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String body = m.group();
			body = this.parseBean(body);

			m.appendReplacement(sb, body);
		}
		m.appendTail(sb);
		// return "";
		return sb.toString();
	}

	/**
	 * 获取文件内容.
	 * 
	 * @return
	 */
	public String getContent() {
		String content = this.parse();
		return content;
	}
}
