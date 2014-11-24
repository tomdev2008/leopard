package io.leopard.test.mock.template;

import org.junit.Test;

public class MethodHandlerUpdateImplTest {

	@Test
	public void get() {

	}

	// public static class User {
	// private String username;
	//
	// public String getUsername() {
	// return username;
	// }
	//
	// public void setUsername(String username) {
	// this.username = username;
	// }
	//
	// }
	//
	// public static class UserDaoMysqlImpl implements IGet<User, String> {
	// private Map<String, User> data = new HashMap<String, User>();
	//
	// public User get(String username) {
	// return data.get(username);
	// }
	//
	// public String getNickname() {
	// return "ok";
	// }
	//
	// @Override
	// public boolean add(User user) {
	// data.put(user.getUsername(), user);
	// return true;
	// }
	//
	// public boolean update(User user) {
	// data.put(user.getUsername(), user);
	// return true;
	// }
	// }
	//
	// @Test
	// public void invoke() throws Throwable {
	// UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
	//
	// MethodHandlerUpdateImpl<UserDaoMysqlImpl> methodHandlerUpdateImpl = Mock.spy(new MethodHandlerUpdateImpl<UserDaoMysqlImpl>(userDao, "", true));
	//
	// Method method = MethodUtil.getMethod(UserDaoMysqlImpl.class, "get");
	// methodHandlerUpdateImpl.invoke(userDao, method, null, new Object[] { "hctan" });
	// }
}