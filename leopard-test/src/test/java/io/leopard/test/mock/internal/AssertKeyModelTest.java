package io.leopard.test.mock.internal;

import org.junit.Assert;
import org.junit.Test;

public class AssertKeyModelTest {

	public static class MemberKey {
		private int groupId;
		private long yyuid;

		public MemberKey() {

		}

		public MemberKey(int groupId, long yyuid) {
			this.groupId = groupId;
			this.yyuid = yyuid;
		}

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}

		public long getYyuid() {
			return yyuid;
		}

		public void setYyuid(long yyuid) {
			this.yyuid = yyuid;
		}

	}

	public static class Member2Key {
		protected int groupId;

		public Member2Key() {

		}

		public Member2Key(int groupId) {
			this.groupId = groupId;
		}

		public int getGroupId() {
			throw new RuntimeException("msg");
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}
	}

	public static class Member3Key {
		protected int groupId;

		public Member3Key(int groupId) {
			throw new RuntimeException("msg");
		}

	}

	@Test
	public void AssertKeyModel() {
		new AssertKeyModel();
	}

	@Test
	public void assertKeyModel() {
		AssertKeyModel.assertKeyModel(MemberKey.class);
		try {
			AssertKeyModel.assertKeyModel(Member2Key.class);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
		try {
			AssertKeyModel.assertKeyModel(Member3Key.class);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void isValidMethodName() {
		Assert.assertTrue(AssertKeyModel.isValidMethodName("getYyuid"));
		Assert.assertTrue(AssertKeyModel.isValidMethodName("setYyuid"));
		Assert.assertTrue(AssertKeyModel.isValidMethodName("isMember"));
		Assert.assertTrue(AssertKeyModel.isValidMethodName("toString"));
		Assert.assertFalse(AssertKeyModel.isValidMethodName("toJson"));
	}

}