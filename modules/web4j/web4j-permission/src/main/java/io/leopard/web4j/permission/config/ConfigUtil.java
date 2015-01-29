package io.leopard.web4j.permission.config;

import java.util.List;

public class ConfigUtil {

	protected static Config config = new Config();

	// public static List<Bean> printPermissionList() {
	// List<Bean> list = config.getPermissionList();
	// if (list != null) {
	// for (Bean bean : list) {
	// logger.info("id:" + bean.getId() + " url:" + bean.getUrl());
	// }
	// }
	// return list;
	// }

	/**
	 * 获取URL对应的权限bean.
	 * 
	 * @param url
	 * @return
	 */
	public static Bean getBean(String url) {
		List<Bean> list = config.getPermissionList();
		// System.out.println("config List:" + list);
		if (list == null) {
			return null;
		}

		for (Bean error : list) {
			String prefix = error.getUrl();
			if (url.startsWith(prefix)) {
				return error;
			}
			// 未做参数检查
		}
		return null;
	}
}
