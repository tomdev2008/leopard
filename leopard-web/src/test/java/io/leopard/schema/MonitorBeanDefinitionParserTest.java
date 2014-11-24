package io.leopard.schema;

import io.leopard.monitor.MonitorServiceImpl;
import io.leopard.schema.model.BaseInfo;
import io.leopard.schema.model.MonitorConfig;

import java.lang.reflect.Field;

import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration("/monitor.xml")
public class MonitorBeanDefinitionParserTest extends AbstractJUnit4SpringContextTests {

	@Test
	public void parse() {
		MonitorConfig monitorConfig = MonitorServiceImpl.getMonitorConfig();
		Assert.assertEquals(2.0f, monitorConfig.getBaseInfo().getSystemLoadAverage(), 0);
		Assert.assertEquals(1025, monitorConfig.getBaseInfo().getThreadCount());
		Assert.assertFalse(monitorConfig.getAlarmInfo().isSms());
	}

	@Test
	public void test() {
		Field field = FieldUtils.getField(BaseInfo.class, "threadCount", true);
		Assert.assertNotNull(field);
	}
}