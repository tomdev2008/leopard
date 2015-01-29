package io.leopard.data4j.redis;

import io.leopard.burrow.util.StringUtil;

public class DefaultHashType implements HashType {

	@Override
	public long getHashCode(String key) {
		long hashCode = StringUtil.getHashCode(key);
		return hashCode;
	}

}
