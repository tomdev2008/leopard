package io.leopard.test.mock.internal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class AssertMapTest {
	public static class User {
		private String username;

		public User() {

		}

		public User(String username) {
			setUsername(username);
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	@Test
	public void map() {
		{
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("key1", "value1");
			map.put("key2", "value2");
			AssertMap.map("[key1:value1;key2:value2]", map);
		}
		{
			Map<String, User> map = new LinkedHashMap<String, User>();
			map.put("key1", new User("a"));
			map.put("key2", new User("b"));
			AssertMap.map("[username:a;username:b]", map);
		}

	}

	@Test
	public void AssertMap() {
		new AssertMap();
	}

	@Test
	public void bean() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		AssertMap.bean("[key:key1]", map);
	}

}