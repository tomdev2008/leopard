package io.leopard.core.inum;

import org.junit.Assert;
import org.junit.Test;

public class EnumUtilTest {

	public static enum Type implements Inum {
		DEFAULT(0, "默认"), A(1, "a"), B(2, "b");
		private int key;
		private String desc;

		private Type(int key, String desc) {
			this.key = key;
			this.desc = desc;
		}

		@Override
		public Integer getKey() {
			return this.key;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}

	}

	@Test
	public void toLowerCase() {
		Assert.assertEquals("abc", EnumUtil.toLowerCase("ABC"));
		Assert.assertEquals(1, EnumUtil.toLowerCase(1));
		Assert.assertEquals(1L, EnumUtil.toLowerCase(1L));
	}

	@Test
	public void get() {
		Assert.assertNotNull(EnumUtil.get(1, Type.class));
	}

	@Test
	public void contains() {
		EnumUtil.cache.clear();
		Assert.assertTrue(EnumUtil.contains(0, Type.class));
		Assert.assertTrue(EnumUtil.contains(0, Type.class));
		Assert.assertTrue(EnumUtil.contains(1, Type.class));
		Assert.assertTrue(EnumUtil.contains(2, Type.class));
		Assert.assertFalse(EnumUtil.contains(3, Type.class));
	}

	@Test
	public void toEnum() {
		Assert.assertEquals(new Integer(1), EnumUtil.toEnum(3, Type.class, Type.A).getKey());
		Assert.assertEquals(new Integer(2), EnumUtil.toEnum(2, Type.class, Type.A).getKey());
		Assert.assertNull(EnumUtil.toEnum(3, Type.class, null));
	}

	@Test
	public void toEnum2() {
		Assert.assertEquals(new Integer(2), EnumUtil.toEnum(2, Type.class).getKey());
		try {
			EnumUtil.toEnum(3, Type.class);
			Assert.fail("为什么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void getDesc() {
		Assert.assertEquals("b", EnumUtil.getDesc(2, Type.class));
		Assert.assertNull(EnumUtil.getDesc(3, Type.class));
	}

	@Test
	public void EnumUtil() {
		new EnumUtil();
	}

	@Test
	public void toMap() {
		Assert.assertNotNull(EnumUtil.toMap("key", Type.class));
		Assert.assertNotNull(EnumUtil.toMap("key", Type.class));
	}
}