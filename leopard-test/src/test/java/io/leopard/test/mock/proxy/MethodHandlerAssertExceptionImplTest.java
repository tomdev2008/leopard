package io.leopard.test.mock.proxy;

import io.leopard.test.mock.MockTests;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class MethodHandlerAssertExceptionImplTest extends MockTests {

	protected MethodHandlerAssertExceptionImpl methodHandlerAssertExceptionImpl = new MethodHandlerAssertExceptionImpl(null, null, null);

	@Test
	public void getDefaultMethodValue() {
		Assert.assertEquals(0, methodHandlerAssertExceptionImpl.getDefaultMethodValue(int.class));
		Assert.assertEquals(0L, methodHandlerAssertExceptionImpl.getDefaultMethodValue(long.class));
		Assert.assertEquals(0F, methodHandlerAssertExceptionImpl.getDefaultMethodValue(Float.class));
		Assert.assertEquals(0D, methodHandlerAssertExceptionImpl.getDefaultMethodValue(Double.class));
		Assert.assertEquals(false, methodHandlerAssertExceptionImpl.getDefaultMethodValue(Boolean.class));
		Assert.assertNull(methodHandlerAssertExceptionImpl.getDefaultMethodValue(Date.class));
	}

}