package io.leopard.monitor.thread;

import io.leopard.core.inum.Inum;

public enum ThreadCategory implements Inum {
	CONNECTION(90, "网络连接"), DAO(80, "Dao"), SERVICE(70, "Service"), TIMER(60, "定时器"), UNKNOWN(50, "未知");
	private int key;
	private String desc;

	private ThreadCategory(int key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public Integer getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	public static ThreadCategory toEnumByDesc(String desc) {
		ThreadCategory[] values = ThreadCategory.values();
		for (ThreadCategory category : values) {
			if (category.getDesc().equals(desc)) {
				return category;
			}
		}
		// throw new IllegalArgumentException("根据描述信息[" + desc + "]找不到对应的枚举.");

		// "根据描述信息[" + desc + "]找不到对应的枚举."
		return UNKNOWN;
	}

}
