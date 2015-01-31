package io.leopard.burrow.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.type.TypeFactory;

public class Json {
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share
	private static ObjectMapper mapperIgnoreUnknownField = new ObjectMapper(); // 忽略不存在的字段.
	private static ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
	private static final Log logger = LogFactory.getLog(Json.class);

	static {
		mapperIgnoreUnknownField.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 将对象转成json.
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public static String toFormatJson(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return writer.writeValueAsString(obj);
		}
		catch (Exception e) {
			// LOGGER.info(e.getMessage(), e);
			logger.warn(e.getMessage());
			throw new JsonException(e.getMessage(), e);
		}

	}

	/**
	 * 将对象转成json.
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public static String toJson(Object obj) {
		try {
			String json;
			if (obj == null) {
				json = null;
			}
			else {
				json = mapper.writeValueAsString(obj);
			}
			return json;
		}
		catch (Exception e) {
			// LOGGER.info(e.getMessage(), e);
			logger.warn(e.getMessage());
			throw new JsonException(e.getMessage(), e);
		}
	}

	/**
	 * json转List.
	 * 
	 * @param content
	 *            json数据
	 * @param valueType
	 *            泛型数据类型
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static <T> List<T> toListObject(String content, Class<T> valueType) {
		if (content == null || content.length() == 0) {
			return null;
		}
		try {
			return mapper.readValue(content, TypeFactory.collectionType(List.class, valueType));
		}
		catch (Exception e) {
			logger.warn("message:" + e.getMessage() + " content:" + content);
			throw new JsonException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("deprecation")
	public static <T> List<T> toListObject(String content, Class<T> valueType, boolean ignoreUnknownField) {
		if (content == null || content.length() == 0) {
			return null;
		}
		try {
			if (ignoreUnknownField) {
				return mapperIgnoreUnknownField.readValue(content, TypeFactory.collectionType(List.class, valueType));
			}
			else {
				return mapper.readValue(content, TypeFactory.collectionType(List.class, valueType));
			}
		}
		catch (Exception e) {
			logger.warn("message:" + e.getMessage() + " content:" + content);
			throw new JsonException(e.getMessage(), e);
		}

	}

	public static <T> List<T> toObject(List<String> jsonList, Class<T> valueType) {
		return toObject(jsonList, valueType, false);
	}

	public static <T> List<T> toObject(List<String> jsonList, Class<T> valueType, boolean ignoreUnknownField) {
		if (jsonList == null || jsonList.isEmpty()) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (String json : jsonList) {
			list.add(Json.toObject(json, valueType, ignoreUnknownField));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String content) {
		return Json.toObject(content, Map.class);
	}

	@SuppressWarnings("unchecked")
	public static Set<Object> toSet(String content) {
		return Json.toObject(content, Set.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> toMap(String json, Class<T> clazz) {
		return Json.toObject(json, Map.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> toSet(String json, Class<T> clazz) {
		return Json.toObject(json, Set.class);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toNotNullMap(String json) {
		Map<String, Object> map = Json.toObject(json, Map.class);
		if (map == null) {
			map = new LinkedHashMap<String, Object>();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> toNotNullMap(String json, Class<T> clazz) {
		Map<String, T> map = Json.toObject(json, Map.class);
		if (map == null) {
			map = new LinkedHashMap<String, T>();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> toNotNullSet(String json, Class<T> clazz) {
		Set<T> set = Json.toObject(json, Set.class);
		if (set == null) {
			set = new LinkedHashSet<T>();
		}
		return set;
	}

	/**
	 * 类型转换.
	 * 
	 * @param obj
	 * @param clazz
	 * @return
	 */
	@Deprecated
	public static <T> T convert(Object obj, Class<T> clazz) {
		String json = Json.toJson(obj);
		return toObject(json, clazz);
	}

	/**
	 * 将Json转换成对象.
	 * 
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		return toObject(json, clazz, false);
	}

	/**
	 * 将Json转换成对象.
	 * 
	 * @param json
	 * @param clazz
	 * @param ignoreUnknownField
	 *            是否忽略不存在的字段?
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz, boolean ignoreUnknownField) {
		if (json == null || json.length() == 0) {
			return null;
		}

		try {
			if (ignoreUnknownField) {
				return mapperIgnoreUnknownField.readValue(json, clazz);
			}
			else {
				return mapper.readValue(json, clazz);
			}
		}
		catch (Exception e) {
			logger.warn("message:" + e.getMessage() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static void print(Object obj) {
		String json = Json.toJson(obj);
		System.out.println("json:" + json);
	}

	public static void print(Object obj, String name) {
		String json = Json.toJson(obj);
		System.out.println("json info " + name + "::" + json);
	}

	public static void printFormat(Object obj, String name) {
		String json = Json.toFormatJson(obj);
		System.out.println("json info " + name + "::" + json);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void printMap(Map map, String name) {
		if (map == null) {
			System.out.println("json info " + name + "::null");
			return;
		}
		if (map.size() == 0) {
			System.out.println("json info " + name + "::");
			return;
		}
		Iterator<Entry> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println(name + " key:" + key + " json:" + Json.toJson(value));
		}

	}

	public static void printPaging(@SuppressWarnings("rawtypes") Paging paging, String name) {
		System.out.println("hasNextPage:" + paging.hasNextPage());
		printList(paging, name);
	}

	@SuppressWarnings({ "rawtypes" })
	public static void printList(List list, String name) {
		if (list == null) {
			System.out.println("json info " + name + "::null");
			return;
		}
		if (list.size() == 0) {
			System.out.println("json info " + name + "::");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			Object element = list.get(i);
			System.out.println("json info(" + i + ") " + name + "::" + Json.toJson(element));
		}

	}

	protected static String toJson(ObjectWriter writer, Object obj, String fullMethodName) {
		if (obj == null) {
			return null;
		}
		try {
			return writer.writeValueAsString(obj);
		}
		catch (Exception e) {
			// LOGGER.info(e.getMessage(), e);
			logger.warn(e.getMessage());
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static class JsonException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public JsonException(String message) {
			super(message);
		}

		public JsonException(Throwable cause) {
			super(cause.getMessage(), cause);
		}

		public JsonException(String message, Throwable cause) {
			super(message, cause);
		}
	}

}
