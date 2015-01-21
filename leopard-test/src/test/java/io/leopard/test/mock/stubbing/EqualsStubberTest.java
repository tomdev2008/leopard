package io.leopard.test.mock.stubbing;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.burrow.util.ListUtil;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.reflect.MethodInfo;
import io.leopard.test.mock.reflect.MethodUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ Mock.class, FieldUtil.class, MethodUtil.class })
public class EqualsStubberTest {

	protected EqualsStubber stubber = Mockito.spy(new EqualsStubber("service.list", 1));

	@Test
	public void verify() {
		Mockito.reset(stubber);
		Mockito.doNothing().when(stubber).verify(null);
		PowerMockito.when(Mock.getCurrentMock()).thenReturn(null);
		stubber.verify();
		Mockito.verify(stubber).verify(null);
	}

	@Test
	public void equals2() {
		Mockito.doNothing().when(stubber).verify();
		stubber.equals("a", "a");
		stubber.equals(2, ListUtil.makeList("a,b"));
	}

	@Test
	public void getDaoMock() {
		{
			MethodInfo methodInfo = new MethodInfo();
			methodInfo.setFieldName(null);
			methodInfo.setMethodName("list");
			methodInfo.setParamCount(-1);
			EqualsStubber stubber = Mockito.spy(new EqualsStubber("list", 1));
			PowerMockito.when(MethodUtil.parseMethodInfo("list")).thenReturn(methodInfo);
			Assert.assertEquals("mock", stubber.getDaoMock("mock"));
		}
		{
			MethodInfo methodInfo = new MethodInfo();
			methodInfo.setFieldName("service");
			methodInfo.setMethodName("list");
			methodInfo.setParamCount(-1);
			EqualsStubber stubber = Mockito.spy(new EqualsStubber("service.list", 1));
			PowerMockito.when(MethodUtil.parseMethodInfo("service.list")).thenReturn(methodInfo);
			PowerMockito.when(FieldUtil.getFieldValue(null, (String) null)).thenReturn(null);
			Assert.assertNull(stubber.getDaoMock("mock"));
		}
	}

	@Test
	public void getMethod() {
		MethodInfo methodInfo = new MethodInfo();
		methodInfo.setFieldName("service");
		methodInfo.setMethodName("list");
		methodInfo.setParamCount(-1);
		EqualsStubber stubber = Mockito.spy(new EqualsStubber("service.list", 1));
		// MethodInfo methodInfo = MethodUtil.parseMethodInfo(serviceAndMethodName);

		PowerMockito.when(MethodUtil.parseMethodInfo("service.list")).thenReturn(methodInfo);
		PowerMockito.when(MethodUtil.getMethod(String.class, "list", -1)).thenReturn(null);
		Assert.assertNull(stubber.getMethod("mock"));
	}

	public static class UserServiceImpl {

		public String getTitle(int id) {
			return "ok:" + id;
		}
	}

	@Test
	public void when() {
		EqualsStubber stubber = Mockito.spy(new EqualsStubber("service.list", 1));

		UserServiceImpl userService = new UserServiceImpl();
		Mockito.doNothing().when(stubber).verify(userService);
		Assert.assertEquals("ok:1", stubber.when(userService).getTitle(1));

	}

	@Test
	public void EqualsStubber() {
		// AUTO
	}
}