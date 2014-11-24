package io.leopard.test.api;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class ApiKeyUtilTest {

	@Test
	public void ApiKeyUtil() {
		new ApiKeyUtil();
	}

	@Test
	public void getDefaultValue() {
		Assert.assertEquals("str", ApiKeyUtil.getDefaultValue(String.class));
		Assert.assertEquals(1, ApiKeyUtil.getDefaultValue(Integer.class));
		Assert.assertEquals(1L, ApiKeyUtil.getDefaultValue(Long.class));
		Assert.assertNull(ApiKeyUtil.getDefaultValue(BigDecimal.class));
	}

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

	@Test
	public void getKey() {
		MemberKey memberKey = (MemberKey) ApiKeyUtil.getKey(MemberKey.class);
		Assert.assertEquals(1, memberKey.getGroupId());
	}

}