package io.leopard.test.mock.template;

import io.leopard.burrow.lang.Json;
import io.leopard.commons.utility.NumberUtil;
import io.leopard.commons.utility.ObjectUtil;
import io.leopard.data4j.cache.api.IDecr;
import io.leopard.data4j.cache.api.IDelete;
import io.leopard.data4j.cache.api.IGet;
import io.leopard.data4j.cache.api.IGetIncludeDeleted;
import io.leopard.data4j.cache.api.IUnDelete;
import io.leopard.test.mock.Mock;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MethodHandlerAddImplTest {

	public static class User {
		private String username;
		private boolean del;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public boolean isDel() {
			return del;
		}

		public void setDel(boolean del) {
			this.del = del;
		}

	}

	public static class UserDaoMysqlImpl implements IDelete<User, String> {

		public List<String> list(List<String> idList) {
			return null;
		}

		public List<String> list(int type) {
			return null;
		}

		@Override
		public User get(String username) {
			return null;
		}

		@Override
		public boolean add(User user) {
			return false;
		}

		@Override
		public boolean delete(String key, String opusername, Date lmodify) {
			return false;
		}
	}

	@Test
	public void invoke() throws Throwable {
		Method thisMethod = UserDaoMysqlImpl.class.getMethod("add", User.class);
		UserDaoMysqlImpl mock = new UserDaoMysqlImpl();
		MethodHandlerAddImpl<UserDaoMysqlImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMysqlImpl>(mock, ""));
		Object[] args = null;
		{
			// add
			try {
				methodHandler.invoke(thisMethod, args, "add");
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
		{
			// 未知
			try {
				methodHandler.invoke(thisMethod, args, "other");
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
		{// get
			Mockito.doReturn(null).when(methodHandler).get(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "get");
			Mock.verify(methodHandler, 1).get(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// delete
			Mockito.doReturn(false).when(methodHandler).delete(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "delete");
			Mock.verify(methodHandler, 1).delete(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// incr
			Mockito.doReturn(1L).when(methodHandler).incr(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "incr");
			Mock.verify(methodHandler, 1).incr(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// decr
			Mockito.doReturn(1L).when(methodHandler).decr(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "decr");
			Mock.verify(methodHandler, 1).decr(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// remove
			Mockito.doReturn(false).when(methodHandler).remove(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "remove");
			Mock.verify(methodHandler, 1).remove(Mockito.any(Method.class), Mockito.any(Object[].class));
		}

		{// undelete
			Mockito.doReturn(false).when(methodHandler).undelete(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "undelete");
			Mock.verify(methodHandler, 1).undelete(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// exist
			Mockito.doReturn(false).when(methodHandler).exist(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "exist");
			Mock.verify(methodHandler, 1).exist(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// getIncludeDeleted
			Mockito.doReturn(null).when(methodHandler).getIncludeDeleted(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "getIncludeDeleted");
			Mock.verify(methodHandler, 1).getIncludeDeleted(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// getXxx
			Mockito.doReturn(null).when(methodHandler).getXxx(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "getXxx");
			Mock.verify(methodHandler, 1).getXxx(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// map
			Mockito.doReturn(null).when(methodHandler).map(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "map");
			Mock.verify(methodHandler, 1).map(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// deleteXxx
			Mockito.doReturn(false).when(methodHandler).deleteXxx(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "deleteXxx");
			Mock.verify(methodHandler, 1).deleteXxx(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// listXxx
			Mockito.doReturn(false).when(methodHandler).listXxx(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "listXxx");
			Mock.verify(methodHandler, 1).listXxx(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// count
			Mockito.doReturn(1).when(methodHandler).count(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "count");
			Mock.verify(methodHandler, 1).count(Mockito.any(Method.class), Mockito.any(Object[].class));
		}

		{// update
			Mockito.doReturn(false).when(methodHandler).update(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "update");
			Mock.verify(methodHandler, 1).update(Mockito.any(Method.class), Mockito.any(Object[].class));
		}

	}

	@Test
	public void invoke2() throws Throwable {
		UserDaoMysqlImpl mock = new UserDaoMysqlImpl();
		MethodHandlerAddImpl<UserDaoMysqlImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMysqlImpl>(mock, ""));
		Object[] args = null;
		{// list
			Method thisMethod = UserDaoMysqlImpl.class.getMethod("list", List.class);
			Mockito.doReturn(false).when(methodHandler).list(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "list");
			Mock.verify(methodHandler, 1).list(Mockito.any(Method.class), Mockito.any(Object[].class));

			methodHandler.invoke(null, thisMethod, null, args);
			Mock.verify(methodHandler, 2).list(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
		{// listXxx
			Method thisMethod = UserDaoMysqlImpl.class.getMethod("list", int.class);
			Mockito.doReturn(false).when(methodHandler).listXxx(Mockito.any(Method.class), Mockito.any(Object[].class));
			methodHandler.invoke(thisMethod, args, "list");
			Mock.verify(methodHandler, 1).listXxx(Mockito.any(Method.class), Mockito.any(Object[].class));
		}
	}

	@Test
	public void count() {

	}

	@Test
	public void get() throws SecurityException, NoSuchMethodException {
		{
			class UserDaoMemoryImpl implements IGet<User, String> {
				private Map<String, User> data = new HashMap<String, User>();

				@Override
				public User get(String username) {
					return data.get(username);
				}

				@Override
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("get", String.class);
			Object user = methodHandler.get(method, new Object[] { "hctan" });
			Json.print(user, "user");
		}
		{
			class UserDaoMemoryImpl {
				private Map<String, User> data = new HashMap<String, User>();

				@SuppressWarnings("unused")
				public User get(String username) {
					return data.get(username);
				}

				@SuppressWarnings("unused")
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}
			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("get", String.class);
			try {
				methodHandler.get(method, new Object[] { "hctan" });
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}

	}

	@Test
	public void delete() throws SecurityException, NoSuchMethodException {
		{
			class UserDaoMemoryImpl implements IDelete<User, String> {
				private Map<String, User> data = new HashMap<String, User>();

				@Override
				public User get(String username) {
					return data.get(username);
				}

				@Override
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

				@Override
				public boolean delete(String username, String opusername, Date lmodify) {
					User user = data.remove(username);
					return ObjectUtil.isNotNull(user);
				}

			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("delete", String.class, String.class, Date.class);
			Object user = methodHandler.delete(method, new Object[] { "hctan", "hctan", new Date() });
			Json.print(user, "user");
		}
		{
			class UserDaoMemoryImpl {
				private Map<String, User> data = new HashMap<String, User>();

				@SuppressWarnings("unused")
				public User get(String username) {
					return data.get(username);
				}

				@SuppressWarnings("unused")
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

				@SuppressWarnings("unused")
				public boolean delete(String username, String opusername, Date lmodify) {
					User user = data.remove(username);
					return ObjectUtil.isNotNull(user);
				}
			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("delete", String.class, String.class, Date.class);
			try {
				methodHandler.delete(method, new Object[] { "hctan", "hctan", new Date() });
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	@Test
	public void delete2() throws SecurityException, NoSuchMethodException {
		{
			class UserDaoMemoryImpl implements io.leopard.data4j.cache.api.uid.IDelete<User, String> {
				private Map<String, User> data = new HashMap<String, User>();

				@Override
				public User get(String username) {
					return data.get(username);
				}

				@Override
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

				@Override
				public boolean delete(String username, long opyyuid, Date lmodify) {
					User user = data.remove(username);
					return ObjectUtil.isNotNull(user);
				}

			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("delete", String.class, long.class, Date.class);
			Object user = methodHandler.delete(method, new Object[] { "hctan", 1L, new Date() });
			Json.print(user, "user");
		}
		{
			class UserDaoMemoryImpl {
				private Map<String, User> data = new HashMap<String, User>();

				@SuppressWarnings("unused")
				public User get(String username) {
					return data.get(username);
				}

				@SuppressWarnings("unused")
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

				@SuppressWarnings("unused")
				public boolean delete(String username, long opyyuid, Date lmodify) {
					User user = data.remove(username);
					return ObjectUtil.isNotNull(user);
				}
			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("delete", String.class, long.class, Date.class);
			try {
				methodHandler.delete(method, new Object[] { "hctan", 1L, new Date() });
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	public static class User2 {
		private long yyuid;

		public long getYyuid() {
			return yyuid;
		}

		public void setYyuid(long yyuid) {
			this.yyuid = yyuid;
		}
	}

	@Test
	public void deleteYyuid() throws SecurityException, NoSuchMethodException {
		{
			class UserDaoMemoryImpl implements io.leopard.data4j.cache.api.uid.IDelete<User2, Long> {
				private Map<Long, User2> data = new HashMap<Long, User2>();

				@Override
				public User2 get(Long yyuid) {
					return data.get(yyuid);
				}

				@Override
				public boolean add(User2 user) {
					data.put(user.getYyuid(), user);
					return true;
				}

				@Override
				public boolean delete(Long yyuid, long opyyuid, Date lmodify) {
					User2 user = data.remove(yyuid);
					return ObjectUtil.isNotNull(user);
				}

			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("delete", Long.class, long.class, Date.class);
			Object user = methodHandler.deleteYyuid(method, new Object[] { 1L, 1L, new Date() });
			Json.print(user, "user");
		}
		{
			class UserDaoMemoryImpl {
				private Map<Long, User2> data = new HashMap<Long, User2>();

				@SuppressWarnings("unused")
				public User2 get(Long yyuid) {
					return data.get(yyuid);
				}

				@SuppressWarnings("unused")
				public boolean add(User2 user) {
					data.put(user.getYyuid(), user);
					return true;
				}

				@SuppressWarnings("unused")
				public boolean delete(Long yyuid, long opyyuid, Date lmodify) {
					User2 user = data.remove(yyuid);
					return ObjectUtil.isNotNull(user);
				}
			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("delete", Long.class, long.class, Date.class);
			try {
				methodHandler.deleteYyuid(method, new Object[] { 1L, 1L, new Date() });
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	@Test
	public void undelete() throws SecurityException, NoSuchMethodException {
		{
			class UserDaoMemoryImpl implements IUnDelete<User, String> {
				private Map<String, User> data = new HashMap<String, User>();

				@Override
				public User get(String username) {
					User user = data.get(username);
					if (user == null || user.isDel()) {
						return null;
					}
					return user;
				}

				@Override
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

				@Override
				public boolean delete(String username, String opusername, Date lmodify) {
					User user = data.get(username);
					if (user == null || user.isDel()) {
						return false;
					}
					user.setDel(true);
					return true;
				}

				@Override
				public boolean undelete(String key, String username, Date lmodify) {
					User user = data.get(username);
					if (user == null || !user.isDel()) {
						return false;
					}
					user.setDel(false);
					return true;
				}

			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("undelete", String.class, String.class, Date.class);
			Object user = methodHandler.undelete(method, new Object[] { "hctan", "hctan", new Date() });
			Json.print(user, "user");
		}
		{
			class UserDaoMemoryImpl {
				private Map<String, User> data = new HashMap<String, User>();

				@SuppressWarnings("unused")
				public User get(String username) {
					return data.get(username);
				}

				@SuppressWarnings("unused")
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

				@SuppressWarnings("unused")
				public boolean delete(String username, String opusername, Date lmodify) {
					User user = data.remove(username);
					return ObjectUtil.isNotNull(user);
				}

				@SuppressWarnings("unused")
				public boolean undelete(String key, String username, Date lmodify) {
					return true;
				}

			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("undelete", String.class, String.class, Date.class);
			try {
				methodHandler.undelete(method, new Object[] { "hctan", "hctan", new Date() });
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	@Test
	public void remove() throws Exception {
		{
			class UserDaoMemoryImpl implements IGet<User, String> {
				private Map<String, User> data = new HashMap<String, User>();

				@Override
				public User get(String username) {
					return data.get(username);
				}

				@Override
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

				@SuppressWarnings("unused")
				public boolean remove(String username) {
					User user = data.remove(username);
					return ObjectUtil.isNotNull(user);
				}

			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("remove", String.class);
			methodHandler.remove(method, new Object[] { "hctan" });

		}
		{
			class UserDaoMemoryImpl {
				private Map<String, User> data = new HashMap<String, User>();

				@SuppressWarnings("unused")
				public User get(String username) {
					return data.get(username);
				}

				@SuppressWarnings("unused")
				public boolean add(User user) {
					data.put(user.getUsername(), user);
					return true;
				}

				@SuppressWarnings("unused")
				public boolean remove(String username) {
					User user = data.remove(username);
					return ObjectUtil.isNotNull(user);
				}
			}

			UserDaoMemoryImpl mock = new UserDaoMemoryImpl();
			MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(mock, ""));
			Method method = UserDaoMemoryImpl.class.getMethod("remove", String.class);
			try {
				methodHandler.remove(method, new Object[] { "hctan" });
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	@Test
	public void decr() throws SecurityException, NoSuchMethodException {
		{
			class StatDaoMemoryImpl implements IDecr<String> {
				private Map<String, Long> data = new HashMap<String, Long>();

				@Override
				public Long incr(String key, int count) {
					Long num = data.get(key);
					num = NumberUtil.toLong(num);
					num += count;
					data.put(key, num);
					return num;
				}

				@Override
				public Long decr(String key, int count) {
					Long num = data.get(key);
					num = NumberUtil.toLong(num);
					num -= count;
					data.put(key, num);
					return num;
				}

			}
			StatDaoMemoryImpl mock = new StatDaoMemoryImpl();
			MethodHandlerAddImpl<StatDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<StatDaoMemoryImpl>(mock, ""));
			Method method = StatDaoMemoryImpl.class.getMethod("decr", String.class, int.class);
			methodHandler.decr(method, new Object[] { "hctan", 1 });

		}

	}

	@Test
	public void incr() throws SecurityException, NoSuchMethodException {
		{
			class StatDaoMemoryImpl implements IDecr<String> {
				private Map<String, Long> data = new HashMap<String, Long>();

				@Override
				public Long incr(String key, int count) {
					Long num = data.get(key);
					num = NumberUtil.toLong(num);
					num += count;
					data.put(key, num);
					return num;
				}

				@Override
				public Long decr(String key, int count) {
					Long num = data.get(key);
					num = NumberUtil.toLong(num);
					num -= count;
					data.put(key, num);
					return num;
				}

			}
			StatDaoMemoryImpl mock = new StatDaoMemoryImpl();
			MethodHandlerAddImpl<StatDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<StatDaoMemoryImpl>(mock, ""));
			Method method = StatDaoMemoryImpl.class.getMethod("incr", String.class, int.class);
			methodHandler.incr(method, new Object[] { "hctan", 1 });

		}
	}

	@Test
	public void getIncludeDeleted() throws SecurityException, NoSuchMethodException {

		class UserDaoMemoryImpl implements IUnDelete<User, String>, IGetIncludeDeleted<User, String> {
			private Map<String, User> data = new HashMap<String, User>();

			@Override
			public User getIncludeDeleted(String username) {
				return data.get(username);
			}

			@Override
			public User get(String username) {
				User user = data.get(username);
				if (user == null || user.isDel()) {
					return null;
				}
				return user;
			}

			@Override
			public boolean add(User user) {
				data.put(user.getUsername(), user);
				return true;
			}

			@Override
			public boolean delete(String username, String opusername, Date lmodify) {
				User user = data.get(username);
				if (user == null || user.isDel()) {
					return false;
				}
				user.setDel(true);
				return true;
			}

			@Override
			public boolean undelete(String key, String username, Date lmodify) {
				User user = data.get(username);
				if (user == null || !user.isDel()) {
					return false;
				}
				user.setDel(false);
				return true;
			}

		}

		UserDaoMemoryImpl userDaoMemoryImpl = new UserDaoMemoryImpl();

		MethodHandlerAddImpl<UserDaoMemoryImpl> methodHandler = Mockito.spy(new MethodHandlerAddImpl<UserDaoMemoryImpl>(userDaoMemoryImpl, ""));
		Method method = UserDaoMemoryImpl.class.getMethod("getIncludeDeleted", String.class);
		methodHandler.getIncludeDeleted(method, new Object[] { "hctan" });

	}
}