package io.leopard.test.mock.internal;

import io.leopard.burrow.lang.inum.Inum;

import org.junit.Test;

public class AssertEnumTest {

	public static enum Type implements Inum {
		ALL(0, "all");
		private int key;
		private String desc;

		private Type(int key, String desc) {
			this.key = key;
			this.desc = desc;
		}

		@Override
		public Integer getKey() {
			return key;
		}

		@Override
		public String getDesc() {
			return desc;
		}

		public static Type toEnum(int key) {
			return ALL;
		}

	}

	@Test
	public void AssertEnum() {
		new AssertEnum();
	}

	public static enum Type2 {
		ALL
	}

	@Test
	public void assertEnum() {
		AssertEnum.assertEnum(Type.class);
		AssertEnum.assertEnum(Type2.class);
	}

}