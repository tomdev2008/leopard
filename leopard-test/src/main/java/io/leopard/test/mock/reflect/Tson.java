package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.Json;
import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;
import io.leopard.burrow.refect.ClassTypeUtil;
import io.leopard.burrow.refect.FieldUtil;
import io.leopard.burrow.util.DateTime;
import io.leopard.burrow.util.DateUtil;
import io.leopard.commons.utility.BeanUtil;
import io.leopard.test.CustomBeanUtil;

import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author 阿海
 * 
 */
public class Tson {

	public static <KEY, VALUE> Map<KEY, VALUE> toMapObject(String content, Class<KEY> keyType, Class<VALUE> valueType) {
		String[] strs = content.split("[,;]");
		Map<KEY, VALUE> map = new LinkedHashMap<KEY, VALUE>();
		for (String str : strs) {
			String[] blocks = StringUtils.split(str, ":");
			KEY key = parse(blocks[0], keyType);
			VALUE value = parse(blocks[1], valueType);
			map.put(key, value);
		}
		Json.print(map, "map");
		return map;
	}

	/**
	 * 文本转成List.
	 * 
	 * <pre>
	 * 1、"gameId:ddt;gameId:sxd,userCount:1";
	 * </pre>
	 * 
	 * @param tson
	 * @param valueType
	 * @return
	 */
	public static <T> List<T> toListObject(String tson, Class<T> valueType) {
		tson = tson.replace("[", "");
		tson = tson.replace("]", "");
		String[] strs = StringUtils.split(tson, ";");
		List<T> list = new ArrayList<T>();
		for (String str : strs) {
			list.add(toObject(str, valueType));
		}
		return list;
	}

	public static <KEY, VALUE> List<Entry<KEY, VALUE>> toEntryList(String content, Class<KEY> keyType, Class<VALUE> valueType) {
		String[] strs = StringUtils.split(content, ";");
		List<Entry<KEY, VALUE>> list = new ArrayList<Entry<KEY, VALUE>>();
		for (String str : strs) {
			String[] blocks = StringUtils.split(str, ",");
			KEY key = parse(blocks[0], keyType);
			VALUE value = parse(blocks[1], valueType);
			Entry<KEY, VALUE> entry = new SimpleEntry<KEY, VALUE>(key, value);
			list.add(entry);
		}
		return list;
	}

	public static String toTextJson(String content, String[] names, Object[] values) {
		Set<String> ignoreNameSet = null;
		return Tson.toTextJson(content, names, values, ignoreNameSet);
	}

	public static String toTextJson(String content, String[] names, Object[] values, Set<String> ignoreNameSet) {
		Map<String, String> map = Tson.parseMap(content, names, values, ignoreNameSet);
		return toTextJson(map);
	}

