package io.leopard.schema;

import io.leopard.commons.utility.ClassUtil;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class LeopardNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("redis", new RedisBeanDefinitionParser());
		registerBeanDefinitionParser("memdb", new MemdbBeanDefinitionParser());
		registerBeanDefinitionParser("pub", new PubBeanDefinitionParser());
		registerBeanDefinitionParser("jdbc", new JdbcBeanDefinitionParser());
		registerBeanDefinitionParser("memcache", new MemcacheBeanDefinitionParser());

		registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
		registerBeanDefinitionParser("timer-scan", new TimerScanBeanDefinitionParser());
		// registerBeanDefinitionParser("connection-limit", new ConnectionLimitBeanDefinitionParser());
		registerBeanDefinitionParser("mysql-dsn", new MysqlDsnBeanDefinitionParser());
		registerBeanDefinitionParser("redis-dsn", new RedisDsnBeanDefinitionParser());
		registerBeanDefinitionParser("mongo-dsn", new MongoDsnBeanDefinitionParser());
		registerBeanDefinitionParser("dfs-dsn", new DfsDsnBeanDefinitionParser());
		registerBeanDefinitionParser("queue-dsn", new QueueDsnBeanDefinitionParser());
		registerBeanDefinitionParser("memcache-dsn", new MemcacheDsnBeanDefinitionParser());
		registerBeanDefinitionParser("counter", new CounterBeanDefinitionParser());

		registerBeanDefinitionParser("signature", new SignatureBeanDefinitionParser());
		// registerBeanDefinitionParser("env", new EnvBeanDefinitionParser());

		registerBeanDefinitionParser("tx", new TxBeanDefinitionParser());

		registerParser("connection-limit", "io.leopard.schema.ConnectionLimitBeanDefinitionParser");
		registerParser("config", "io.leopard.schema.ConfigBeanDefinitionParser");
		registerParser("performance", "io.leopard.schema.PerformanceBeanDefinitionParser");
		registerParser("monitor", "io.leopard.schema.MonitorBeanDefinitionParser");
		registerParser("server", "io.leopard.schema.ServerBeanDefinitionParser");
		registerParser("errorpage", "io.leopard.schema.ErrorpageBeanDefinitionParser");
	}

	@SuppressWarnings("unchecked")
	protected void registerParser(String elementName, String className) {
		Class<BeanDefinitionParser> clazz;
		try {
			clazz = (Class<BeanDefinitionParser>) Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			return;
		}
		// try {
		BeanDefinitionParser parser = ClassUtil.newInstance(clazz);// clazz.newInstance();
		registerBeanDefinitionParser(elementName, parser);
		// }
		// catch (Exception e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// registerBeanDefinitionParser("admin", new
		// AdminBeanDefinitionParser());
	}
}