package io.leopard.test.mock;

import io.leopard.commons.utility.SystemUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.google.inject.matcher.Matchers;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ Matchers.class, SystemUtil.class })
public class MockTest {
	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	@Test
	public void Mock() {
		new Mock();
	}

	@Test
	public void getSpyCount() {
		Mock.getSpyCount();
	}

	@Test
	public void getCurrentMock() {
		Mock.getCurrentMock();
	}

	@Test
	public void newInstance() {
		Assert.assertNotNull(Mock.newInstance(User.class));
		User user = Mock.newInstance("{username:hctan", User.class);
		Assert.assertEquals("hctan", user.getUsername());
	}

	@Test
	public void newList() {
		List<User> userList = Mock.newList("[username:hctan;username:tanhaichao]", User.class);
		Assert.assertEquals(2, userList.size());
		Assert.assertEquals("hctan", userList.get(0).getUsername());
		Assert.assertEquals("tanhaichao", userList.get(1).getUsername());
	}

	@Test
	public void dao() {

	}

	@Test
	public void verifyStatic() {

	}

	@Test
	public void anyDate() {
		Assert.assertNull(Mock.anyDate());
	}

	@Test
	public void anyFile() {
		Assert.assertNull(Mock.anyFile());
	}

	@Test
	public void anyList() {
		Assert.assertNotNull(Mock.anyList());
		Assert.assertNotNull(Mock.anyList(String.class));
	}

	@Test
	public void verify() {
		UserDaoMysqlImpl userDao = Mock.mock(UserDaoMysqlImpl.class);
		Mock.doReturn("ok").when(userDao).getNickname();
		Assert.assertEquals("ok", userDao.getNickname());

		Mock.verify(userDao, 1).getNickname();

	}

	@Test
	public void mock() {

	}

	@Test
	public void isMocked() {

	}

	@Test
	public void mockLogs() {

	}

	@Test
	public void intList() {
		Assert.assertEquals("[1, 2]", Mock.intList("1,2").toString());
	}

	@Test
	public void intSet() {
		Assert.assertEquals("[1, 2]", Mock.intSet("1,2").toString());
	}

	@Test
	public void longSet() {
		Assert.assertEquals("[1, 2]", Mock.longSet("1,2").toString());
	}

	@Test
	public void strList() {
		Assert.assertEquals("[a, b]", Mock.strList("a,b").toString());
	}

	@Test
	public void strSet() {
		Assert.assertEquals("[a, b]", Mock.strSet("a,b").toString());
	}

	public static class MockSpyServiceImplTest {

		protected MockSpyServiceImpl mockSpyService;
	}

	public static class MockSpyServiceImpl {

		protected UserDaoMysqlImpl userDao;

		public String getTitle() {
			return "ok";
		}
	}

	@Test
	public void spy() throws SecurityException, NoSuchFieldException {
		Assert.assertNull(Mock.spy(null));
		{
			MockSpyServiceImplTest mockSpyServiceImplTest = new MockSpyServiceImplTest();
			MockSpyServiceImpl mockSpyService = Mock.spy(mockSpyServiceImplTest, MockSpyServiceImpl.class);
			Assert.assertNotNull(mockSpyService);
			Field userDaoField = MockSpyServiceImpl.class.getDeclaredField("userDao");
			Assert.assertTrue(Mock.isMocked(mockSpyService, userDaoField));
		}
	}

	@Test
	public void back() {

	}

	@Test
	public void except() {

	}

	@Test
	public void goback() {
		UserDaoMysqlImpl userDao = Mockito.mock(UserDaoMysqlImpl.class);
		{
			Mock.goback(userDao, "getNickname");
			Assert.assertEquals("string", userDao.getNickname());
		}
		{
			Mock.goback(userDao, "getNickname", "ok");
			Assert.assertEquals("ok", userDao.getNickname());
		}

		{
			Mock.goback(SystemUtil.class, "currentTimeMillis", 2L);
			Assert.assertEquals(2L, SystemUtil.currentTimeMillis());
		}
	}

	public static class UserDaoMysqlImpl {
		public User get(String username) {
			return null;
		}

		public List<User> list() {
			return null;
		}

		public String getNickname() {
			return null;
		}
	}

	@Test
	public void doReturn() {
		UserDaoMysqlImpl userDao = Mockito.mock(UserDaoMysqlImpl.class);
		{
			Mock.doReturn("{username:hctan}").when(userDao).get("hctan");
			User user = userDao.get("hctan");
			Assert.assertEquals("hctan", user.getUsername());
		}
		{
			Mock.doReturn("[username:hctan1;username:hctan2]").when(userDao).list();
			List<User> userList = userDao.list();
			Assert.assertEquals(2, userList.size());
			// Assert.equals("[username:hctan1;username:hctan2]", userList);
			Assert.assertEquals("hctan1", userList.get(0).getUsername());
			Assert.assertEquals("hctan2", userList.get(1).getUsername());
		}
		{
			Mock.doReturn().when(userDao).getNickname();
			String nickname = userDao.getNickname();
			Assert.assertEquals("string", nickname);
		}
	}

	@Test
	public void reset() {
		MockSpyServiceImpl mockSpyService = Mock.mock(MockSpyServiceImpl.class);
		Mock.doReturn("hello").when(mockSpyService).getTitle();
		Assert.assertEquals("hello", mockSpyService.getTitle());
		Mock.reset(mockSpyService);
		Assert.assertNull(mockSpyService.getTitle());
	}

	@Test
	public void getFieldValue() {

	}

	@Test
	public void getInterfaces() {

	}

	@Test
	public void getInterfacesAndSelf() {

	}

	@Test
	public void getField() {

	}

	@Test
	public void getTestDate() {

	}

	@Test
	public void testFindBugs() {

	}

}