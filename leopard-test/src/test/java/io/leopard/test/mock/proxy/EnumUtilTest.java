package io.leopard.test.mock.proxy;

import io.leopard.test.mock.reflect.EnumUtil;

import org.junit.Assert;
import org.junit.Test;

public class EnumUtilTest {

	public static enum Type {
		DEFAULT;
		public int toIntValue() {
			return 1;
		}
	}

	public static enum Type2 {
		DEFAULT;
		public int getValue() {
			return 1;
		}
	}

	public static enum Type3 {
		// DEFAULT;
	}

	@Test
	public void EnumUtil() {
		new EnumUtil();
	}

	@Test
	public void getId() {
		Assert.assertEquals(1, (int) (Integer) EnumUtil.getId(Type.DEFAULT));
		Assert.assertEquals(1, (int) (Integer) EnumUtil.getId(Type2.DEFAULT));
	}

	@Test
	public void getFirstElement() {
		Assert.assertEquals(1, ((Type) EnumUtil.getFirstElement(Type.class)).toIntValue());
		Assert.assertNull(EnumUtil.getFirstElement(Type3.class));
	}

}