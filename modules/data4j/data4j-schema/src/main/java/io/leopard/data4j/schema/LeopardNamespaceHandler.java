package io.leopard.data4j.schema;

import io.leopard.data4j.env.LogConfigLeiImpl;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class LeopardNamespaceHandler extends NamespaceHandlerSupport {

	static {
		new LogConfigLeiImpl().configure();
	}

	@Override
	public void init() {

		registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());

		registerParser("redis", "io.leopard.schema.RedisBeanDefinitionParser");
		registerParser("memdb", "io.leopard.schema.MemdbBeanDefinitionParser");
		registerParser("pub", "io.leopard.schema.PubBeanDefinitionParser");
		registerParser("jdbc", "io.leopard.schema.JdbcBeanDefinitionParser");
		registerParser("memcache", "io.leopard.schema.MemcacheBeanDefinitionParser");
		registerParser("mysql-dsn", "io.leopard.schema.MysqlDsnBeanDefinitionParser");
		registerParser("redis-dsn", "io.leopard.schema.RedisDsnBeanDefinitionParser");
		registerParser("mongo-dsn", "io.leopard.schema.MongoDsnBeanDefinitionParser");
		registerParser("dfs-dsn", "io.leopard.schema.DfsDsnBeanDefinitionParser");
		registerParser("queue-dsn", "io.leopard.schema.QueueDsnBeanDefinitionParser");
		registerParser("memcache-dsn", "io.leopard.schema.MemcacheDsnBeanDefinitionParser");
		registerParser("counter", "io.leopard.schema.CounterBeanDefinitionParser");
		registerParser("signature", "io.leopard.schema.SignatureBeanDefinitionParser");
		registerParser("tx", "io.leopard.schema.TxBeanDefinitionParser");

		// registerParser("timer-scan", "io.leopard.schema.TimerScanBeanDefinitionParser");
		registerParser("connection-limit", "io.leopard.schema.ConnectionLimitBeanDefinitionParser");
		registerParser("config", "io.leopard.schema.ConfigBeanDefinitionParser");
		// registerParser("performance", "io.leopard.schema.PerformanceBeanDefinitionParser");
		// registerParser("server", "io.leopard.schema.ServerBeanDefinitionParser");
	}

	protected void registerParser(String elementName, String className) {
		BeanDefinitionParser parser;
		try {
			parser = (BeanDefinitionParser) Class.forName(className).newInstance();
		}
		catch (Exception e) {
			// throw new RuntimeException(e.getMessage(), e);
			return;
		}
		registerBeanDefinitionParser(elementName, parser);
	}

	// registerBeanDefinitionParser("memdb", new MemdbBeanDefinitionParser());
	// registerBeanDefinitionParser("pub", new PubBeanDefinitionParser());
	// registerBeanDefinitionParser("jdbc", new JdbcBeanDefinitionParser());
	// registerBeanDefinitionParser("memcache", new MemcacheBeanDefinitionParser());
	//
	// registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
	// registerBeanDefinitionParser("timer-scan", new TimerScanBeanDefinitionParser());
	// // registerBeanDefinitionParser("connection-limit", new
	// // ConnectionLimitBeanDefinitionParser());
	// registerBeanDefinitionParser("mysql-dsn", new MysqlDsnBeanDefinitionParser());
	// registerBeanDefinitionParser("redis-dsn", new RedisDsnBeanDefinitionParser());
	// registerBeanDefinitionParser("mongo-dsn", new MongoDsnBeanDefinitionParser());
	// registerBeanDefinitionParser("dfs-dsn", new DfsDsnBeanDefinitionParser());
	// registerBeanDefinitionParser("queue-dsn", new QueueDsnBeanDefinitionParser());
	// registerBeanDefinitionParser("memcache-dsn", new MemcacheDsnBeanDefinitionParser());
	// registerBeanDefinitionParser("counter", new CounterBeanDefinitionParser());
	//
	// registerBeanDefinitionParser("signature", new SignatureBeanDefinitionParser());
	// // registerBeanDefinitionParser("env", new EnvBeanDefinitionParser());
	//
	// registerBeanDefinitionParser("tx", new TxBeanDefinitionParser());
	//
	// registerParser("connection-limit", "io.leopard.schema.ConnectionLimitBeanDefinitionParser");
	// registerParser("config", "io.leopard.schema.ConfigBeanDefinitionParser");
	// registerParser("performance", "io.leopard.schema.PerformanceBeanDefinitionParser");
	// registerParser("monitor", "io.leopard.schema.MonitorBeanDefinitionParser");
	// registerParser("server", "io.leopard.schema.ServerBeanDefinitionParser");
	// registerParser("errorpage", "io.leopard.schema.ErrorpageBeanDefinitionParser");
	// }
	// @Override
	// public BeanDefinition parse(Element element, ParserContext parserContext) {
	// String localName = parserContext.getDelegate().getLocalName(element);
	// String className = element.getAttribute("clazz");
	//
	// if (StringUtils.isEmpty(className)) {
	// System.err.println("<leopard:" + localName + "/>" + "未定义BeanDefinitionParser.");
	// return null;
	// }
	// BeanDefinitionParser parser = loadBeanDefinitionParser(className);
	// if (parser == null) {
	// parserContext.getReaderContext().fatal("Cannot locate BeanDefinitionParser for element [" + localName + "]", element);
	// }
	// return parser.parse(element, parserContext);
	// }
	//
	// @SuppressWarnings("unchecked")
	// protected BeanDefinitionParser loadBeanDefinitionParser(String className) {
	// Class<BeanDefinitionParser> clazz;
	// try {
	// clazz = (Class<BeanDefinitionParser>) Class.forName(className);
	// }
	// catch (ClassNotFoundException e) {
	// throw new RuntimeException(e);
	// }
	// BeanDefinitionParser parser;
	// try {
	// parser = clazz.newInstance();
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// return parser;
	// // registerBeanDefinitionParser(elementName, parser);
	// }

}