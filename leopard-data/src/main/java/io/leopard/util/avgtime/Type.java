package io.leopard.util.avgtime;

import io.leopard.burrow.lang.inum.Inum;

public enum Type implements Inum {
	TEN(1, "1分钟"),
	/** */
	TWENTY(10, "10分钟"),
	/** */
	THIRTY(30, "30分钟"),
	/** */
	ONEHR(60, "一个小时"),
	/** */
	TWOHR(120, "2小时"),
	/** */
	ONEDAY(1440, "1天");

	private int key;
	private String desc;

	private Type(int key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

	@Override
	public Integer getKey() {
		return this.key;
	}

}
