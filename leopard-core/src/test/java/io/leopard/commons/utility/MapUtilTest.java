package io.leopard.commons.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class MapUtilTest {

	@Test
	public void isEmpty() {

		Assert.assertTrue(MapUtil.isEmpty(null));
		Map<String, String> map = new HashMap<String, String>();
		Assert.assertTrue(MapUtil.isEmpty(map));
		map.put("key", "value");
		Assert.assertFalse(MapUtil.isEmpty(map));
	}

	@Test
	public void toDefaultIntMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "1");
		map.put("b", "2");

		Map<String, Integer> map2 = MapUtil.toDefaultIntMap(map);

		Assert.assertEquals(1, (int) map2.get("a"));
		Assert.assertEquals(2, (int) map2.get("b"));
	}

	@Test
	public void toMap() {
		Set<String> keySet = new LinkedHashSet<String>();
		{
			keySet.add("a");
			keySet.add("b");
		}
		List<Integer> valueList = new ArrayList<Integer>();
		{
			valueList.add(1);
			valueList.add(2);
		}

		Map<String, Integer> map2 = MapUtil.toMap(keySet, valueList);
		Assert.assertEquals(1, (int) map2.get("a"));
		Assert.assertEquals(2, (int) map2.get("b"));

		try {
			valueList.add(3);
			MapUtil.toMap(keySet, valueList);
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void MapUtil() {
		new MapUtil();
	}
}