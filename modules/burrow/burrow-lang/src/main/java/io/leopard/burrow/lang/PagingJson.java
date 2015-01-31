package io.leopard.burrow.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页json.
 * 
 * @author 阿海
 *
 */
public class PagingJson {
	/**
	 * 将对象转成json.
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public static String toJson(Paging<?> paging) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", paging.getCount());
		map.put("hasNextPage", paging.hasNextPage());
		map.put("list", paging);
		return Json.toJson(map);
	}

	public static <T> Paging<T> toPagingObject(String content, Class<T> valueType) {
		Map<String, Object> map = Json.toMap(content);
		int count = (Integer) map.get("count");
		boolean hasNextPage = (Boolean) map.get("hasNextPage");
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) map.get("list");

		PagingImpl<T> paging = new PagingImpl<T>(count, hasNextPage);

		if (!list.isEmpty()) {
			for (Object map2 : list) {
				String json = Json.toJson(map2);
				// System.out.println("json:" + json);
				T bean = Json.toObject(json, valueType);
				paging.add(bean);
			}

		}
		return paging;
	}
}
