package io.leopard.test.mock.parameter;

import io.leopard.core.exception.NoSuchFieldRuntimeException;
import io.leopard.test4j.mock.LeopardMockito;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ParameterImplTest {

	public static class UserDaoMysqlImpl {
		public String getNickname() {
			return "ahai";
		}
	}

	protected ParameterImpl parameter = Mockito.spy(new ParameterImpl());

	@Test
	public void size() {
		try {
			parameter.size(null, null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void defaultCharacter() {
		Mockito.doReturn('a').when(parameter).getFieldValue(null);
		Assert.assertEquals('a', (char) parameter.defaultCharacter(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(' ', (char) parameter.defaultCharacter(null));
	}

	@Test
	public void defaultString() {
		Mockito.doReturn("a").when(parameter).getFieldValue(null);
		Assert.assertEquals("a", parameter.defaultString(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals("", parameter.defaultString(null));

		Mockito.doNothing().when(parameter).logClassCastException(Mockito.any(NbField.class), Mockito.any(ClassCastException.class));
		Mockito.doThrow(new ClassCastException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals("", parameter.defaultString(null));
	}

	@Test
	public void logClassCastException() throws SecurityException, NoSuchMethodException {
		NbField field = Mockito.spy(new NbField());
		ClassCastException e = new ClassCastException();
		Log logger = Mockito.mock(Log.class);
		LeopardMockito.setProperty(parameter, logger);

		field.setParam(String.class);

		{
			Mockito.when(field.getMethod()).thenReturn(null);
			parameter.logClassCastException(field, e);
		}

		{
			Method method = UserDaoMysqlImpl.class.getMethod("getNickname");
			Mockito.when(field.getMethod()).thenReturn(method);
			parameter.logClassCastException(field, e);
		}
	}

	@Test
	public void defaultStrings() {
		Mockito.doReturn(null).when(parameter).getFieldValue(null);
		Assert.assertNull(parameter.defaultStrings(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultStrings(null).length);

		Mockito.doNothing().when(parameter).logClassCastException(Mockito.any(NbField.class), Mockito.any(ClassCastException.class));
		Mockito.doThrow(new ClassCastException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultStrings(null).length);
	}

	@Test
	public void defaultInteger() {
		Mockito.doReturn(1).when(parameter).getFieldValue(null);
		Assert.assertEquals(1, (int) parameter.defaultInteger(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, (int) parameter.defaultInteger(null));

		Mockito.doNothing().when(parameter).logClassCastException(Mockito.any(NbField.class), Mockito.any(ClassCastException.class));
		Mockito.doThrow(new ClassCastException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, (int) parameter.defaultInteger(null));
	}

	@Test
	public void defaultInts() {
		Mockito.doReturn(null).when(parameter).getFieldValue(null);
		Assert.assertNull(parameter.defaultInts(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultInts(null).length);

		Mockito.doNothing().when(parameter).logClassCastException(Mockito.any(NbField.class), Mockito.any(ClassCastException.class));
		Mockito.doThrow(new ClassCastException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultInts(null).length);
	}

	@Test
	public void defaultIntegers() {
		Mockito.doReturn(null).when(parameter).getFieldValue(null);
		Assert.assertNull(parameter.defaultIntegers(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultIntegers(null).length);

		Mockito.doNothing().when(parameter).logClassCastException(Mockito.any(NbField.class), Mockito.any(ClassCastException.class));
		Mockito.doThrow(new ClassCastException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultIntegers(null).length);
	}

	@Test
	public void defaultFloat() {
		Mockito.doReturn(1f).when(parameter).getFieldValue(null);
		Assert.assertEquals(1f, parameter.defaultFloat(null), 0);
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0f, parameter.defaultFloat(null), 0);
	}

	@Test
	public void defaultBigDecimal() {
		Mockito.doReturn(new BigDecimal(1)).when(parameter).getFieldValue(null);
		Assert.assertEquals(1, parameter.defaultBigDecimal(null).intValue());
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultBigDecimal(null).intValue());
	}

	@Test
	public void defaultDouble() {
		Mockito.doReturn(1d).when(parameter).getFieldValue(null);
		Assert.assertEquals(1d, parameter.defaultDouble(null), 0);
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0d, parameter.defaultDouble(null), 0);
	}

	@Test
	public void defaultLong() {
		Mockito.doReturn(1L).when(parameter).getFieldValue(null);
		Assert.assertEquals(1L, (long) parameter.defaultLong(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0L, (long) parameter.defaultLong(null));

		Mockito.doNothing().when(parameter).logClassCastException(Mockito.any(NbField.class), Mockito.any(ClassCastException.class));
		Mockito.doThrow(new ClassCastException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0L, (long) parameter.defaultLong(null));
	}

	@Test
	public void defaultBoolean() {
		Mockito.doReturn(true).when(parameter).getFieldValue(null);
		Assert.assertEquals(true, (boolean) parameter.defaultBoolean(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(false, (boolean) parameter.defaultBoolean(null));
	}

	@Test
	public void defaultDate() {
		Mockito.doReturn(null).when(parameter).getFieldValue(null);
		Assert.assertNull(parameter.defaultDate(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertNotNull(parameter.defaultDate(null));
	}

	@Test
	public void defaultList() {
		Mockito.doReturn(null).when(parameter).getFieldValue(null);
		Assert.assertNull(parameter.defaultList(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultList(null).size());

		Mockito.doNothing().when(parameter).logClassCastException(Mockito.any(NbField.class), Mockito.any(ClassCastException.class));
		Mockito.doThrow(new ClassCastException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultList(null).size());
	}

	@Test
	public void toList() {
		Assert.assertNull(parameter.toList(null));
		Assert.assertNull(parameter.toList(new int[] {}));

		List<Object> list = parameter.toList(new int[] { 1, 2 });
		Assert.assertEquals("[1, 2]", list.toString());
	}

	@Test
	public void defaultCollection() {
		Mockito.doReturn(null).when(parameter).getFieldValue(null);
		Assert.assertNull(parameter.defaultCollection(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultCollection(null).size());
	}

	@Test
	public void defaultSet() {
		Mockito.doReturn(null).when(parameter).getFieldValue(null);
		Assert.assertNull(parameter.defaultSet(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultSet(null).size());

		Mockito.doNothing().when(parameter).logClassCastException(Mockito.any(NbField.class), Mockito.any(ClassCastException.class));
		Mockito.doThrow(new ClassCastException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultSet(null).size());

	}

	@Test
	public void defaultMap() {
		Mockito.doReturn(null).when(parameter).getFieldValue(null);
		Assert.assertNull(parameter.defaultMap(null));
		Mockito.doThrow(new NoSuchFieldRuntimeException("msg")).when(parameter).getFieldValue(null);
		Assert.assertEquals(0, parameter.defaultMap(null).size());
	}

	@Test
	public void getFieldValue() {

	}

}