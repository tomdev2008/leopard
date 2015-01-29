package io.leopard.data4j.redis;

import io.leopard.burrow.util.StringUtil;


public class StringHashType implements HashType {

	@Override
	public long getHashCode(String key) {
		String param = key.substring(key.lastIndexOf(":") + 1);
		// System.out.println("param:" + param);
		long hashCode = StringUtil.getHashCode(param);
		return hashCode;
	}

}
