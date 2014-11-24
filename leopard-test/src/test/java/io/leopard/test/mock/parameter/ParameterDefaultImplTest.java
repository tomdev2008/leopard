package io.leopard.test.mock.parameter;

import org.junit.Assert;
import org.junit.Test;

public class ParameterDefaultImplTest {

	protected ParameterDefaultImpl parameter = new ParameterDefaultImpl();

	@Test
	public void defaultString() {
		Assert.assertEquals("", parameter.defaultString(null));
	}

	@Test
	public void defaultStrings() {
		Assert.assertEquals(0, parameter.defaultStrings(null).length);
	}

	@Test
	public void defaultInteger() {
		Assert.assertEquals(0, (int) parameter.defaultInteger(null));
	}

	@Test
	public void defaultInts() {
		Assert.assertEquals(0, parameter.defaultInts(null).length);
	}

	@Test
	public void defaultIntegers() {
		Assert.assertEquals(0, parameter.defaultIntegers(null).length);
	}

	@Test
	public void defaultFloat() {
		Assert.assertEquals(0f, (float) parameter.defaultFloat(null), 0);
	}

	@Test
	public void defaultDouble() {
		Assert.assertEquals(0d, (double) parameter.defaultDouble(null), 0);
	}

	@Test
	public void defaultLong() {
		Assert.assertEquals(0L, (long) parameter.defaultInteger(null));

	}

	@Test
	public void defaultBoolean() {
		Assert.assertEquals(false, parameter.defaultBoolean(null));
	}

	@Test
	public void defaultDate() {
		Assert.assertNotNull(parameter.defaultDate(null));
	}

	@Test
	public void defaultCharacter() {
		Assert.assertEquals(' ', (char) parameter.defaultCharacter(null));
	}

	@Test
	public void defaultList() {
		Assert.assertEquals(0, parameter.defaultList(null).size());
	}

	@Test
	public void defaultCollection() {
		Assert.assertEquals(0, parameter.defaultCollection(null).size());
	}

	@Test
	public void defaultSet() {
		Assert.assertEquals(0, parameter.defaultSet(null).size());
	}

	@Test
	public void defaultMap() {
		Assert.assertEquals(0, parameter.defaultMap(null).size());
	}

	@Test
	public void size() {
		Assert.assertEquals(0, parameter.size(null, null));
	}

	@Test
	public void defaultBigDecimal() {
		Assert.assertEquals(0, parameter.defaultBigDecimal(null).intValue());
	}

}