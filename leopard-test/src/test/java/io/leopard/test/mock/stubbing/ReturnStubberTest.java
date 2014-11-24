package io.leopard.test.mock.stubbing;

import io.leopard.commons.utility.StringUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ StringUtil.class })
public class ReturnStubberTest {

	@Test
	public void ReturnStubber() {
		new ReturnStubber("{}");
	}

	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	@Test
	public void toList() {
		ReturnStubber stubber = new ReturnStubber("{}");
		List<User> userList = stubber.toList(User.class, "[username:hctan1;username:hctan2]");
		Assert.assertEquals(2, userList.size());
	}

	@Test
	public void getMethod() {
		// String className = User.class.getName();
		// PowerMockito.when(MockStatic.getStaticClassName("classSimpleName")).thenReturn(className);
		ReturnStubber stubber = new ReturnStubber("{}");
		Assert.assertNotNull(stubber.getMethod("StringUtil", "uuid"));
		Assert.assertNull(stubber.getMethod("StringUtil", "uuid2"));
	}

	@Test
	public void whenStatic() {
		// Map<String, String> mapping = new HashMap<String, String>();
		// mapping.put("StringUtil", StringUtil.class.getName());
		// MockStatic.setStaticClassName(mapping);

		ReturnStubber stubber = new ReturnStubber("{}");
		stubber.whenStatic("StringUtil.uuid");

		String uuid = StringUtil.uuid();
		System.out.println("uuid:" + uuid);
	}

}