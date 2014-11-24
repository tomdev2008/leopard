package io.leopard.test.mock.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class BasicTypeTest {

	@Test
	public void BasicType() {
		new BasicType();
	}

	@Test
	public void isBasicType() {
		{
			List<String> list = new ArrayList<String>();
			list.add("a");
			Assert.assertTrue(BasicType.isBasicType(list));
		}
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", "value");
			Assert.assertTrue(BasicType.isBasicType(map));
		}
	}

	@Test
	public void isBasicType2() {
		{
			List<Object> list = new ArrayList<Object>();
			list.add(new Object());
			Assert.assertFalse(BasicType.isBasicType(list));
		}
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", new Object());
			Assert.assertFalse(BasicType.isBasicType(map));
		}
		{
			Map<Object, String> map = new HashMap<Object, String>();
			map.put(new Object(), "str");
			Assert.assertFalse(BasicType.isBasicType(map));
		}
	}
}