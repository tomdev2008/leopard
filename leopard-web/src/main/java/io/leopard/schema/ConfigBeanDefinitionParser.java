package io.leopard.schema;

import io.leopard.commons.utility.ClassUtil;
import io.leopard.commons.utility.StringUtil;
import io.leopard.schema.config.IConfigBeanDefinitionParser;
import io.leopard.schema.config.PermissionConfigBeanDefinitionParser;
import io.leopard.schema.model.ConfigSchema;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ConfigBeanDefinitionParser implements BeanDefinitionParser {
	protected Log logger = LogFactory.getLog(this.getClass());

	protected static boolean configured = false;

	protected static ConfigSchema config = new ConfigSchema();

	// TODO ahai 为了支持多个<leopard:config/>，目前的实现代码比较复杂，也还存在一些问题
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		if (configured) {
			throw new RuntimeException("<leopard:config/>只允许配置一次.");
		}

		new PermissionConfigBeanDefinitionParser().parse(null, parserContext);

		NamedNodeMap nodeMap = element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			Node node = nodeMap.item(i);
			String name = node.getNodeName();
			String value = node.getNodeValue();
			// System.err.println("config name:" + name + " value:" + value);
			try {
				this.parse(parserContext, name, value);
			} catch (Exception e) {
				System.err.println("error:" + e.getMessage());
				e.printStackTrace();
			}
		}
		configured = true;
		return null;
	}

	protected void parse(ParserContext parserContext, String name, String value) {
		String packageName = IConfigBeanDefinitionParser.class.getPackage().getName();
		String className = packageName + "." + StringUtil.firstCharToUpperCase(name) + "ConfigBeanDefinitionParser";
		@SuppressWarnings("unchecked")
		Class<IConfigBeanDefinitionParser> clazz = (Class<IConfigBeanDefinitionParser>) ClassUtil.forName(className);
		IConfigBeanDefinitionParser parser = ClassUtil.newInstance(clazz);
		parser.parse(value, parserContext);
	}

	// /**
	// * 属性是否用户重新设置？
	// *
	// * @param element
	// * @param name
	// * @return
	// */
	// protected boolean isSpecified(Element element, String name) {
	// if (configured == false) {
	// // 首次加载
	// return true;
	// }
	//
	// Attr attr = element.getAttributeNode(name);
	// if (attr == null) {
	// throw new NullPointerException("属性[" + name + "]不存在.");
	// // return false;
	// }
	// return attr.getSpecified();
	// }

	// protected ConfigSchema toConfigSchema(Element element) {
	//
	// ConfigSchema configSchema = new ConfigSchema();
	// String adminType = element.getAttribute("adminType");
	// String adminFolder = element.getAttribute("adminFolder");
	// String checkAdminAllowIp = element.getAttribute("checkAdminAllowIp");
	// // String csrfLog = element.getAttribute("csrfLog");
	// String adminRole = element.getAttribute("adminRole");
	// String performance = element.getAttribute("performance");
	// String distributedSession = element.getAttribute("distributedSession");
	// String udbJsFolder = element.getAttribute("udbJsFolder");
	// int sessionExpiry = Integer.parseInt(element.getAttribute("sessionExpiry"));
	//
	// String domainWhiteListJdbcRef = element.getAttribute("domainWhiteListJdbcRef");
	// // String permission = element.getAttribute("permission");
	// String csrf = element.getAttribute("csrf");
	// String xss = element.getAttribute("xss");
	//
	// // String nocsrf = element.getAttribute("nocsrf");
	// String convert = element.getAttribute("convert");
	//
	// if (StringUtils.isEmpty(udbJsFolder)) {
	// udbJsFolder = "/js/udbsdk/";
	// }
	//
	// configSchema.setAdminType(adminType);
	// configSchema.setAdminFolder(adminFolder);
	// configSchema.setCheckAdminAllowIp("true".equals(checkAdminAllowIp));
	// // configSchema.setCsrfLog("true".equals(csrfLog));
	// configSchema.setAdminRole(adminRole);
	// configSchema.setPerformance(performance);
	// configSchema.setDistributedSession(distributedSession);
	// configSchema.setSessionExpiry(sessionExpiry);
	// configSchema.setUdbJsFolder(udbJsFolder);
	// // configSchema.setNocsrf(nocsrf);
	// configSchema.setConvert(convert);
	// // configSchema.setPermission(permission);
	// configSchema.setCsrf(csrf);
	// configSchema.setXss(xss);
	// configSchema.setDomainWhiteListJdbcRef(domainWhiteListJdbcRef);
	// return configSchema;
	// }

}