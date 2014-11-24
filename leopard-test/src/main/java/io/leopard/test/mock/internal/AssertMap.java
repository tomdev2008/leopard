package io.leopard.test.mock.internal;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.test.mock.reflect.Tson;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;

public class AssertMap {

	@SuppressWarnings("unused")
	public static void map(String content, Map<?, ?> map) {
		if (content == null) {
			Assert.assertNull(map);
			return;
		}
		if (map == null) {
			Assert.assertNull(content);
			return;
		}

		if (BasicType.isBasicType(map)) {
			basicType(content, map);
		}
		else {
			bean(content, map);
		}
	}

	/**
	 * 基本类型的map.
	 * 
	 * @param content
	 * @param map
	 */
	private static void basicType(String content, Map<?, ?> map) {
		List<Map<String, String>> list = Tson.parseList(content);
		Assert.assertEquals(list.size(), map.size());
		Iterator<?> iterator = map.entrySet().iterator();
		for (Map<String, String> row : list) {
			@SuppressWarnings("unchecked")
			Entry<Object, Object> entry = (Entry<Object, Object>) iterator.next();
			Entry<String, String> field = row.entrySet().iterator().next();
			String key = field.getKey();
			String value = field.getValue();

			Assert.assertEquals(key, entry.getKey().toString());
			Assert.assertEquals(value, entry.getValue().toString());
		}
	}

	/**
	 * 非基本类型的map.
	 * 
	 * @param content
	 * @param map
	 */
	protected static void bean(String content, Map<?, ?> map) {
		List<Map<String, String>> list = Tson.parseList(content);
		// System.out.println("list:" + list);
		Assert.assertEquals(list.size(), map.size());

		Iterator<?> iterator = map.entrySet().iterator();

		for (Map<String, String> row : list) {
			@SuppressWarnings("unchecked")
			Entry<Object, Object> entry = (Entry<Object, Object>) iterator.next();

			for (Entry<String, String> field : row.entrySet()) {
				String key = field.getKey();
				String value = field.getValue();
				if ("key".equalsIgnoreCase(key)) {
					String mapKey = entry.getKey().toString();
					Assert.assertEquals(value, mapKey);
				}
				else {
					Object element = entry.getValue();
					Object mapValue = FieldUtil.getFieldValue(element, key);
					Assert.assertEquals(value, mapValue.toString());
				}
			}
		}
	}
}
