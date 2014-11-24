package io.leopard.monitor.log;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Assert;
import org.junit.Test;

public class LogInfoTest {

	@Test
	public void LogInfo() {
		BeanAssert.assertModel(LogInfo.class);

		LogInfo logInfo = new LogInfo("filename", "leveL");
		Assert.assertEquals("filename", logInfo.getFilename());
	}

}