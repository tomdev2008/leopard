package io.leopard.test.mock.proxy;

import io.leopard.data.rpc.RpcClient;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ RpcClient.class })
public class RpcMethodHandlerAssertWhenImplTest {

	@Test
	public void RpcMethodHandlerAssertWhenImpl() {
		new RpcMethodHandlerAssertWhenImpl(null);
	}

	public static class UserDao {
		public String getNickname() {
			return "ahai";
		}

		public List<String> listUsername() {
			return null;
		}

		public List<String> listUsername2() {
			List<String> list = new ArrayList<String>();
			list.add("a");
			list.add("b");
			return list;
		}

		public Long other() {
			return null;
		}
	}

	@Test
	public void invoke() throws Throwable {

		UserDao userDao = new UserDao();

		{
			RpcMethodHandlerAssertWhenImpl handler = new RpcMethodHandlerAssertWhenImpl(null, "ahai");
			Method method = UserDao.class.getDeclaredMethod("getNickname");
			handler.invoke(userDao, method, method, null);
		}
		{
			RpcMethodHandlerAssertWhenImpl handler = new RpcMethodHandlerAssertWhenImpl(null, null);
			Method method = UserDao.class.getDeclaredMethod("listUsername");
			handler.invoke(userDao, method, method, null);
		}
		{
			List<String> list = new ArrayList<String>();
			list.add("a");
			list.add("b");
			RpcMethodHandlerAssertWhenImpl handler = new RpcMethodHandlerAssertWhenImpl(null, list);
			Method method = UserDao.class.getDeclaredMethod("listUsername2");
			handler.invoke(userDao, method, method, null);
		}
		{
			RpcMethodHandlerAssertWhenImpl handler = new RpcMethodHandlerAssertWhenImpl(null, null);
			Method method = UserDao.class.getDeclaredMethod("other");
			try {
				handler.invoke(userDao, method, method, null);
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	@Test
	public void isSimpleType() {
		RpcMethodHandlerAssertWhenImpl handler = new RpcMethodHandlerAssertWhenImpl(null);
		Assert.assertTrue(handler.isSimpleType(boolean.class));
		Assert.assertTrue(handler.isSimpleType(int.class));
		Assert.assertTrue(handler.isSimpleType(String.class));
		Assert.assertFalse(handler.isSimpleType(Long.class));
	}

}