	public static String toTextJson(Map<String, String> map) {
		StringBuilder sb = new StringBuilder("{");
		// System.out.println("sb:" + sb.toString());
		for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (sb.length() > 1) {
				sb.append(',');
			}
			sb.append(key).append(':').append(value);
		}
		sb.append('}');
		return sb.toString();
	}

	public static Map<String, String> parseMap(String content, String[] names, Object[] values, Set<String> ignoreNameSet) {
		Map<String, String> map;
		if (StringUtils.isEmpty(content)) {
			map = new LinkedHashMap<String, String>();
		}
		else {
			map = parseMap(content);
		}
		if (names != null) {
			AssertUtil.assertNotNull(values, "values不能为null.");

			for (int i = 0; i < names.length; i++) {
				if (ignoreNameSet != null && ignoreNameSet.contains(names[i])) {
					continue;
				}
				AssertUtil.assertNotNull(values[i], "values[" + i + "]不能为null.");
				String modelName = values[i].getClass().getName();
				if (CustomBeanUtil.isCustomBean(modelName)) {
					if (modelName.endsWith("Key")) {
						Map<String, String> map2 = toMap(values[i]);
						map.putAll(map2);
					}
					else {
						Map<String, String> map2 = toMap(values[i]);
						map.putAll(map2);
					}
				}
				else {
					map.put(names[i], toValue(values[i]));
				}
			}
		}
		return map;
	}

	protected static Map<String, String> toMap(Object arg) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Field[] fields = arg.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			if (fieldName.equals("$jacocoData")) {
				// 兼容Converage插件
				continue;
			}
			String value;
			try {
				value = toValue(field.get(arg));
			}
			catch (Exception e) {
				throw new RuntimeException("fieldName:" + fieldName + " message:" + e.getMessage(), e);
			}
			// System.out.println("fieldName:" + fieldName + " value:" + value);
			map.put(fieldName, value);
		}
		return map;
	}

	protected static String toValue(Object obj) {
		String value;
		if (obj instanceof String) {
			value = (String) obj;
		}
		else if (obj instanceof Integer) {
			value = obj.toString();
		}
		else if (obj instanceof Long) {
			value = obj.toString();
		}
		else if (obj instanceof Float) {
			value = obj.toString();
		}
		else if (obj instanceof Double) {
			value = obj.toString();
		}
		else if (obj instanceof Date) {
			value = ((Date) obj).getTime() + "";
		}
		else if (obj instanceof OnlyDate) {
			value = ((OnlyDate) obj).toString();
		}
		else if (obj instanceof Month) {
			value = ((Month) obj).toString();
		}
		else if (obj instanceof Boolean) {
			value = obj.toString();
		}
		else if (obj.getClass().isEnum()) {
			throw new RuntimeException("目前还不支持枚举属性.");
		}
		else {
			throw new RuntimeException("未知数据类型[" + obj + "].");
		}
		return value;
	}

	public static Map<String, String> parseMap(String content) {
		content = content.replace("{", "");
		content = content.replace("}", "");

		String[] fields = StringUtils.split(content, ",");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (String field : fields) {
			String[] blocks = field.split(":");
			String key = blocks[0];
			String value = blocks[1];
			map.put(key, value);
		}

		return map;
	}

	public static List<Map<String, String>> parseList(String content) {
		content = content.replace("[", "");
		content = content.replace("]", "");

		String[] rows = StringUtils.split(content, ";");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (String row : rows) {
			String[] fields = StringUtils.split(row, ",");
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (String field : fields) {
				String[] blocks = field.split(":");
				String key = blocks[0];
				String value = blocks[1];
				map.put(key, value);
			}
			list.add(map);
		}
		return list;
	}

	public static long toTime(String str) {
		if (str.endsWith("ms")) {
			return Long.parseLong(str.replace("ms", ""));
		}
		else if (str.endsWith("s")) {
			return Long.parseLong(str.replace("s", "")) * 1000;
		}
		else if (str.endsWith("m")) {
			return Long.parseLong(str.replace("m", "")) * 60 * 1000;
		}
		else if (str.endsWith("h")) {
			return Long.parseLong(str.replace("h", "")) * 60 * 60 * 1000;
		}
		else if (str.endsWith("D")) {
			return Long.parseLong(str.replace("D", "")) * 60 * 60 * 1000 * 24;
		}
		throw new RuntimeException("非法时间[" + str + "].");
	}

	public static Date parseDate(String str) {
		if ("now".equalsIgnoreCase(str)) {
			return new Date();
		}
		if (str.startsWith("now+")) {
			long time = System.currentTimeMillis() + toTime(str.substring(4));
			return new Date(time);
		}
		if (str.startsWith("now-")) {
			long time = System.currentTimeMillis() - toTime(str.substring(4));
			return new Date(time);
		}
		if (DateTime.isDate(str)) {
			return DateUtil.toDate(str + " 00:00:00");
		}
		return new Date(Long.parseLong(str));
	}

	@SuppressWarnings("unchecked")
	public static <T> T parse(String str, Class<T> clazz) {
		String className = clazz.getName();
		if (className.equals(String.class.getName())) {
			return (T) str;
		}
		else if (className.equals(Date.class.getName())) {
			return (T) parseDate(str);
		}
		else if (className.equals(OnlyDate.class.getName())) {
			return (T) new OnlyDate(parseDate(str));
		}
		else if (className.equals(Month.class.getName())) {
			return (T) new Month(parseDate(str));
		}
		else if (ClassTypeUtil.isInteger(className)) {
			Integer num = Integer.parseInt(str);
			return (T) num;
		}
		else if (ClassTypeUtil.isLong(className)) {
			Long num = Long.parseLong(str);
			return (T) num;
		}
		else if (ClassTypeUtil.isFloat(className)) {
			Float num = Float.parseFloat(str);
			return (T) num;
		}
		else if (ClassTypeUtil.isDouble(className)) {
			Double num = Double.parseDouble(str);
			return (T) num;
		}
		else if (ClassTypeUtil.isBoolean(className)) {
			Boolean bool = Boolean.parseBoolean(str);
			return (T) bool;
		}
		throw new RuntimeException("未知类型[" + className + "].");
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(String content, Class<T> valueType) {
		content = content.replace("{", "");
		content = content.replace("}", "");
		String[] strs = StringUtils.split(content, ";");
		String[] strs2 = StringUtils.split(strs[0], ",");
		String value = StringUtils.split(strs2[0], ":")[1];
		String className = valueType.getName();
		return (T) getValue(value, className);
	}

	public static <T> String getFieldName(String content) {
		content = content.replace("{", "");
		content = content.replace("}", "");
		String[] strs = StringUtils.split(content, ";");
		String[] strs2 = StringUtils.split(strs[0], ",");
		String fieldName = StringUtils.split(strs2[0], ":")[0];
		return fieldName;
	}

	public static <T> T toObject(String content, Class<T> valueType) {
		content = content.replace("{", "");
		content = content.replace("}", "");
		valueType = ModelInstanceUtil.toRealClass(valueType);

		// System.out.println("valueType:" + valueType.getName());

		// String[] strs = StringUtils.split(content, ",");
		// System.out.println("content:" + content);
		String[] strs = TsonUtil.split(content, ',');

		T bean;
		try {
			bean = valueType.newInstance();
		}
		catch (Exception e) {
			System.err.println("valueType:" + valueType.getName());
			throw new RuntimeException(e.toString(), e);
		}

		for (String str : strs) {
			// String[] block = str.split(":");
			String[] block = TsonUtil.split(str, ':');
			// System.out.println("str:" + str + " block:" + StringUtils.join(block, "#"));
			String fieldName = block[0];
			String value = block[1].replace("\"", "");
			setFieldValue(bean, fieldName, value);
		}
		return bean;
	}

	protected static void setFieldValue(Object bean, String fieldName, String value) {
		Object fieldValue = getFieldValue(bean, fieldName, value);
		Field field = BeanUtil.getField(bean, fieldName);
		// field.setAccessible(true);
		// try {
		// field.set(bean, fieldValue);
		// }
		// catch (Exception e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }
		FieldUtil.setFieldValue(bean, field, fieldValue);
	}

	public static Set<String> getNameSet(String content) {
		String[] strs = TsonUtil.split(content, ',');
		Set<String> set = new LinkedHashSet<String>();
		for (String str : strs) {
			String[] block = TsonUtil.split(str, ':');
			String fieldName = block[0];
			set.add(fieldName);
		}
		return set;
	}

	protected static <T> Object getFieldValue(T bean, String fieldName, String value) {
		// System.out.println("bean:" + bean.getClass().getName() + " fieldName:" + fieldName + " value:" + value);

		Field field = BeanUtil.getField(bean, fieldName);
		String className = field.getType().getName();
		return getValue(value, className);
	}

	protected static Object getValue(String value, String className) {
		if (className.equals(String.class.getName())) {
			return value;
		}
		else if (ClassTypeUtil.isBoolean(className)) {
			return Boolean.parseBoolean(value);
		}
		else if (ClassTypeUtil.isInteger(className)) {
			return Integer.parseInt(value);
		}
		else if (ClassTypeUtil.isLong(className)) {
			return Long.parseLong(value);
		}
		else if (ClassTypeUtil.isFloat(className)) {
			return Float.parseFloat(value);
		}
		else if (ClassTypeUtil.isDouble(className)) {
			return Double.parseDouble(value);
		}
		else if (className.equals(Date.class.getName())) {
			// return new Date(Long.parseLong(value));
			return parseDate(value);
		}
		throw new RuntimeException("未知类型[" + className + "].");
		// return value;
	}
}
