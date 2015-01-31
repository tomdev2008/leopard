package io.leopard.burrow.lang;

import org.junit.Test;

public class PagingJsonTest {

	@Test
	public void toJson() {
		Paging<String> paging = new PagingImpl<String>(10, true);
		paging.add("A");
		paging.add("B");
		String json = PagingJson.toJson(paging);
		System.out.println(json);

		Paging<String> paging2 = PagingJson.toPagingObject(json, String.class);
		System.out.println(PagingJson.toJson(paging2));

	}

	public static class User {
		private String username;

		public User() {
		}

		public User(String username) {
			this.username = username;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	@Test
	public void toPagingObject() {
		Paging<User> paging = new PagingImpl<User>(10, true);
		paging.add(new User("A"));
		paging.add(new User("B"));
		String json = PagingJson.toJson(paging);
		// System.out.println(json);

		Paging<User> paging2 = PagingJson.toPagingObject(json, User.class);
		for (User user : paging2) {
			System.out.println(Json.toJson(user));
		}
		// System.out.println(PagingJson.toJson(paging2));
		// new Exception().printStackTrace();
	}

}