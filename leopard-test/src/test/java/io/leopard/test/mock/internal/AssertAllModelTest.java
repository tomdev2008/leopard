package io.leopard.test.mock.internal;

import io.leopard.test.mock.reflect.PackageUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ PackageUtil.class })
public class AssertAllModelTest {

	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

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

	public static enum Type {
		ALL(0, "all");

		private int key;
		private String desc;

		private Type(int key, String desc) {
			this.key = key;
			this.desc = desc;
		}

		// @Override
		public Integer getKey() {
			return key;
		}

		// @Override
		public String getDesc() {
			return desc;
		}
	}

	public static abstract class AbstractDao {

	}

	@Test
	public void AssertAllModel() {
		new AssertAllModel();
	}

	@Test
	public void assertAllModels2() {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		classList.add(MemberKey.class);
		classList.add(Member2Key.class);
		PowerMockito.when(PackageUtil.getClassList("packageName")).thenReturn(classList);
		AssertAllModel.assertAllModels("packageName");
	}

	@Test
	public void assertModel() {
		AssertAllModel.assertModel(User.class);
		AssertAllModel.assertModel(MemberKey.class);
		AssertAllModel.assertModel(Type.class);
		AssertAllModel.assertModel(AbstractDao.class);
	}

}