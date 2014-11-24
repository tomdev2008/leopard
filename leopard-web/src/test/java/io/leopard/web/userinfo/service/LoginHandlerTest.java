package io.leopard.web.userinfo.service;

import io.leopard.test.mock.MockTests;

import org.junit.Assert;
import org.junit.Test;

public class LoginHandlerTest extends MockTests {

	protected ConfigHandler loginHandler = new ConfigHandler();

	@Test
	public void logined() {
		loginHandler.logined(null, null);
	}

	// @Test
	// public void getLoginService() {
	// Assert.assertNull(loginHandler.getLoginService());
	// }

	@Test
	public void isEnableTimeLog() {
		Assert.assertFalse(loginHandler.isEnableTimeLog());
	}

	@Test
	public void getExcludeUris() {
		Assert.assertNull(loginHandler.getExcludeUris());
	}

	@Test
	public void getConnectionLimitIncludeUris() {
		Assert.assertNull(loginHandler.getConnectionLimitIncludeUris());
	}

}