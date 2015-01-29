package io.leopard.web4j.permission.config;

import io.leopard.burrow.lang.XmlUtils;
import io.leopard.burrow.util.ListUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Element;

public class Config {
	private Log logger = LogFactory.getLog(this.getClass());

	public Config() {

	}

	/**
	 * 获取权限文件根节点.
	 * 
	 * @return
	 * @throws IOException
	 */
	public Element getRootElement() throws IOException {
		String content = new ConfigFile().getContent();
		return XmlUtils.toRootElement(content);
	}

	protected List<Bean> beanList = null;
	private List<Bean> beanList2 = null;// 调整过顺序的

	/**
	 * 获取权限列表.
	 * 
	 * @return
	 */
	public List<Bean> getPermissionList() {
		if (this.beanList2 == null) {
			this.getConfig();
		}
		return this.beanList2;
	}

	/**
	 * 获取权限列表.
	 * 
	 * @return
	 */
	public synchronized List<Bean> getConfig() {
		if (this.beanList != null) {
			return this.beanList;
		}
		this.beanList = new ArrayList<Bean>();

		try {
			Element root = this.getRootElement();
			parseConfig(root);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		this.beanList2 = this.beanList;// 未调整顺序
		return this.beanList;
	}

	/**
	 * 解析节点获取权限列表.
	 * 
	 * @param root
	 * @return
	 */
	public List<Bean> parseConfig(Element root) {
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element element : list) {
			String name = element.getName();
			if ("allow".equalsIgnoreCase(name)) {
				logger.error(element.asXML());
				throw new IllegalArgumentException("allow标签放错位置了.");
			}
			if (!"permission".equalsIgnoreCase(name)) {
				this.parseConfig(element);
				continue;
			}
			this.addConfig(element);
		}
		return null;
	}

	/**
	 * 添加权限到列表.
	 * 
	 * @param element
	 */
	protected void addConfig(Element element) {
		String id = this.getAttribute(element, "id");
		String url = this.getAttribute(element, "url");
		Boolean enable = Boolean.parseBoolean(this.getAttribute(element, "enable"));
		String parent = this.getAttribute(element, "parent");
		// System.out.println("parent:" + parent);
		if (!url.startsWith("/")) {
			logger.error(element.asXML());
			throw new IllegalArgumentException("url必须以/开始.");
		}

		List<Allow> allowList = this.getAllowList(element);
		// System.out.println("url:" + url + " page:" + page + " allowList:" +
		// allowList);

		Bean bean = new Bean();
		bean.setId(id);
		bean.setUrl(url);
		bean.setParent(parent);
		bean.setEnable(enable);
		bean.setAllowList(allowList);
		if (allowList != null) {
			for (Allow allow : allowList) {
				logger.info("add permission, id:" + id + " url:" + url + " allowIp:" + allow.getIp());
			}
		}
		this.beanList.add(bean);
	}

	/**
	 * 获取权限节点对应属性的值.
	 * 
	 * @param element
	 *            节点
	 * @param name
	 *            属性名
	 * @return 属性值
	 */
	protected String getAttribute(Element element, String name) {
		Attribute attribute = element.attribute(name);
		if (attribute == null) {
			return null;
			// logger.error(element.asXML());
			// throw new IllegalArgumentException("属性[" + name + "]不能为空.");
		}
		else {
			return attribute.getValue();
		}
	}

	/**
	 * 获取权限的白名单列表.
	 * 
	 * @param root
	 * @return
	 */
	protected List<Allow> getAllowList(Element root) {
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		if (ListUtil.isEmpty(list)) {
			return null;
		}
		List<Allow> allowList = new ArrayList<Allow>();
		for (Element element : list) {
			String name = element.getName();
			// System.out.println("name:" + name);
			if (!"allow".equalsIgnoreCase(name)) {
				logger.error(element.asXML());
				throw new IllegalArgumentException("permission标签内只能使用allow标签.");
			}
			String ip = this.getAttribute(element, "ip");
			String user = this.getAttribute(element, "user");
			String password = this.getAttribute(element, "password");
			String log = this.getAttribute(element, "log");

			Allow allow = new Allow();
			allow.setIp(ip);
			allow.setUser(user);
			allow.setPassword(password);
			// if ("false".equalsIgnoreCase(log)) {
			// allow.setLog(false);
			// }
			// else {
			allow.setLog(isLog(log));
			// }
			allowList.add(allow);
		}
		return allowList;
	}

	protected boolean isLog(String log) {
		return !"false".equalsIgnoreCase(log);
	}
}
