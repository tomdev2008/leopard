package io.leopard.javahost.impl;

import io.leopard.javahost.model.Host;

/**
 * 虚拟DNS实现类
 * 
 * @author 阿海
 *
 */
public class DnsImpl extends AbstractDns {

	@Override
	public boolean update(String host, String ip) {
		Object entry = createCacheEntry(host, ip);
		getAddressCache().put(host, entry);
		return true;
	}

	@Override
	public boolean remove(String host) {
		Object obj = getAddressCache().remove(host);
		return (obj != null);
	}

	@Override
	public String query(String host) {
		Object entry = getAddressCache().get(host);
		if (entry == null) {
			return null;
		}
		Host bean = super.toHost(entry);
		long millis = bean.getExpiration() - System.currentTimeMillis();
		if (millis > ABOUT_YEAR) {
			// JVM的DNS缓存默认是30秒过期，如果过期时间大于1年则表示自定义的域名解析记录
			// 在要求特别准确的情况下请注意:如果自定义了JVM DNS缓存时间超过1年，则会返回错误数据.
			return bean.getIp();
		}
		return null;
	}

}
