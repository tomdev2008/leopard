package io.leopard.ext.queue;

import io.leopard.burrow.lang.Json;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractQueueTest {

	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	public static class UserQueue extends AbstractQueue<User> {

		@Override
		public int add(User bean) {

			return 0;
		}

		@Override
		public int count() {

			return 0;
		}

		@Override
		public String pop() {
			User user = new User();
			user.setUsername("hctan");
			return Json.toJson(user);
		}

		@Override
		public User execute(String json, User user) {
			return user;
		}

	}

	@Test
	public void execute() {
		UserQueue queue = Mockito.spy(new UserQueue());
		Assert.assertNotNull(queue.execute());

		Mockito.doReturn(null).when(queue).pop();
		Assert.assertNull(queue.execute());
		queue.setSyncExecute(true);
		Assert.assertTrue(queue.isSyncExecute());
	}

}