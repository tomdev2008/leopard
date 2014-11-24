package io.leopard.test.mock;

import io.leopard.commons.utility.DateUtil;
import io.leopard.commons.utility.ListUtil;
import io.leopard.core.inum.Inum;
import io.leopard.data4j.cache.api.IGet;
import io.leopard.test4j.mock.LeopardMockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Test;
import org.mockito.Mockito;

//@RunWith(LeopardMockRunner.class)
// @PrepareForTest({ AssertAllModel.class, AssertKeyModel.class })
public class AssertTest {
	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	public static interface UserDao extends IGet<User, String> {

		String getNickname(String username);

	}

	public static class UserDaoMysqlImpl implements UserDao {
		private Map<String, User> data = new HashMap<String, User>();

		@Override
		public boolean add(User user) {
			data.put(user.getUsername(), user);
			return true;
		}

		@Override
		public User get(String username) {
			return data.get(username);
		}

		@Override
		public String getNickname(String username) {
			return "nick:" + username;
		}

		public boolean update(User user) {
			return true;
		}
	}

	public static class UserService {
		private UserDao userDao;

		public User get(String username) {
			return userDao.get(username);
		}

		public String getNickname(String username) {
			return userDao.getNickname(username);
		}
	}

	@Test
	public void update() {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();

		User user = new User();
		user.setUsername("hctan");

		Assert.Dao.update(userDao).get("hctan");
	}

	@Test
	public void assertModel() {
		Assert.assertModel(User.class);
	}

	@Test
	public void dao() {
		UserDaoMysqlImpl userDaoMysqlImpl = new UserDaoMysqlImpl();
		Assert.dao(userDaoMysqlImpl).get("username");
		Assert.dao(userDaoMysqlImpl, "{username:hctan2}").get("username");
	}

	@Test
	public void noimpl() {
		UserDaoMysqlImpl userDao = Mockito.mock(UserDaoMysqlImpl.class);
		Mockito.doThrow(new NotImplementedException()).when(userDao).get("username");
		Assert.noimpl(userDao).get("username");
	}

	@Test
	public void verify() {
		UserDaoMysqlImpl userDao = Mockito.mock(UserDaoMysqlImpl.class);
		// Mock.doReturn(null).when(userDao).get("username");
		userDao.get("username");

		Assert.verify(userDao).get("username");
	}

	@Test
	public void except() {
		UserDaoMysqlImpl userDao = Mockito.mock(UserDaoMysqlImpl.class);
		Mockito.doThrow(new NotImplementedException()).when(userDao).get("username");
		Assert.except(NotImplementedException.class).when(userDao).get("username");
	}

	@Test
	public void exception() {

	}

	@Test
	public void isBasicType() {

	}

	@Test
	public void map() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		Assert.map("[key1:value1;key2:value2]", map);
	}

	@Test
	public void map1() {

	}

	@Test
	public void map2() {

	}

	@Test
	public void assertList() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		Assert.assertList("a,b", list);
	}

	@Test
	public void list() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		Assert.list("a,b", list);
	}

	@Test
	public void list1() {

	}

	@Test
	public void list2() {

	}

	@Test
	public void toStringValue() {

	}

	@Test
	public void doReturn() {
		UserDaoMysqlImpl userDao = Mockito.mock(UserDaoMysqlImpl.class);
		UserService userService = Mockito.spy(new UserService());
		LeopardMockito.setProperty(userService, userDao);
		// Mock.doReturn("{username:hctan}").when(userDao).get("hctan");

		Assert.doReturn("ahai").when(userService).getNickname("hctan");
		Assert.doReturn("userDao.getNickname", "ahai").when(userService).getNickname("hctan");
		// Assert.doReturn("{username:hctan}").when(userService).get("hctan");

	}

	@Test
	public void when() {
		UserDaoMysqlImpl userDao = Mockito.mock(UserDaoMysqlImpl.class);
		UserService userService = Mockito.spy(new UserService());
		LeopardMockito.setProperty(userService, userDao);

		Assert.when(userService).getNickname("username");
		Assert.when(userService, userDao).getNickname("username");
		Assert.when(userService, "userDao.getNickname").getNickname("username");
	}

	@Test
	public void equals2() {
		Assert.equals(1, 1);
		Assert.equals(1, 1L);
		Assert.equals(1, new Integer(1));
		Assert.equals(new Integer(1), new Integer(1));
		Assert.equals("1", "1");
		Assert.equals(2, ListUtil.makeList("a,b"));
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("key1", "value1");
			map.put("key2", "value2");
			Assert.equals(2, map);
		}
		Assert.equals(1, new Date(1));
		Assert.equals("2013-01-01 00:00:00", DateUtil.toDate("2013-01-01 00:00:00"));

	}

	@Test
	public void equalsByTextson() {
	}

	@Test
	public void getFieldValue() {

	}

	@Test
	public void assertAllModels() {
		Assert.assertAllModels("packageName");
		// Mock.verifyStatic(AssertAllModel.class, "assertAllModels");
	}

	public static enum Type implements Inum {
		ALL(0, "desc");
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

	}

	@Test
	public void assertEnum() {
		Assert.assertEnum(Type.class);
	}

	@Test
	public void enumInvoke() {

	}

	@Test
	public void assertKeyModel() {
		Assert.assertKeyModel(User.class);
		// Mock.verifyStatic(AssertKeyModel.class, "assertKeyModel");
	}

	@Test
	public void Dao() {
		new Assert.Dao();
	}

	@Test
	public void Assert() {
		new Assert();
	}

	@Test
	public void Template() {
		new Assert.Template();
	}

	@Test
	public void equals() {
		Assert.equals(1L, new Long(1));
		Assert.equals(1, new Long(1));

		User user = new User();
		user.setUsername("hctan");
		Assert.equals("{username:hctan}", user);
	}

	@Test
	public void templateNullOrField() {
		// UserDaoMysqlImpl userDao = Mockito.mock(UserDaoMysqlImpl.class);
		// UserService userService = Mockito.spy(new UserService());
		// LeopardMockito.setProperty(userService, userDao);
		// Assert.templateNullOrField("getNickname", "ahai").when(userService).get("username");
	}

}