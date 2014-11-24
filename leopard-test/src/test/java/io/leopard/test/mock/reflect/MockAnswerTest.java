package io.leopard.test.mock.reflect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockAnswerTest {

	@Test
	public void MockAnswer() {
		new MockAnswer();
	}

	@Test
	public void addQuotes() {
		Assert.assertEquals("1", MockAnswer.addQuotes("1", Integer.class));
		Assert.assertEquals("'1'", MockAnswer.addQuotes("'1'", String.class));
		Assert.assertEquals("\"1\"", MockAnswer.addQuotes("\"1\"", String.class));
		Assert.assertEquals("[\"1\",\"2\"]", MockAnswer.addQuotes("1,2", String.class));
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

	public static class UserAnswer {
		public User getUser() {
			return null;
		}

		public Long getMoney() {
			return null;
		}

		public int getMoney2() {
			return 1;
		}

		public Date getPosttime() {
			return null;
		}

		public Integer getPoint() {
			return null;
		}

		public String getNickname() {
			return null;
		}

		public List<User> listUser() {
			return null;
		}
	}

	@Test
	public void getModelAnswer() throws Throwable {
		Answer<Object> answer = MockAnswer.getModelAnswer("{username:hctan}");
		InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

		Method method = UserAnswer.class.getDeclaredMethod("getUser");
		Mockito.doReturn(method).when(invocation).getMethod();
		answer.answer(invocation);

		// Method method = invocation.getMethod();
		// Class<?> clazz = method.getReturnType();

	}

	@Test
	public void getAnswer() throws Throwable {
		{
			Answer<Object> answer = MockAnswer.getAnswer(1);
			InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

			Method method = UserAnswer.class.getDeclaredMethod("getMoney");
			Mockito.doReturn(method).when(invocation).getMethod();
			answer.answer(invocation);
		}
		{
			Answer<Object> answer = MockAnswer.getAnswer(1);
			InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

			Method method = UserAnswer.class.getDeclaredMethod("getPosttime");
			Mockito.doReturn(method).when(invocation).getMethod();
			answer.answer(invocation);
		}
		{
			Answer<Object> answer = MockAnswer.getAnswer(1L);
			InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

			Method method = UserAnswer.class.getDeclaredMethod("getMoney");
			Mockito.doReturn(method).when(invocation).getMethod();
			answer.answer(invocation);
		}
		{
			Answer<Object> answer = MockAnswer.getAnswer(1L);
			InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

			Method method = UserAnswer.class.getDeclaredMethod("getPosttime");
			Mockito.doReturn(method).when(invocation).getMethod();
			answer.answer(invocation);
		}
	}

	@Test
	public void getDefaultAnswer() throws Throwable {
		{
			Answer<Object> answer = MockAnswer.getDefaultAnswer();
			InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

			Method method = UserAnswer.class.getDeclaredMethod("getMoney2");
			Mockito.doReturn(method).when(invocation).getMethod();
			answer.answer(invocation);
		}
		{
			Answer<Object> answer = MockAnswer.getDefaultAnswer();
			InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

			Method method = UserAnswer.class.getDeclaredMethod("getNickname");
			Mockito.doReturn(method).when(invocation).getMethod();
			answer.answer(invocation);
		}
		{
			Answer<Object> answer = MockAnswer.getDefaultAnswer();
			InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

			Method method = UserAnswer.class.getDeclaredMethod("listUser");
			Mockito.doReturn(method).when(invocation).getMethod();
			answer.answer(invocation);
		}
		{
			Answer<Object> answer = MockAnswer.getDefaultAnswer();
			InvocationOnMock invocation = Mockito.mock(InvocationOnMock.class);

			Method method = UserAnswer.class.getDeclaredMethod("getUser");
			Mockito.doReturn(method).when(invocation).getMethod();
			answer.answer(invocation);
		}
	}

}