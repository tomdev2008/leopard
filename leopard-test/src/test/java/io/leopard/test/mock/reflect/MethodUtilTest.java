package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.inum.Inum;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.google.inject.matcher.Matchers;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ Matchers.class })
public class MethodUtilTest {

	public static class UserDaoMysqlImpl {
		public String getContent(Integer articleId) {
			return "content:" + articleId;
		}

		public String getTitle(Integer articleId) {
			return "title:" + articleId;
		}
	}

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
	public void MethodUtil() {
		new MethodUtil();
	}

	@Test
	public void getMethod() {
		{
			Method method = MethodUtil.getMethod(UserDaoMysqlImpl.class, "getTitle");
			Assert.assertNotNull(method);
		}
		{
			Method method = MethodUtil.getMethod(UserDaoMysqlImpl.class, "getTitle", 1);
			Assert.assertNotNull(method);
		}
		{
			UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
			Method method = MethodUtil.getMethod(userDao, "getTitle", new Object[] { 1 });
			Assert.assertNotNull(method);
		}
		{
			UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
			Method method = MethodUtil.getMethod(userDao, "getTitle", new Object[] { "title" });
			Assert.assertNull(method);
		}
	}

	@Test
	public void parseMethodInfo() {
		MethodInfo methodInfo = MethodUtil.parseMethodInfo("dao.getTitle");
		Assert.assertNotNull(methodInfo);
	}

	@Test
	public void getDaoValue() {

	}

	@Test
	public void getDaoField() {

	}

	@Test
	public void invoke() {
		Method method = MethodUtil.getMethod(UserDaoMysqlImpl.class, "getTitle");

		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		Object[] args = { 1 };
		Object result = MethodUtil.invoke(userDao, method, args);
		Assert.assertNotNull(result);
	}

	@Test
	public void invokeMethod() {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		MethodUtil.invokeMethod(userDao, "getTitle");

	}

	@Test
	public void getTypes() {
		Object[] args = { 1 };
		Class<?>[] classes = MethodUtil.getTypes(args);
		Assert.assertEquals(1, classes.length);
		Assert.assertEquals(Integer.class, classes[0]);
	}

