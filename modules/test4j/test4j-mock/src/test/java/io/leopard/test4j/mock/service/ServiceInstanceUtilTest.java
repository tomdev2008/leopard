package io.leopard.test4j.mock.service;

import io.leopard.core.exception.ClassNotFoundRuntimeException;

import org.junit.Assert;
import org.junit.Test;

public class ServiceInstanceUtilTest {

	@Test
	public void ServiceInstanceUtil() {
		new ServiceInstanceUtil();
	}

	public static class UserServiceImpl {
	}

	@Test
	public void newInstance() {
		ServiceInstanceUtil.newInstance(UserServiceImpl.class);
	}

	@Test
	public void newServiceInstance() {
		try {
			ServiceInstanceUtil.newServiceInstance(UserServiceImpl.class, UserServiceImpl.class);
			Assert.fail("怎么没有抛异常?");
		}
		catch (ClassNotFoundRuntimeException e) {

		}
	}

}