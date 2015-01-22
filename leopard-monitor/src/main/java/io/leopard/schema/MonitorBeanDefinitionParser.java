package io.leopard.schema;

import io.leopard.monitor.MonitorServiceImpl;
import io.leopard.schema.model.AlarmInfo;
import io.leopard.schema.model.MonitorConfig;
import io.leopard.schema.model.Notifier;
import io.leopard.schema.model.RedisInfo;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MonitorBeanDefinitionParser implements BeanDefinitionParser {
	private final BaseInfoBuilder baseInfoBuilder = new BaseInfoBuilder();

	
	
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		boolean enable = Boolean.parseBoolean(element.getAttribute("enable"));

		NodeList nodeList = element.getChildNodes();
		int size = nodeList.getLength();
		MonitorConfig monitorConfig = new MonitorConfig();
		monitorConfig.setEnable(enable);
		for (int i = 0; i < size; i++) {
			Node node = nodeList.item(i);
			this.node(node, monitorConfig);
		}
		monitorConfig.setBaseInfo(baseInfoBuilder.getBaseInfo());
		// Json.printFormat(monitorConfig, "monitorConfig");
		MonitorServiceImpl.setMonitorConfig(monitorConfig);
		return null;
	}

	protected void node(Node node, MonitorConfig monitorConfig) {
		String localName = node.getLocalName();
		if (localName == null) {
			return;
		}
		if ("notifier".equals(localName)) {
			Notifier notifier = this.notifier(node);
			monitorConfig.addNotifier(notifier);
		}
		else if ("redis".equals(localName)) {
			RedisInfo redisInfo = this.redisInfo(node);
			monitorConfig.addRedisInfo(redisInfo);
		}
		else if ("base".equals(localName)) {
			this.baseInfo(node);
		}
		else if ("alarm".equals(localName)) {
			AlarmInfo alarmInfo = this.alarmInfo(node);
			monitorConfig.setAlarmInfo(alarmInfo);
		}
		else {
			throw new IllegalArgumentException("未知监控节点[" + localName + "].");
		}
	}

	protected Notifier notifier(Node node) {
		// <leopard:notifier name="谭海潮" mobile="18666923069" email="tanhaichao@yy.com" />
		NamedNodeMap namedNodeMap = node.getAttributes();
		Notifier notifier = new Notifier();
		notifier.setName(this.getNodeValue(namedNodeMap, "name"));
		notifier.setMobile(this.getNodeValue(namedNodeMap, "mobile"));
		notifier.setEmail(this.getNodeValue(namedNodeMap, "email"));
		return notifier;
	}

	protected RedisInfo redisInfo(Node node) {
		// <leopard:redis-memory server="127.0.0.1:6311" threshold="3G" />
		NamedNodeMap namedNodeMap = node.getAttributes();
		RedisInfo redisMonitorConfig = new RedisInfo();
		redisMonitorConfig.setServer(this.getNodeValue(namedNodeMap, "server"));
		redisMonitorConfig.setRedisRef(this.getNodeValue(namedNodeMap, "redis-ref"));
		redisMonitorConfig.setMaxMemory(this.getNodeValue(namedNodeMap, "maxMemory"));
		return redisMonitorConfig;
	}

	protected AlarmInfo alarmInfo(Node node) {
		// <leopard:alarm sms="true" />
		NamedNodeMap namedNodeMap = node.getAttributes();
		boolean smsEnable = Boolean.parseBoolean(namedNodeMap.getNamedItem("sms").getNodeValue());
		Node windowsNode = namedNodeMap.getNamedItem("windows");

		AlarmInfo alarmInfo = new AlarmInfo();
		alarmInfo.setSms(smsEnable);
		if (windowsNode != null) {
			alarmInfo.setWindows(Boolean.parseBoolean(windowsNode.getNodeValue()));
		}
		return alarmInfo;
	}

	protected void baseInfo(Node node) {
		// <leopard:thread-count threshold="1024" />
		NamedNodeMap namedNodeMap = node.getAttributes();
		this.baseInfoBuilder.setField(namedNodeMap, "threadCount");
		this.baseInfoBuilder.setField(namedNodeMap, "systemLoadAverage");
		this.baseInfoBuilder.setField(namedNodeMap, "usedHeapMemory");
		this.baseInfoBuilder.setField(namedNodeMap, "freeHeapMemory");
		this.baseInfoBuilder.setField(namedNodeMap, "freePhysicalMemory");
	}

	protected String getNodeValue(NamedNodeMap namedNodeMap, String name) {
		Node node = namedNodeMap.getNamedItem(name);
		if (node == null) {
			return null;
		}
		// Attr attr = (Attr) node;
		// namedNodeMap.
		// System.out.println("name:" + name + " type:" + attr.getSpecified());
		return node.getNodeValue();
	}
}