	@Test
	public void getServiceMethod() throws SecurityException, NoSuchMethodException {
		Object[] args = { 1 };
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		Method method = MethodUtil.getServiceMethod(userDao, "getTitle", args);
		Assert.assertNotNull(method);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void getDefaultValue() {
		Assert.assertEquals("value", MethodUtil.getDefaultValue(String.class));
		Assert.assertEquals(true, MethodUtil.getDefaultValue(Boolean.class));
		Assert.assertEquals(true, MethodUtil.getDefaultValue(boolean.class));
		Assert.assertEquals(1, MethodUtil.getDefaultValue(int.class));
		Assert.assertEquals(1, MethodUtil.getDefaultValue(Integer.class));
		Assert.assertEquals(1L, MethodUtil.getDefaultValue(long.class));
		Assert.assertEquals(1L, MethodUtil.getDefaultValue(Long.class));
		Assert.assertEquals(1f, (Float) MethodUtil.getDefaultValue(float.class), 0);
		Assert.assertEquals(1f, (Float) MethodUtil.getDefaultValue(Float.class), 0);

		Assert.assertEquals(1d, (Double) MethodUtil.getDefaultValue(double.class), 0);
		Assert.assertEquals(1d, (Double) MethodUtil.getDefaultValue(Double.class), 0);
		Assert.assertEquals(1, ((Date) MethodUtil.getDefaultValue(Date.class)).getTime());

		Assert.assertEquals(0, ((List) MethodUtil.getDefaultValue(List.class)).size());
		Assert.assertEquals(0, ((Map) MethodUtil.getDefaultValue(Map.class)).size());
		Assert.assertEquals(0, ((Set) MethodUtil.getDefaultValue(Set.class)).size());
		Assert.assertNotNull(MethodUtil.getDefaultValue(Pattern.class));
		Assert.assertNull(MethodUtil.getDefaultValue(void.class));
		Assert.assertNotNull(MethodUtil.getDefaultValue(User.class));
		Assert.assertNotNull(MethodUtil.getDefaultValue(HashSet.class));
	}

	@Test
	public void getDefaultValue2() {
		Assert.assertNull(MethodUtil.getDefaultValue((Class<?>[]) null));
		Object[] args = MethodUtil.getDefaultValue(new Class<?>[] { String.class });
		Assert.assertEquals(1, args.length);
		Assert.assertEquals("value", args[0]);
	}

	@Test
	public void getAny() {
		Assert.assertEquals("", MethodUtil.getAny(String.class));
		Assert.assertEquals(false, MethodUtil.getAny(boolean.class));
		Assert.assertEquals(false, MethodUtil.getAny(Boolean.class));
		Assert.assertEquals(0, MethodUtil.getAny(int.class));
		Assert.assertEquals(0, MethodUtil.getAny(Integer.class));
		Assert.assertEquals(0L, MethodUtil.getAny(long.class));
		Assert.assertEquals(0L, MethodUtil.getAny(Long.class));
		Assert.assertNull(MethodUtil.getAny(Date.class));
		Assert.assertNull(MethodUtil.getAny(Object.class));
		Assert.assertNull(MethodUtil.getAny(Class.class));
		Assert.assertNull(MethodUtil.getAny(User.class));
		try {
			MethodUtil.getAny(HashSet.class);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void getGreaterEqualMethod() {

	}

	@Test
	public void hasObjectParam() {
		Assert.assertFalse(MethodUtil.hasObjectParam(new Class<?>[] { String.class }));
		Assert.assertTrue(MethodUtil.hasObjectParam(new Class<?>[] { Object.class }));
	}

	@Test
	public void isLikeMethod() {

	}

	public static enum TestEnum implements Inum {
		;

		@Override
		public Integer getKey() {

			return null;
		}

		@Override
		public String getDesc() {

			return null;
		}

	}

	@Test
	public void getClassTypeName() {
		Assert.assertEquals(String.class.getName(), MethodUtil.getClassTypeName(String.class));
		Assert.assertEquals(Integer.class.getName(), MethodUtil.getClassTypeName(int.class));
		Assert.assertEquals(Long.class.getName(), MethodUtil.getClassTypeName(long.class));
		Assert.assertEquals(Float.class.getName(), MethodUtil.getClassTypeName(float.class));
		Assert.assertEquals(Double.class.getName(), MethodUtil.getClassTypeName(double.class));
		Assert.assertEquals(Double.class.getName(), MethodUtil.getClassTypeName(Double.class));
		Assert.assertEquals(Integer.class.getName(), MethodUtil.getClassTypeName(TestEnum.class));

	}

	@Test
	public void getClassTypeNameByEnum() {
		Assert.assertEquals(Integer.class.getName(), MethodUtil.getClassTypeNameByEnum(TestEnum.class));

	}

	@Test
	public void isEqualsType() {
		Assert.assertFalse(MethodUtil.isEqualsType(new Class<?>[] { String.class }, new Object[] {}));
		Assert.assertFalse(MethodUtil.isEqualsType(new Class<?>[] { String.class }, new Object[] { 1 }));
		Assert.assertFalse(MethodUtil.isEqualsType(new Class<?>[] { String.class }, new Object[] { null }));
		Assert.assertTrue(MethodUtil.isEqualsType(new Class<?>[] { String.class }, new Object[] { "str" }));
	}

	@Test
	public void replaceType() {
		Assert.assertEquals(String.class.getName(), MethodUtil.replaceType(String.class.getName()));
		Assert.assertEquals(Integer.class.getName(), MethodUtil.replaceType(int.class.getName()));
	}

	@Test
	public void listMethod() {
		{
			List<Method> methodList = MethodUtil.listMethod(UserDaoMysqlImpl.class, "getTitle");
			Assert.assertEquals(1, methodList.size());
		}
		{
			List<Method> methodList = MethodUtil.listMethod(UserDaoMysqlImpl.class, "getTitle2");
			Assert.assertEquals(0, methodList.size());
		}
	}

}