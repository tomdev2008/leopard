package io.leopard.test.mock.reflect;

import io.leopard.data4j.cache.api.IGet;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class InterfaceUtilTest {

	public static class UserDaoMysqlImpl implements IGet<String, String> {

		@Override
		public boolean add(String bean) {

			return false;
		}

		@Override
		public String get(String key) {

			return null;
		}

	}

	public static class UserService {
		protected UserDaoMysqlImpl userDao;

	}

	@Test
	public void InterfaceUtil() {
		new InterfaceUtil();
	}

	@Test
	public void getInterfaces() {
		Set<String> set = InterfaceUtil.getInterfaces(UserDaoMysqlImpl.class);
		// System.out.println("set:" + set.toString());
		Assert.assertEquals("[io.leopard.data.api.IAdd, io.leopard.data.api.IGet]", set.toString());
	}

	@Test
	public void getInterfacesAndSelf() {
		Set<String> set = InterfaceUtil.getInterfacesAndSelf(UserDaoMysqlImpl.class);
		Assert.assertEquals("[io.leopard.data.api.IAdd, io.leopard.data.api.IGet, io.leopard.test.mock.reflect.InterfaceUtilTest$UserDaoMysqlImpl]", set.toString());
	}

	@Test
	public void getField() {
		UserService userService = new UserService();
		Field field = InterfaceUtil.getField(userService, UserDaoMysqlImpl.class);
		System.out.println("field:" + field);
		Assert.assertNotNull(field);
	}

	@Test
	public void getFieldValue() {
		UserService userService = new UserService();
		userService.userDao = new UserDaoMysqlImpl();
		UserDaoMysqlImpl userDao = InterfaceUtil.getFieldValue(userService, UserDaoMysqlImpl.class);
		Assert.assertNotNull(userDao);
	}

}