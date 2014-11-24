package io.leopard.test.mock.internal;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.test.mock.reflect.Tson;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

public class AssertList {
	@SuppressWarnings("unused")
	public static void list(String content, List<?> list) {
		if (content == null) {
			Assert.assertNull("content对象为null，list怎么不为null？", list);
			return;
		}
		if (list == null) {
			Assert.assertNull("list对象为null，content怎么不为null？", content);
			return;
		}

		if (BasicType.isBasicType(list)) {
			list1(content, list);
		}
		else {
			list2(content, list);
		}
	}

	protected static void list1(String content, List<?> list) {
		String[] strs = StringUtils.split(content, ",");
		Assert.assertEquals("list长度不一致", strs.length, list.size());
		for (int i = 0; i < strs.length; i++) {
			Assert.assertEquals(strs[i], toStringValue(list.get(i)));
		}
	}

	protected static void list2(String content, List<?> list) {
		List<Map<String, String>> data = Tson.parseList(content);
		Assert.assertEquals("list长度不一致", data.size(), list.size());
		Iterator<Map<String, String>> iterator = data.iterator();
		for (Object element : list) {
			Map<String, String> map = iterator.next();
			for (Entry<String, String> field : map.entrySet()) {
				String key = field.getKey();
				String value = field.getValue();
				Object mapValue = FieldUtil.getFieldValue(element, key);
				Assert.assertEquals(value, toStringValue(mapValue));
			}
		}
	}

	protected static String toStringValue(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Date) {
			long time = ((Date) value).getTime();
			return Long.toString(time);
		}
		return value.toString();
	}

}
