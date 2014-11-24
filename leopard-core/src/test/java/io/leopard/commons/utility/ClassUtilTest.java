package io.leopard.commons.utility;

import io.leopard.core.exception.ClassNotFoundRuntimeException;

import org.junit.Assert;
import org.junit.Test;

public class ClassUtilTest {

	@Test
	public void forName() {
		ClassUtil.forName(String.class.getName());

		try {
			ClassUtil.forName("io.leopard.Abc");
			Assert.fail("怎么没有抛异常?");
		}
		catch (ClassNotFoundRuntimeException e) {

		}
	}

	@Test
	public void ClassUtil() {
		new ClassUtil();
	}

	@Test
	public void exist() {
		Assert.assertFalse(ClassUtil.exist("io.leopard.Abc"));
		Assert.assertTrue(ClassUtil.exist("io.leopard.commons.utility.DateTime"));
	}

}